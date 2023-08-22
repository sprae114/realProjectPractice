package com.example.feign.controller;

import com.example.feign.service.DemoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.RequiredArgsConstructor;

/**
 * DemoController는 FeignClient를 사용하기 위한 컨트롤러입니다.
 */

@RestController
@RequiredArgsConstructor
public class DemoController {

    private final DemoService demoService;

    @GetMapping("/get")
    public String getController() {
        return demoService.get();
    }

    @GetMapping("/post")
    public String postController() {
        return demoService.post();
    }

    @GetMapping("/error")
    public String errorDecoderController() {
        return demoService.errorDecoder();
    }
}