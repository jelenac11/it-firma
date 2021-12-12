package com.firma.psp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.firma.psp.model.PaymentData;

@Repository
public interface IPaymentDataRepository extends JpaRepository<PaymentData, Long> {

	PaymentData findByMerchantIdAndAttributeId(Long id, Long id2);

}
