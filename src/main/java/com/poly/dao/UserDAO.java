package com.poly.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.entity.User;

public interface UserDAO extends JpaRepository<User, Long>{
	
}
