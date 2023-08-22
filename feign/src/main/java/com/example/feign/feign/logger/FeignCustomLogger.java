package com.example.feign.feign.logger;

import feign.Logger;
import feign.Request;
import feign.Response;
import feign.Util;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

import static feign.Util.decodeOrDefault;
import static feign.Util.valuesOrEmpty;
import static feign.form.util.CharsetUtil.UTF_8;

@RequiredArgsConstructor
public class FeignCustomLogger extends Logger {
    private static final int DEFAULT_SLOW_API_TIME = 3_000;
    private static final String SLOW_API_NOTICE = "Slow API";

    /**
     * 메소드내에선 Request에 대한 정보를 log로 남길 수 있다.
     * @param configKey : Feign Client의 이름
     * @param logLevel : log의 레벨
     * @param request : Request 객체
     */
    @Override
    protected void logRequest(String configKey, Level logLevel, Request request) {
        System.out.println("[loggerReqeust] : " + request.toString());
    }

    /**
     * 메소드내에선 Request, Response에 대한 정보를 log로 남길 수 있다.
     * 원하는 부분만 추출해서 남길 수 있다.
     *
     * ex) [DemoFeignClient#callGet] <--- HTTP/1.1 200 (115ms)
     *     [DemoFeignClient#callGet] connection: keep-alive
     *     [DemoFeignClient#callGet] content-type: application/json
     *     [DemoFeignClient#callGet] date: Sun, 24 Jul 2022 01:26:05 GMT
     *     [DemoFeignClient#callGet] keep-alive: timeout=60
     *     [DemoFeignClient#callGet] transfer-encoding: chunked
     *     [DemoFeignClient#callGet] {"name":"customName","age":1,"header":"CustomHeader"}
     *     [DemoFeignClient#callGet] [Slow API] elapsedTime : 3001
     *     [DemoFeignClient#callGet] <--- END HTTP (53-byte body)
     */
    @Override
    protected Response logAndRebufferResponse(String configKey, Logger.Level logLevel,
                                              Response response, long elapsedTime) throws IOException {

        String protocolVersion = resolveProtocolVersion(response.protocolVersion());

        String reason = response.reason() != null
                && logLevel.compareTo(Level.NONE) > 0 ? " " + response.reason() : ""; //reason이 null이 아니고 logLevel이 Level.NONE보다 크면 reason을 가져옴

        int status = response.status();

        log(configKey, "<--- %s %s%s (%sms)", protocolVersion, status, reason, elapsedTime); // log를 남김 (ex. <--- HTTP/1.1 200 OK (123ms))

        if (logLevel.ordinal() >= Level.HEADERS.ordinal()) { // logLevel이 Level.HEADERS보다 크면 header를 가져옴

            for (String field : response.headers().keySet()) { // header를 가져옴
                if (shouldLogResponseHeader(field)) {
                    for (String value : valuesOrEmpty(response.headers(), field)) {
                        log(configKey, "%s: %s", field, value); // log를 남김 (ex. Content-Type: application/json)
                    }
                }
            }

            int bodyLength = 0;

            if (response.body() != null && !(status == 204 || status == 205)) { // body가 존재하면 log를 남김
                if (logLevel.ordinal() >= Level.FULL.ordinal()) {
                    log(configKey, ""); // CRLF
                }
                byte[] bodyData = Util.toByteArray(response.body().asInputStream());

                bodyLength = bodyData.length;

                if (logLevel.ordinal() >= Level.HEADERS.ordinal() && bodyLength > 0) { // body가 존재하면 log를 남김
                    log(configKey, "%s", decodeOrDefault(bodyData, UTF_8, "Binary data"));
                }

                if (elapsedTime > DEFAULT_SLOW_API_TIME) { // 3초 이상 걸리면 SLOW_API_NOTICE를 log로 남김
                    log(configKey, "[%s] elapsedTime : %s", SLOW_API_NOTICE, elapsedTime);
                }

                log(configKey, "<--- END HTTP (%s-byte body)", bodyLength);

                return response.toBuilder().body(bodyData).build();

            } else { // body가 존재하지 않으면 log를 남김
                log(configKey, "<--- END HTTP (%s-byte body)", bodyLength);
            }
        }

        return response;
    }

    /**
     * log를 어떤 형식으로 남길지 정해줌
     * @param configKey : Feign Client의 이름
     * @param format : log의 형식
     * @param args : log의 형식에 맞는 값
     */
    @Override
    protected void log(String configKey, String format, Object... args) {
        System.out.println(String.format(methodTag(configKey) + format, args));
    }
}
