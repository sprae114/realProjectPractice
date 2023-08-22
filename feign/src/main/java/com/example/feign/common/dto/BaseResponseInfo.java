package com.example.feign.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 클라이언트와 서버 간에 데이터를 응답 받는 객체
 */

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class BaseResponseInfo {

    private String name;
    private Long age;
    private String header;

}