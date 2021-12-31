package com.firma.psp.services;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.firma.psp.dto.OrderDataDTO;
import com.firma.psp.model.OrderData;
import com.firma.psp.repositories.IOrderDataRepository;

@Service
public class OrderDataService {

	@Autowired
	private IOrderDataRepository orderDataRepository;

	private static final Logger logger = LoggerFactory.getLogger(OrderDataService.class);

	public Long addNewOrderData(OrderDataDTO orderDataDTO) throws Exception {
		OrderData o = new OrderData();
		o.setMerchantEmail(orderDataDTO.getMerchantEmail());
		o.setTotalPrice(orderDataDTO.getTotalPrice());
		o.setTransactionId(orderDataDTO.getTransactionId());
		Date d = new Date(orderDataDTO.getTimestamp());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdf.format(d);
		o.setTimestamp(Timestamp.valueOf(dateStr));
		o.setSuccessUrl(orderDataDTO.getSuccessUrl());
		o.setFailUrl(orderDataDTO.getFailUrl());
		o.setErrorUrl(orderDataDTO.getErrorUrl());

		OrderData newOrder = orderDataRepository.save(o);
		logger.info("Saving order data.");

		return newOrder.getId();
	}

}
