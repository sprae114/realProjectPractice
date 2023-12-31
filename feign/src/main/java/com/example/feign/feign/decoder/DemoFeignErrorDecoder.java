package com.example.feign.feign.decoder;

import org.springframework.http.HttpStatus;

import feign.Response;
import feign.codec.ErrorDecoder;


public final class DemoFeignErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder errorDecoder = new Default();

    /**
     * 외부 컴포넌트와 통신 시
     * 정의해놓은 예외 코드 일 경우엔 적절하게 핸들링하여 처리한다.
     */
    @Override
    public Exception decode(String methodKey, Response response) {
        final HttpStatus httpStatus = HttpStatus.resolve(response.status());


        if (httpStatus == HttpStatus.NOT_FOUND) { // 404 에러가 발생하면 RuntimeException을 발생시킵니다.
            System.out.println("[DemoFeignErrorDecoder] Http Status = " + httpStatus);
            throw new RuntimeException(String.format("[RuntimeException] Http Status is %s", httpStatus));
        }

        return errorDecoder.decode(methodKey, response); // 기본 에러 디코더로 에러를 넘깁니다.
    }

}