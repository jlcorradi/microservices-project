package br.com.jlcorradi.payment.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface BaseRepository<T, K> extends Repository<T, K> {
  T save(T entity);
}
