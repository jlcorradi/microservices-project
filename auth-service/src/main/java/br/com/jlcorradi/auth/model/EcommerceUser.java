package br.com.jlcorradi.auth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "ecommerce_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EcommerceUser
{
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String username;
  private String encodedPassword;
  private String commaSeparatedAuthorities;
}
