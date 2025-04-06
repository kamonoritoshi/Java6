package com.poly.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.entity.Payment;

public interface PaymentDAO extends JpaRepository<Payment, Long>{

}
