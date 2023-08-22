package com.example.feign.config;

import com.example.feign.feign.logger.FeignCustomLogger;
import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FeignConfig는 FeignClient를 사용하기 위해 필요한 설정을 담고 있는 클래스입니다.
 * FeignClient를 사용하기 위해선 @EnableFeignClients 어노테이션을 사용해야 합니다.
 */
@Configuration
public class FeignConfig {

    /**
     * FeignClient의 로그 레벨을 설정합니다.
     */
    @Bean
    public Logger logger() {
        return new FeignCustomLogger();
    }
}