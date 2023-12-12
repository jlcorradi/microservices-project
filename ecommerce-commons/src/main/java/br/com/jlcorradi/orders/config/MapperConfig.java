package br.com.jlcorradi.orders.config;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper mapper() {
        log.info("Model Mapper was imported into the context.");
        return new ModelMapper();
    }

}
