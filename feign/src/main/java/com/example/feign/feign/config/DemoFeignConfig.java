package com.example.feign.feign.config;

import com.example.feign.feign.decoder.DemoFeignErrorDecoder;
import com.example.feign.feign.interceptor.DemoFeignInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * DemoFeignConfig는 DemoFeignClient를 사용하기 위해 필요한 설정을 담고 있는 클래스입니다.
 */
@Configuration
public class DemoFeignConfig {

    /**
     * demoFeignInterceptor는 FeignClient의 요청 헤더에 CustomHeaderName을 추가하기 위한 인터셉터입니다.
     */
    @Bean
    public DemoFeignInterceptor demoFeignInterceptor() {
        return DemoFeignInterceptor.of();
    }

    /**
     * demoErrorDecoder는 FeignClient의 에러를 디코딩하기 위한 에러 디코더입니다.
     */
    @Bean
    public DemoFeignErrorDecoder demoErrorDecoder() {
        return new DemoFeignErrorDecoder();
    }
}
