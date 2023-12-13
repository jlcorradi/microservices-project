package br.com.jlcorradi.commons.web;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class FeignClientInterceptorConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new FeignClientInterceptor();
    }

    private static class FeignClientInterceptor implements RequestInterceptor {
        @Override
        public void apply(RequestTemplate requestTemplate) {
            HttpUtils.getHeaderFromCurrentRequest(HttpHeaders.AUTHORIZATION)
                    .ifPresent(auth -> requestTemplate.header(HttpHeaders.AUTHORIZATION, auth));
        }
    }
}
