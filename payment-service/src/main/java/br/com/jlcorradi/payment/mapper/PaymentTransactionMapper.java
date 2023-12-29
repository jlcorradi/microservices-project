package br.com.jlcorradi.payment.mapper;

import br.com.jlcorradi.commons.EntityDtoMapper;
import br.com.jlcorradi.payment.dto.PaymentTransactionDto;
import br.com.jlcorradi.payment.model.PaymentTransaction;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentTransactionMapper implements EntityDtoMapper<PaymentTransaction, PaymentTransactionDto>
{
  @Getter
  private final ModelMapper mapper;

  @PostConstruct
  @Override
  public void configureMappings()
  {
    log.info("Configuring mapper for payment transaction");
    mapper.createTypeMap(PaymentTransaction.class, PaymentTransactionDto.class)
        .setPostConverter(MappingContext::getDestination);
  }
}
