package com.example.feign.feign.client;

import com.example.feign.common.dto.BaseRequestInfo;
import com.example.feign.common.dto.BaseResponseInfo;
import com.example.feign.feign.config.DemoFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.feign.common.consts.DemoConstant.CUSTOM_HEADER_NAME;


@FeignClient(
        name = "demo-client", // application.yaml에 설정해 놓은 값을 참조
        url = "${feign.url.prefix}", // application.yaml에 설정해 놓은 값을 참조 (= http://localhost:8080/target_server)
        configuration = DemoFeignConfig.class)
public interface DemoFeignClient {

    /**
     * Feign Client를 GET 방식으로 호출하는 메소드
     * @param customHeader : 커스텀 헤더
     * @param name : 이름
     * @param age : 나이
     * @return : 응답 정보
     */
    @GetMapping("/get") // "${feign.url.prefix}/get"으로 요청
    ResponseEntity<BaseResponseInfo> callGet(@RequestHeader(CUSTOM_HEADER_NAME) String customHeader,
                                             @RequestParam("name") String name,
                                             @RequestParam("age") Long age);

    /**
     * Feign Client를 POST 방식으로 호출하는 메소드
     * @param customHeader : 커스텀 헤더
     * @param baseRequestInfo : 요청 정보
     * @return : 응답 정보
     */
    @PostMapping("/post") // "${feign.url.prefix}/post"로 요청
    ResponseEntity<BaseResponseInfo> callPost(@RequestHeader(CUSTOM_HEADER_NAME) String customHeader,
                                              @RequestBody BaseRequestInfo baseRequestInfo);

    /**
     * 에러가 발생하는 메소드
     */
    @GetMapping("/errorDecoder")
    ResponseEntity<BaseResponseInfo> callErrorDecoder();
}