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
import {randomItem} from 'https://jslib.k6.io/k6-utils/1.4.0/index.js';

export let options = {
    vus: 10,
    duration: '5m',
};

const BASE_URL = 'https://daily-ootd.online';
const EMAIL = 'test_user001@otboo.io';
const PASSWORD = 'Test1234!';

const coordinates = [
    // 광주
    {lon: 126.853, lat: 35.160},
    // 서울
    {lon: 126.978, lat: 37.566},
    // 부산
    {lon: 129.075, lat: 35.179},
    // 대구
    {lon: 128.601, lat: 35.871},
    // 인천
    {lon: 126.705, lat: 37.456},
    // 대전
    {lon: 127.384, lat: 36.351},
    // 울산
    {lon: 129.311, lat: 35.538},
    // 세종
    {lon: 127.268, lat: 36.480},
    // 강릉
    {lon: 128.877, lat: 37.751},
    // 전주
    {lon: 127.148, lat: 35.821},
    // 여수
    {lon: 127.662, lat: 34.760},
    // 제주
    {lon: 126.531, lat: 33.499},
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

    let payload = JSON.stringify({
        email: EMAIL,
        password: PASSWORD,
    });

    let params = {
        headers: {
            'Content-Type': 'application/json',
            'X-XSRF-TOKEN': token,
        }
    };

    // 2. 로그인 요청
    let loginRes = http.post(`${BASE_URL}/api/auth/sign-in`, payload, params);
    check(loginRes, {'login success': (r) => r.status === 200});
    let accessToken = JSON.parse(loginRes.body);
    return {token, accessToken}
}

export function setup() {
    return authenticate(); // return 값이 default()의 첫 번째 인자로 전달됨
}
export default function (auth) {
    const coord = randomItem(coordinates)

    let res = http.get(`${BASE_URL}/api/weathers?longitude=${coord.lon}&latitude=${coord.lat}`, {
        headers: {
            'Content-Type': 'application/json',
            'X-XSRF-TOKEN': auth.token,
            Authorization: `Bearer ${auth.accessToken}`
        }
    })

    check(res, {
        'status is 200': (response) => response.status === 200
    })
}
