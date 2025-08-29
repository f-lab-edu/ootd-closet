import http from 'k6/http';
import {check} from 'k6';

export default function () {
    const url = 'http://localhost:8080/api/auth/csrf-token'
    let csrf_res = http.get(url);
    check(csrf_res, { 'csrf status is 200': (r) => r.status === 200});

    let csrf_token = JSON.parse(csrf_res.body).token;

    const login_url = 'http://localhost:8080/api/auth/sign-in';
    const payload = JSON.stringify({
        email: 'system@otboo.io',
        password: 'otboo1!',
    });

    const params = {
        headers: {
            'content-type': 'application/json',
            'X-XSRF-TOKEN': csrf_token
        }
    }
    let res = http.post(login_url, payload, params);
    check(res, {
        'login status 200': (r) => r.status === 200,
    })
};
