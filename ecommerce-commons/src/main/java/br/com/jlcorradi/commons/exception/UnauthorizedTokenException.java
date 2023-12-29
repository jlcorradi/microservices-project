package br.com.jlcorradi.commons.exception;

public class UnauthorizedTokenException extends EcommerceExcepion
{
  public UnauthorizedTokenException(Throwable e)
  {
    super("Unauthorized", e);
  }

  public UnauthorizedTokenException()
  {
    super("Unauthorized", null);
  }
}
