package br.com.jlcorradi.commons.config;

import br.com.jlcorradi.commons.web.ContextInfoPropagationInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Configuration
public class CommonConfig {

    @Bean
    public ModelMapper mapper() {
        log.info("Model Mapper was imported into the context.");
        return new ModelMapper();
    }

    @Bean
    public HandlerInterceptor contextInfoPropagationInterceptor() {
        return new ContextInfoPropagationInterceptor();
    }

}
