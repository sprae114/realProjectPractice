package com.example.feign.feign.interceptor;

import static java.nio.charset.StandardCharsets.UTF_8;

import org.apache.commons.lang3.StringUtils;
import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;

/**
 *  Feign 클라이언트에 사용되는 요청 인터셉터입니다.
 *  Feign 클라이언트의 HTTP 요청을 가로채고, 필요한 기능을 추가할 수 있습니다.
 */

@RequiredArgsConstructor(staticName = "of") // of라는 이름의 static 메서드를 만들어줘
public class DemoFeignInterceptor implements RequestInterceptor {

    /**
     * apply는 Feign 클라이언트의 HTTP 요청을 가로챌 때 호출되는 메서드입니다.
     * @param requestTemplate
     */

    @Override
    public void apply(RequestTemplate requestTemplate) {

        // get 요청일 경우
        if (requestTemplate.method().equals(Request.HttpMethod.GET.name())) {
            System.out.println("GET 요청입니다. : " + requestTemplate.queries());
            return;
        }

        // post 요청일 경우
        String encodedRequestBody = StringUtils.toEncodedString(requestTemplate.body(), UTF_8); // requestTemplate.body()는 byte[] 타입이므로 String으로 변환
        System.out.println("[POST] [DemoFeignInterceptor] requestBody : " + encodedRequestBody);


        // 추가적으로 본인이 필요한 로직 추가하기
        String convertedRequestBody = encodedRequestBody;
        requestTemplate.body(convertedRequestBody);
    }
}
