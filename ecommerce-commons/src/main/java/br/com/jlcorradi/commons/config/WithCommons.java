package br.com.jlcorradi.commons.config;

import br.com.jlcorradi.orders.web.BadRequestErrorHandling;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({MapperConfig.class, BadRequestErrorHandling.class})
public @interface WithCommons {
}
