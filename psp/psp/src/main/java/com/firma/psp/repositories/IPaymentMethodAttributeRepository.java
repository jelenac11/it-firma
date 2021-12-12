package com.firma.psp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.firma.psp.model.PaymentMethodAttribute;

@Repository
public interface IPaymentMethodAttributeRepository extends JpaRepository<PaymentMethodAttribute, Long> {

}
