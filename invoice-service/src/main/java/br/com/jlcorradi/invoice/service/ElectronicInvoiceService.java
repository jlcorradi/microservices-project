package br.com.jlcorradi.invoice.service;

import br.com.jlcorradi.orders.OrderStatusChangeEventListener;
import br.com.jlcorradi.orders.dto.OrderStatusChangeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ElectronicInvoiceService implements OrderStatusChangeEventListener {
  @Override
  @RabbitListener(queues = "#{invoiceOnOrderStatusChangeQueue.name}")
  public void onOrderStatusChange(OrderStatusChangeEvent event) {
    log.info("Handling OrderStatusChangeEvent. #Order: {}, status: {}", event.getOrderId(), event.getNewStatus());
  }
}
