package com.poly.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.poly.dao.CategoryDAO;
import com.poly.dao.OrderDAO;
import com.poly.dao.OrderDetailDAO;
import com.poly.dao.PaymentDAO;
import com.poly.dao.ProductDAO;
import com.poly.dao.ReviewDAO;
import com.poly.dao.UserDAO;
import com.poly.entity.Category;
import com.poly.entity.Order;
import com.poly.entity.OrderDetail;
import com.poly.entity.Payment;
import com.poly.entity.Product;
import com.poly.entity.Review;
import com.poly.entity.User;

@CrossOrigin("*")
@RestController
public class TableRestController {
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private CategoryDAO categoryDAO;
	
	@Autowired
	private OrderDAO orderDAO;
	
	@Autowired
	private OrderDetailDAO orderDetailDAO;
	
	@Autowired
	private PaymentDAO paymentDAO;
	
	@Autowired
	private ReviewDAO reviewDAO;
	
	// UserRestController Begin
	
	@GetMapping("/rest/users")
	public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userDAO.findAll();
        return ResponseEntity.ok(users);
    }
	
	@GetMapping("/rest/users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = userDAO.findById(id).get();
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	@PostMapping("/rest/users")
	public ResponseEntity<User> createUser(@RequestBody User user) {
        user.setCreated_at(new Date()); // Gán ngày tạo
        User savedUser = userDAO.save(user);
        return ResponseEntity.ok(savedUser);
    }
	
	@PutMapping("/rest/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        User existingUser = userDAO.findById(id).get();
        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }
        user.setId(id); // Đảm bảo ID không bị thay đổi
        user.setCreated_at(existingUser.getCreated_at()); // Giữ nguyên ngày tạo cũ
        User updatedUser = userDAO.save(user);
        return ResponseEntity.ok(updatedUser);
    }
	
	@DeleteMapping("/rest/users/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        User user = userDAO.findById(id).get();
        if (user != null) {
            userDAO.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	// UserRestController End
	
	// ProductController Begin
	
	@GetMapping("/rest/products")
	public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productDAO.findAll();
        return ResponseEntity.ok(products);
    }
	
	@GetMapping("/rest/products/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
		Product product = productDAO.findById(id).get();
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	@PostMapping("/rest/products")
	public ResponseEntity<Product> createProduct(@RequestBody Product product) {
		product.setCreated_at(new Date()); // Gán ngày tạo
		Product savedProduct = productDAO.save(product);
        return ResponseEntity.ok(savedProduct);
    }
	
	@PutMapping("/rest/products/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
		Product existingProduct = productDAO.findById(id).get();
        if (existingProduct == null) {
            return ResponseEntity.notFound().build();
        }
        product.setId(id); // Đảm bảo ID không bị thay đổi
        product.setCreated_at(existingProduct.getCreated_at()); // Giữ nguyên ngày tạo cũ
        Product updatedProduct = productDAO.save(product);
        return ResponseEntity.ok(updatedProduct);
    }
	
	@DeleteMapping("/rest/products/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
		Product product = productDAO.findById(id).get();
        if (product != null) {
        	productDAO.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	//ProductController end
	
	//CategoryController begin
	
	@GetMapping("/rest/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryDAO.findAll();
        return ResponseEntity.ok(categories);
    }
	
	@GetMapping("/rest/categories/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Category category = categoryDAO.findById(id).get();
        if (category != null) {
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	@PostMapping("/rest/categories")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category savedCategory = categoryDAO.save(category);
        return ResponseEntity.ok(savedCategory);
    }
	
	@PutMapping("/rest/categories/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        Category existingCategory = categoryDAO.findById(id).get();
        if (existingCategory == null) {
            return ResponseEntity.notFound().build();
        }
        category.setId(id); // Đảm bảo ID không bị thay đổi
        Category updatedCategory = categoryDAO.save(category);
        return ResponseEntity.ok(updatedCategory);
    }
	
	@DeleteMapping("/rest/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        Category category = categoryDAO.findById(id).get();
        if (category != null) {
            categoryDAO.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	//CategoryController end
	
	//OrderController begin
	@GetMapping("/rest/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderDAO.findAll();
        return ResponseEntity.ok(orders);
    }
	
	@GetMapping("/rest/orders/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderDAO.findById(id).get();
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
        	return ResponseEntity.notFound().build();
        }
    }
	
	@PostMapping("/rest/orders")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
		Order savedOrder = orderDAO.save(order);
        return ResponseEntity.ok(savedOrder);
    }
	
	@PutMapping("/rest/orders/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) {
		Order existingOrder = orderDAO.findById(id).get();
        if (existingOrder == null) {
            return ResponseEntity.notFound().build();
        }
        order.setId(id); // Đảm bảo ID không bị thay đổi
        Order updatedOrder = orderDAO.save(order);
        return ResponseEntity.ok(updatedOrder);
    }
	
	@DeleteMapping("/rest/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
		Order order = orderDAO.findById(id).get();
        if (order != null) {
        	orderDAO.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	//OrderController end
	
	//OrderDetailController begin
	
	@GetMapping("/rest/order-details")
	public ResponseEntity<List<OrderDetail>> getAllOrderDetails() {
        List<OrderDetail> orderDetails = orderDetailDAO.findAll();
        return ResponseEntity.ok(orderDetails);
    }
	
	@GetMapping("/rest/order-details/{id}")
    public ResponseEntity<OrderDetail> getOrderDetailById(@PathVariable("id") Long id) {
        Optional<OrderDetail> orderDetail = orderDetailDAO.findById(id);
        return orderDetail.map(ResponseEntity::ok)
                          .orElseGet(() -> ResponseEntity.notFound().build());
    }
	
	@PostMapping("/rest/order-details")
    public ResponseEntity<OrderDetail> createOrderDetail(@RequestBody OrderDetail orderDetail) {
        OrderDetail savedOrderDetail = orderDetailDAO.save(orderDetail);
        return ResponseEntity.ok(savedOrderDetail);
    }
	
	@PutMapping("/rest/order-details/{id}")
    public ResponseEntity<OrderDetail> updateOrderDetail(@PathVariable("id") Long id,
                                                         @RequestBody OrderDetail orderDetail) {
        Optional<OrderDetail> existing = orderDetailDAO.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        orderDetail.setId(id); // Đảm bảo không đổi ID
        OrderDetail updatedOrderDetail = orderDetailDAO.save(orderDetail);
        return ResponseEntity.ok(updatedOrderDetail);
    }
	
	@DeleteMapping("/rest/order-details/{id}")
    public ResponseEntity<Void> deleteOrderDetail(@PathVariable("id") Long id) {
        Optional<OrderDetail> existing = orderDetailDAO.findById(id);
        if (existing.isPresent()) {
            orderDetailDAO.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	//OrderDetailController end
	
	//PaymentController begin
	
	@GetMapping("/rest/payments")
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentDAO.findAll();
        return ResponseEntity.ok(payments);
    }
	
	@GetMapping("/rest/payments/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable("id") Long id) {
        Optional<Payment> paymentOpt = paymentDAO.findById(id);
        if (paymentOpt.isPresent()) {
            return ResponseEntity.ok(paymentOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	@PostMapping("/rest/payments")
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        Payment savedPayment = paymentDAO.save(payment);
        return ResponseEntity.ok(savedPayment);
    }
	
	@PutMapping("/rest/payments/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable("id") Long id, @RequestBody Payment payment) {
        Optional<Payment> existingPaymentOpt = paymentDAO.findById(id);
        if (!existingPaymentOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        payment.setId(id); // Đảm bảo ID không bị thay đổi
        Payment updatedPayment = paymentDAO.save(payment);
        return ResponseEntity.ok(updatedPayment);
    }
	
	@DeleteMapping("/rest/payments/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable("id") Long id) {
        Optional<Payment> paymentOpt = paymentDAO.findById(id);
        if (paymentOpt.isPresent()) {
            paymentDAO.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	//PaymentController end
	
	//ReviewController begin
	
	@GetMapping("/rest/reviews")
    public ResponseEntity<List<Review>> getAllReviews() {
        List<Review> reviews = reviewDAO.findAll();
        return ResponseEntity.ok(reviews);
    }
	
	@GetMapping("/rest/reviews/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable("id") Long id) {
        Optional<Review> reviewOpt = reviewDAO.findById(id);
        return reviewOpt.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
    }
	
	@PostMapping("/rest/reviews")
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        review.setCreated_at(new Date()); // Gán ngày tạo mới
        Review savedReview = reviewDAO.save(review);
        return ResponseEntity.ok(savedReview);
    }
	
	@PutMapping("/rest/reviews/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable("id") Long id, @RequestBody Review review) {
        Optional<Review> existingReviewOpt = reviewDAO.findById(id);
        if (existingReviewOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Review existingReview = existingReviewOpt.get();
        review.setId(id);
        review.setCreated_at(existingReview.getCreated_at()); // Giữ nguyên ngày tạo
        Review updatedReview = reviewDAO.save(review);
        return ResponseEntity.ok(updatedReview);
    }
	
	@DeleteMapping("/rest/reviews/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable("id") Long id) {
        if (reviewDAO.existsById(id)) {
            reviewDAO.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
	
	//ReviewController end
}
