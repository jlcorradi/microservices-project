package br.com.jlcorradi.auth.repository;

import br.com.jlcorradi.auth.model.ActiveToken;
import br.com.jlcorradi.auth.model.EcommerceUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ActiveTokenRepository extends JpaRepository<ActiveToken, UUID> {
  List<ActiveToken> findActiveTokenByUser(EcommerceUser user);

  @Modifying
  @Query("update ActiveToken  set active = false where user = :user")
  void markOtherTokensAsInactive(@Param("user") EcommerceUser user);

  Optional<ActiveToken> findActiveTokenByUserAndToken(EcommerceUser user, String token);
}
