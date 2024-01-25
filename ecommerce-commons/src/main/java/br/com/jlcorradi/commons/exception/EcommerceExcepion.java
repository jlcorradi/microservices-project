package br.com.jlcorradi.commons.exception;

import lombok.Getter;

@Getter
public class EcommerceExcepion extends RuntimeException {
  private final String message;
  private final Throwable cause;

  public EcommerceExcepion(String message, Throwable cause) {
    this.message = message;
    this.cause = cause;
  }
}
