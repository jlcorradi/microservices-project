package br.com.jlcorradi.commons.config;

import br.com.jlcorradi.commons.auth.DefaultJwtValidator;
import br.com.jlcorradi.commons.web.DefaultApiErrorHandler;
import br.com.jlcorradi.commons.web.FeignClientInterceptorConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({CommonConfig.class, DefaultApiErrorHandler.class, DefaultJwtValidator.class,
    FeignClientInterceptorConfig.class})
public @interface WithCommons
{
}
