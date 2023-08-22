package com.example.feign.service;

import com.example.feign.common.dto.BaseRequestInfo;
import com.example.feign.common.dto.BaseResponseInfo;
import com.example.feign.feign.client.DemoFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * DemoService는 FeignClient를 사용하기 위한 서비스입니다.
 */
@Service
@RequiredArgsConstructor
public class DemoService {

    private final DemoFeignClient client; // FeignClient를 주입받습니다.


    public String get() {
        ResponseEntity<BaseResponseInfo> response = client.callGet("CustomHeader",
                "CustomName",
                1L); // FeignClient를 통해 서버에 요청합니다.

        System.out.println("Name : " + response.getBody().getName());
        System.out.println("Age : " + response.getBody().getAge());
        System.out.println("Header : " + response.getBody().getHeader());

        return "get";
    }

    public String post() {
        BaseRequestInfo requestBody = BaseRequestInfo.builder()
                .name("customName")
                .age(1L)
                .build();

        ResponseEntity<BaseResponseInfo> response = client.callPost("CustomHeader", requestBody);

        System.out.println("Name : " + response.getBody().getName());
        System.out.println("Age : " + response.getBody().getAge());
        System.out.println("Header : " + response.getBody().getHeader());

        return "post";
    }

    public String errorDecoder() {
        client.callErrorDecoder(); // FeignClient를 통해 서버에 요청합니다.
        return "errorDecoder";
    }
}