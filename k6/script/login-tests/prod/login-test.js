import http from 'k6/http';
import {check} from 'k6';

export default function () {
    const csrf_url = 'https://daily-ootd.online/api/auth/csrf-token'
    let csrf_res = http.get(csrf_url);
    check(csrf_res, {'csrf status is 200': (r) => r.status === 200});

    let csrftoken = JSON.parse(csrf_res.body).token;

    const login_url = 'https://daily-ootd.online/api/auth/sign-in';
    const payload = JSON.stringify({
        email: 'gomgom@mail.com',
        password: '1234qwer!',
    });

    const params = {
        headers: {
            'content-type': 'application/json',
            'X-XSRF-TOKEN': csrftoken,
            'Cookie': `XSRF-TOKEN=${data.csrfToken}`,
        }
    }
    let login_res = http.post(login_url, payload, params);
    check(login_res, {'login status is 200': (r) => r.status === 200});
};
