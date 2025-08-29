/*
이 테스트를 통해서 검증하고자 하는 부분.
- 서버가 동시 로그인 몇 명까지 안정적으로 처리 가능한가
- 응답 시간(p95)과 실패율은 어느 시점부터 나빠지는가
 */
import http from 'k6/http';
import {check, sleep} from 'k6';

export const options = {
    stages: [
        {duration: '2m', target: 20},
        {duration: '2m', target: 40},
        {duration: '2m', target: 60},
        {duration: '2m', target: 80},
        {duration: '2m', target: 100},
    ],
    thresholds: {
        http_req_duration: ['p(95)<500'], // 95% 요청이 500ms 미만
        http_req_failed: ['rate<0.01'],   // 실패율 1% 미만
    },
};

export function setup() {
    const login_url = 'https://daily-ootd.online/api/auth/csrf-token';
    let csrfRes = http.get(login_url);
    let csrfToken = JSON.parse(csrfRes.body).token;
    check(
        csrfRes,
        {'GET /csrf-token status is 200': (response) => response.status === 200}
    );

    return {csrfToken: csrfToken};
}

export default function (data) {
    let email = `test_user${String(__VU).padStart(3, '0')}@otboo.io`;
    let password = 'Test1234!';

    let payload = JSON.stringify({
        email: email,
        password: password,
    });

    let params = {
        headers: {
            'Content-Type': 'application/json',
            'X-XSRF-TOKEN': data.csrfToken,
            'Cookie': `XSRF-TOKEN=${data.csrfToken}`,
        }
    };

    let loginRes = http.post('https://daily-ootd.online/api/auth/sign-in', payload, params);
    check(
        loginRes,
        {'POST /sign-in status is 200': (response) => response.status === 200}
    );

    sleep(5);
}
