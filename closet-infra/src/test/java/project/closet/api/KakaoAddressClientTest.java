package project.closet.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    KakaoAddressClient.class,
    RestTemplate.class // 직접 등록
})
@TestPropertySource(properties = {
    "closet.kakao.api.key="
})
class KakaoAddressClientInfraTest {

    public static final double LONGITUDE = 127.1086228;
    public static final double LATITUDE = 37.4012191;

    @Autowired
    private KakaoAddressClient kakaoAddressClient;

    @DisplayName("API 호출 테스트")
    @Test
    void tset1() {
        var response = kakaoAddressClient.requestAddressFromKakao(LONGITUDE, LATITUDE);
        List<String> locationNames = response.getLocationNames();
        Assertions.assertNotNull(locationNames);
        locationNames.forEach(System.out::println);
        assertThat(locationNames.size()).isEqualTo(4);
    }

    @DisplayName("테스트 앱 100개 제한")
    @Test
    void test2() {
        int totalRequests = 100;
        int threadCount = 20;
        try (
            ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        ) {
            CountDownLatch countDownLatch = new CountDownLatch(totalRequests);

            AtomicInteger successCount = new AtomicInteger();
            AtomicInteger failCount = new AtomicInteger();

            for (int i = 0; i < totalRequests; i++) {
                executorService.submit(() -> {
                    try {
                        kakaoAddressClient.requestAddressFromKakao(LONGITUDE, LATITUDE);
                        successCount.incrementAndGet();
                    } catch (Exception e) {
                        failCount.incrementAndGet();
                    } finally {
                        countDownLatch.countDown();
                    }
                });
            }
            countDownLatch.await(); // 모든 요청 완료 대기

            System.out.println("✅ 성공: " + successCount.get());
            System.out.println("❌ 실패: " + failCount.get());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
