package br.com.jlcorradi.auth.repository;

import br.com.jlcorradi.auth.model.EcommerceUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EcommerceUserRepository extends JpaRepository<EcommerceUser, UUID>
{

  Optional<EcommerceUser> findByUsername(String username);

}
