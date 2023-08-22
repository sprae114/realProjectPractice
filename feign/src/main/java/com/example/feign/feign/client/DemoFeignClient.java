package com.example.feign.feign.client;

import com.example.feign.common.dto.BaseResponseInfo;
import com.example.feign.feign.config.DemoFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import static com.example.feign.common.consts.DemoConstant.CUSTOM_HEADER_NAME;


@FeignClient(
        name = "demo-client", // application.yaml에 설정해 놓은 값을 참조
        url = "${feign.url.prefix}", // application.yaml에 설정해 놓은 값을 참조 (= http://localhost:8080/target_server)
        configuration = DemoFeignConfig.class)
public interface DemoFeignClient {

    @GetMapping("/get") // "${feign.url.prefix}/get"으로 요청
    ResponseEntity<BaseResponseInfo> callGet(@RequestHeader(CUSTOM_HEADER_NAME) String customHeader,
                                             @RequestParam("name") String name,
                                             @RequestParam("age") Long age);
}