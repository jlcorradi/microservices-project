package br.com.jlcorradi.commons.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UnauthorizedTokenException extends RuntimeException
{
  public UnauthorizedTokenException(Throwable e)
  {
    super(e);
  }
}
