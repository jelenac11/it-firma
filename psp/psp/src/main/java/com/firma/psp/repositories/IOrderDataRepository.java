package com.firma.psp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.firma.psp.model.OrderData;

@Repository
public interface IOrderDataRepository extends JpaRepository<OrderData, Long> {

}
