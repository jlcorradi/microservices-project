package br.com.jlcorradi.commons;

import org.modelmapper.ModelMapper;

public interface EntityDtoMapper<T, D> {
  default D toDto(T entity, Class<D> dClass) {
    return getMapper().map(entity, dClass);
  }

  default T toEntity(D dto, Class<T> tClass) {
    return getMapper().map(dto, tClass);
  }

  ModelMapper getMapper();

  void configureMappings();
}
