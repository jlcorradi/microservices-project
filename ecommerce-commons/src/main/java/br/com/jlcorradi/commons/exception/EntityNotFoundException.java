package br.com.jlcorradi.commons.exception;

public class EntityNotFoundException extends EcommerceExcepion {

  public EntityNotFoundException() {
    super("Entity not found", null);
  }
}
