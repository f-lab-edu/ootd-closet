import http from 'k6/http';
import {check} from 'k6';

export let options = {
    vus: 1,
    iterations: 1
};

export default function () {
    for (let i = 1; i <= 500; i++) {
        let email = `loadtest_user${String(i).padStart(3, '0')}@otboo.io`;
        let password = 'Test1234!';
        let signupPayload = JSON.stringify({
            email: email,
            password: password,
            name: `LoadTestUser${i}`,
        });

        let csrfRes = http.get('http://localhost:8080/api/auth/csrf-token');
        let csrfToken = JSON.parse(csrfRes.body).token;

        let headers = {
            'Content-Type': 'application/json',
            'X-XSRF-TOKEN': csrfToken,
        }

        let res = http.post('http://localhost:8080/api/users', signupPayload, {headers})
        check(res, {[`signup ${email} success`]: (r) => r.status === 201})
    }
}
