package br.com.jlcorradi.commons.config;

import br.com.jlcorradi.commons.auth.JwtValidator;
import br.com.jlcorradi.commons.web.BadRequestErrorHandling;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({CommonConfig.class, BadRequestErrorHandling.class, JwtValidator.class})
public @interface WithCommons {
}
