package br.com.jlcorradi.orders;

public enum OrderStatus {
  PENDING,
  AWAITING_PAYMENT,
  AWAITING_INVOICE,
  AWAITING_SHIPPING,
  SHIPPED,
  COMPLETE, CANCELED
}
