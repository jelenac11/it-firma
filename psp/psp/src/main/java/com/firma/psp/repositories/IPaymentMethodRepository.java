package com.firma.psp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.firma.psp.model.PaymentMethod;


@Repository
public interface IPaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {

}
