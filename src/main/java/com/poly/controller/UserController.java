package com.poly.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.poly.dao.CategoryDAO;
import com.poly.dao.ProductDAO;
import com.poly.entity.Category;
import com.poly.entity.Product;

@Controller
public class UserController {
	@Autowired CategoryDAO categoryDAO;
	@Autowired ProductDAO productDAO;
	
	@GetMapping("/home")
	public String index(Model model) {
		List<Category> categories = categoryDAO.findAll();
		List<Product> products = productDAO.findAll(); // hoặc findAll()
	    model.addAttribute("categories", categories);
	    model.addAttribute("products", products);
		return "user/index";
	}
	
	@GetMapping("/shop")
	public String shopPage(Model model) {
	    List<Product> products = productDAO.findAll(); // hoặc phân trang nếu muốn
	    model.addAttribute("products", products);
	    return "user/shop";
	}
	
	@GetMapping("/cart")
	public String cartPage(Model model) {
		return "user/cart";
	}
	
	@GetMapping("/contact")
	public String contactPage(Model model) {
		return "user/contact";
	}
}
