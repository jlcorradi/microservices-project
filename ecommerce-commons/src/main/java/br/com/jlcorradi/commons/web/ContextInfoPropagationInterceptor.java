package br.com.jlcorradi.commons.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class ContextInfoPropagationInterceptor implements HandlerInterceptor
{
  @Override
  public boolean preHandle(
      HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
  {
    log.debug("Intercepting request");
    return true;
  }
}
