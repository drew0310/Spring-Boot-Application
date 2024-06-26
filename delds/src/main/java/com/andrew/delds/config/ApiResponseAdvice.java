package com.andrew.delds.config;

import com.andrew.delds.model.ApiResponse;
import org.slf4j.MDC;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Type;

@ControllerAdvice
public class ApiResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, org.springframework.http.MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, org.springframework.http.server.ServerHttpRequest request, org.springframework.http.server.ServerHttpResponse response) {
        String cid = MDC.get("CID");
        return new ApiResponse<>(cid, body);
    }
}
