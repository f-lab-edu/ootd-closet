/*
시나리오
1. CSRF 토큰 획득
2. 로그인 요청
3. 날시 조회 API 호출

부하 조건
동시 사용자 : 20~30명
테스트 시간 : 10분 유지
측정 : 평균 응답 시간, P95, 에러율, DB CPU 부하
 */

import http from 'k6/http';
import {check, sleep} from 'k6';
import { randomItem } from 'https://jslib.k6.io/k6-utils/1.4.0/index.js';

export let options = {
    vus: 20,
    duration: '10m',
};

const BASE_URL = 'https://daily-ootd.online';
const EMAIL = 'test_user001@otboo.io';
const PASSWORD = 'Test1234!';

const coordinates = [
    {lon: 126.8604677, lat: 35.1578084},
    {lon: 127.001, lat: 37.5},
    {lon: 128.123, lat: 36.3},
];

// 로그인 및 csrf 토큰 획득
function authenticate() {
    // 1. csrf 토큰
    let csrfRes = http.get(`${BASE_URL}/api/auth/csrf-token`)
    let token = csrfRes.json('token')
    check(
        csrfRes,
        {'GET /csrf-token status is 200': (response) => response.status === 200}
    );

    // 2. 로그인 요청
    let loginRes = http.post(`${BASE_URL}/api/auth/sign-in`, {
            email: EMAIL,
            password: PASSWORD,
        }, {
            headers: {
                'Content-Type': 'application/json',
                'X-XSRF-TOKEN': csrfToken,
            }
        });
    check(loginRes, { 'login success': (r) => r.status === 200 });
    let accessToken = loginRes.body;
    return { token, accessToken}
}

let auth = authenticate();

export default function () {
    const coord = randomItem(coordinates)

    let res = http.get(`${BASE_URL}/api/`)
}
