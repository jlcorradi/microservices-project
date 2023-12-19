package br.com.jlcorradi.auth.dto;

import lombok.Data;

@Data
public class EcommerceUserDto
{

  private String username;
  private String commaSeparatedAuthorities;

}
