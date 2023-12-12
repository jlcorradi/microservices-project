package br.com.jlcorradi.commons.config;

import br.com.jlcorradi.orders.web.BadRequestErrorHandling;
import org.springframework.context.annotation.Import;

@Import(BadRequestErrorHandling.class)
public class CommonWebMisc {
}
