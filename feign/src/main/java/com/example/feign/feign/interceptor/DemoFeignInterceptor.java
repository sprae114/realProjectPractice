package com.example.feign.feign.interceptor;

import static java.nio.charset.StandardCharsets.UTF_8;

import org.apache.commons.lang3.StringUtils;
import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor(staticName = "of") // of라는 이름의 static 메서드를 만들어줘
public class DemoFeignInterceptor implements RequestInterceptor {

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
/*        String convertedRequestBody = encodedRequestBody.replace("CustomName", "ChangedName"); // requestBody를 변환하는 로직
        requestTemplate.body(convertedRequestBody); // 변환된 requestBody로 다시 설정*/
    }
}
