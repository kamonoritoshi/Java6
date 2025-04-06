package com.poly.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.entity.Review;

public interface ReviewDAO extends JpaRepository<Review, Long>{

}
