import http from 'k6/http';
import {check, sleep} from 'k6';

export const options = {
    thresholds: {
        http_req_duration: ['p(95)<500'],
        http_req_failed: ['rate<0.01']
    },
    stages: [
        {duration: '10s', target: 10},
        {duration: '20s', target: 20},
        {duration: '10s', target: 0}
    ],

}

export function setup() {
    let csrfRes = http.get('http://localhost:8080/api/auth/csrf-token');
    check(csrfRes, {'csrf status is 200': (r) => r.status === 200});

    // Body 또는 Cookie에서 토큰 추출
    let csrfToken = JSON.parse(csrfRes.body).token;
    let csrfCookie = csrfRes.cookies['XSRF-TOKEN'][0].value;

    return {csrfToken: csrfToken};
}

export default function (data) {
    let url = 'http://localhost:8080/api/auth/sign-in';
    let userIndex = Math.floor(Math.random() * 500) + 1;
    let email = `loadtest_user${String(__VU).padStart(3, '0')}@otboo.io`;

    let payload = JSON.stringify({
        email: email,
        password: 'Test1234!',
    });

    let params = {
        headers: {
            'Content-Type': 'application/json',
            'X-XSRF-TOKEN': data.csrfToken,
            'Cookie': `XSRF-TOKEN=${data.csrfToken}`,
        },
    };

    let res = http.post(url, payload, params);

    check(res, {
        'login status is 200': (r) => r.status === 200,
    });

    sleep(5);
}

/*
시나리오
1. csrf-token 발급 ? or 하나의 토큰을 재사용할 것인지?
2. 발급 받은 토큰을 헤더 X-XSRF-TOKEN 에 추가
3. POST 요청에 담을 BODY 만들기 : email, password, loadtest_user${String(i).padStart(3, '0')}@otboo.io
4. POST에 헤더, 페이로드, url 담아서 테스트
 */
