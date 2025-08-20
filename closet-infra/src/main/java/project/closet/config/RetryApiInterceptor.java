package project.closet.config;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.retry.support.RetryTemplate;

@Slf4j
@RequiredArgsConstructor
public class RetryApiInterceptor implements ClientHttpRequestInterceptor {

    private final RetryTemplate retryTemplate;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
        throws IOException {

        return retryTemplate.execute(context -> {
            try {
                return retryTemplate.execute(context1 ->  execution.execute(request, body));
            } catch (Exception exception) {
                log.warn("API 호출 실패, 재시도 ({}회)", context.getRetryCount(), exception);
                throw new RuntimeException(exception);
            }
        }, context -> {
            log.error("API 호출 재시도 모두 실패. 최종 실패: {}", context.getLastThrowable().getMessage());
            throw new IOException("API 호출 재시도 모두 실패", context.getLastThrowable()
            );
        });
    }
}
