package com.poly.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.poly.entity.Category;
import com.poly.entity.Order;
import com.poly.entity.OrderDetail;
import com.poly.entity.Payment;
import com.poly.entity.Product;
import com.poly.entity.Review;
import com.poly.entity.User;

@Controller
public class AdminController {
	@Autowired
	private RestTemplate restTemplate;
	
	private static final String API_BASE_URL = "http://localhost:8080/rest";
	
	//User Controller begin
	
	@GetMapping("/admin/users")
	public String indexUser(Model model) {
        // Gọi API để lấy danh sách user
        ResponseEntity<List<User>> response = restTemplate.exchange(
                API_BASE_URL + "/users",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {}
        );
        List<User> users = response.getBody();

        model.addAttribute("user", new User()); // Form trống ban đầu
        model.addAttribute("users", users);
        return "admin/user/index";
    }
	
	@GetMapping("/admin/users/create")
	public String createUserForm(Model model) {
        ResponseEntity<List<User>> response = restTemplate.exchange(
                API_BASE_URL + "/users",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {}
        );
        List<User> users = response.getBody();

        model.addAttribute("user", new User());
        model.addAttribute("users", users);
        return "admin/user/index";
    }
	
	@PostMapping("/admin/users/save")
	public String saveUser(@ModelAttribute User user) {
        if (user.getId() == null) {
            // Tạo mới
            restTemplate.postForObject(API_BASE_URL + "/users", user, User.class);
        } else {
            // Cập nhật
            restTemplate.put(API_BASE_URL + "/users" + "/" + user.getId(), user);
        }
        return "redirect:/admin/users";
    }
	
	@GetMapping("/admin/users/edit")
	public String editUserForm(@RequestParam Long id, Model model) {
        // Gọi API để lấy thông tin user theo ID
        User user = restTemplate.getForObject(API_BASE_URL + "/users" + "/" + id, User.class);

        // Gọi API để lấy danh sách user
        ResponseEntity<List<User>> response = restTemplate.exchange(
                API_BASE_URL + "/users",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {}
        );
        List<User> users = response.getBody();

        model.addAttribute("user", user);
        model.addAttribute("users", users);
        return "admin/user/index";
    }
	
	@GetMapping("/admin/users/delete")
    public String deleteUser(@RequestParam Long id) {
        restTemplate.delete(API_BASE_URL + "/users" + "/" + id);
        return "redirect:/admin/users";
    }
	
	//UserController end
	
	//ProductController begin
	
	@GetMapping("/admin/products")
    public String indexProduct(Model model) {
        // Lấy danh sách product
        ResponseEntity<List<Product>> productResponse = restTemplate.exchange(
                API_BASE_URL + "/products",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Product>>() {}
        );
        // Lấy danh sách category cho dropdown
        ResponseEntity<List<Category>> categoryResponse = restTemplate.exchange(
        		API_BASE_URL + "/categories",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Category>>() {}
        );

        model.addAttribute("product", new Product());
        model.addAttribute("products", productResponse.getBody());
        model.addAttribute("categories", categoryResponse.getBody());
        return "admin/product/index";
    }
	
	@GetMapping("/admin/products/create")
    public String createProductForm(Model model) {
        ResponseEntity<List<Product>> productResponse = restTemplate.exchange(
                API_BASE_URL + "/products",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Product>>() {}
        );
        ResponseEntity<List<Category>> categoryResponse = restTemplate.exchange(
        		API_BASE_URL + "/categories",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Category>>() {}
        );

        model.addAttribute("product", new Product());
        model.addAttribute("products", productResponse.getBody());
        model.addAttribute("categories", categoryResponse.getBody());
        return "admin/product/index";
    }
	
	@PostMapping("/admin/products/save")
    public String saveProduct(@ModelAttribute Product product) {
        if (product.getId() == null || product.getId() == 0) {
            restTemplate.postForObject(API_BASE_URL + "/products", product, Product.class);
        } else {
            restTemplate.put(API_BASE_URL + "/products" + "/" + product.getId(), product);
        }
        return "redirect:/admin/products";
    }
	
	@GetMapping("/admin/products/edit")
    public String editProductForm(@RequestParam Long id, Model model) {
        Product product = restTemplate.getForObject(API_BASE_URL + "/products" + "/" + id, Product.class);
        ResponseEntity<List<Product>> productResponse = restTemplate.exchange(
                API_BASE_URL + "/products",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Product>>() {}
        );
        ResponseEntity<List<Category>> categoryResponse = restTemplate.exchange(
        		API_BASE_URL + "/categories",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Category>>() {}
        );

        model.addAttribute("product", product);
        model.addAttribute("products", productResponse.getBody());
        model.addAttribute("categories", categoryResponse.getBody());
        return "admin/product/index";
    }
	
	@GetMapping("/admin/products/delete")
    public String deleteProduct(@RequestParam Long id) {
        restTemplate.delete(API_BASE_URL + "/products" + "/" + id);
        return "redirect:/admin/products";
    }
	
	//ProductController end
	
	//CategoryController begin
	
	@GetMapping("/admin/categories")
    public String indexCategories(Model model) {
        // Lấy danh sách category
        ResponseEntity<List<Category>> categoryResponse = restTemplate.exchange(
        		API_BASE_URL + "/categories",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Category>>() {}
        );
        model.addAttribute("category", new Category());
        model.addAttribute("categories", categoryResponse.getBody());
        return "admin/category/index";
    }
	
	@GetMapping("/admin/categories/create")
    public String createCategoryForm(Model model) {
        ResponseEntity<List<Category>> categoryResponse = restTemplate.exchange(
        		API_BASE_URL + "/categories",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Category>>() {}
        );

        model.addAttribute("category", new Category());
        model.addAttribute("categories", categoryResponse.getBody());
        return "admin/category/index";
    }
	
	@PostMapping("/admin/categories/save")
    public String saveCategory(@ModelAttribute Category category) {
        if (category.getId() == null || category.getId() == 0) {
            restTemplate.postForObject(API_BASE_URL + "/categories", category, Category.class);
        } else {
            restTemplate.put(API_BASE_URL + "/categories" + "/" + category.getId(), category);
        }
        return "redirect:/admin/categories";
    }
	
	@GetMapping("/admin/categories/edit")
    public String editCategoryForm(@RequestParam Long id, Model model) {
		Category category = restTemplate.getForObject(API_BASE_URL + "/categories" + "/" + id, Category.class);
        ResponseEntity<List<Category>> categoryResponse = restTemplate.exchange(
        		API_BASE_URL + "/categories",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Category>>() {}
        );

        model.addAttribute("category", category);
        model.addAttribute("categories", categoryResponse.getBody());
        return "admin/category/index";
    }
	
	@GetMapping("/admin/categories/delete")
    public String deleteCategory(@RequestParam Long id) {
        restTemplate.delete(API_BASE_URL + "/categories" + "/" + id);
        return "redirect:/admin/categories";
    }
	
	//CategoryController end
	
	//OrderController begin
	
	@GetMapping("/admin/orders")
    public String indexOrders(Model model) {
        // Lấy danh sách category
        ResponseEntity<List<Order>> orderResponse = restTemplate.exchange(
        		API_BASE_URL + "/orders",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Order>>() {}
        );
        // Lấy danh sách user
        ResponseEntity<List<User>> userResponse = restTemplate.exchange(
        		API_BASE_URL + "/users",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {}
        );
        model.addAttribute("order", new Order());
        model.addAttribute("users", userResponse.getBody());
        model.addAttribute("orders", orderResponse.getBody());
        return "admin/order/index";
    }
	
	@GetMapping("/admin/orders/create")
    public String createOrderForm(Model model) {
        ResponseEntity<List<Order>> orderResponse = restTemplate.exchange(
        		API_BASE_URL + "/orders",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Order>>() {}
        );
        // Lấy danh sách user
        ResponseEntity<List<User>> userResponse = restTemplate.exchange(
        		API_BASE_URL + "/users",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {}
        );
        model.addAttribute("order", new Order());
        model.addAttribute("users", userResponse.getBody());
        model.addAttribute("orders", orderResponse.getBody());
        return "admin/order/index";
    }
	
	@PostMapping("/admin/orders/save")
    public String saveOrder(@ModelAttribute Order order) {
        if (order.getId() == null || order.getId() == 0) {
            restTemplate.postForObject(API_BASE_URL + "/orders", order, Order.class);
        } else {
            restTemplate.put(API_BASE_URL + "/orders" + "/" + order.getId(), order);
        }
        return "redirect:/admin/orders";
    }
	
	@GetMapping("/admin/orders/edit")
    public String editOrderForm(@RequestParam Long id, Model model) {
		Order order = restTemplate.getForObject(API_BASE_URL + "/orders" + "/" + id, Order.class);
        ResponseEntity<List<Order>> orderResponse = restTemplate.exchange(
        		API_BASE_URL + "/orders",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Order>>() {}
        );
        // Lấy danh sách user
        ResponseEntity<List<User>> userResponse = restTemplate.exchange(
        		API_BASE_URL + "/users",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {}
        );
        model.addAttribute("order", order);
        model.addAttribute("users", userResponse.getBody());
        model.addAttribute("orders", orderResponse.getBody());
        return "admin/order/index";
    }
	
	@GetMapping("/admin/orders/delete")
    public String deleteOrder(@RequestParam Long id) {
        restTemplate.delete(API_BASE_URL + "/orders" + "/" + id);
        return "redirect:/admin/orders";
    }
	
	//OrderController end
	
	//OrderDetailController begin
	
	@GetMapping("/admin/order-details")
    public String indexOrderDetail(Model model) {
        ResponseEntity<List<OrderDetail>> response = restTemplate.exchange(
                API_BASE_URL + "/order-details",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<OrderDetail>>() {}
        );
        List<OrderDetail> orderDetails = response.getBody();

        List<Order> orders = restTemplate.exchange(
                API_BASE_URL + "/orders",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Order>>() {}
        ).getBody();

        List<Product> products = restTemplate.exchange(
                API_BASE_URL + "/products",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Product>>() {}
        ).getBody();

        model.addAttribute("orderDetail", new OrderDetail()); // Form trống
        model.addAttribute("orderDetails", orderDetails);
        model.addAttribute("orders", orders);
        model.addAttribute("products", products);
        return "admin/order-detail/index";
    }
	
	@GetMapping("/admin/order-details/create")
    public String createOrderDetailForm(Model model) {
        return indexOrderDetail(model);
    }
	
	@PostMapping("/admin/order-details/save")
    public String saveOrderDetail(@ModelAttribute OrderDetail orderDetail) {
        if (orderDetail.getId() == null) {
            restTemplate.postForObject(API_BASE_URL + "/order-details", orderDetail, OrderDetail.class);
        } else {
            restTemplate.put(API_BASE_URL + "/order-details/" + orderDetail.getId(), orderDetail);
        }
        return "redirect:/admin/order-details";
    }
	
	@GetMapping("/admin/order-details/edit")
    public String editOrderDetailForm(@RequestParam Long id, Model model) {
        OrderDetail orderDetail = restTemplate.getForObject(API_BASE_URL + "/order-details/" + id, OrderDetail.class);

        List<OrderDetail> orderDetails = restTemplate.exchange(
                API_BASE_URL + "/order-details",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<OrderDetail>>() {}
        ).getBody();

        List<Order> orders = restTemplate.exchange(
                API_BASE_URL + "/orders",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Order>>() {}
        ).getBody();

        List<Product> products = restTemplate.exchange(
                API_BASE_URL + "/products",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Product>>() {}
        ).getBody();

        model.addAttribute("orderDetail", orderDetail);
        model.addAttribute("orderDetails", orderDetails);
        model.addAttribute("orders", orders);
        model.addAttribute("products", products);
        return "admin/order-detail/index";
    }
	
	@GetMapping("/admin/order-details/delete")
    public String deleteOrderDetail(@RequestParam Long id) {
        restTemplate.delete(API_BASE_URL + "/order-details/" + id);
        return "redirect:/admin/order-details";
    }
	
	//OrderDetailController end
	
	//PaymentController begin
	
	@GetMapping("/admin/payments")
    public String indexPayments(Model model) {
        // Lấy danh sách payment
        ResponseEntity<List<Payment>> response = restTemplate.exchange(
                API_BASE_URL + "/payments",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Payment>>() {}
        );
        List<Payment> payments = response.getBody();

        // Lấy danh sách order để hiển thị thông tin người dùng
        ResponseEntity<List<Order>> orderResponse = restTemplate.exchange(
                API_BASE_URL + "/orders",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Order>>() {}
        );
        List<Order> orders = orderResponse.getBody();

        model.addAttribute("payment", new Payment());
        model.addAttribute("payments", payments);
        model.addAttribute("orders", orders);

        return "admin/payment/index";
    }
	
	@GetMapping("/admin/payments/create")
    public String createPaymentForm(Model model) {
        ResponseEntity<List<Order>> orderResponse = restTemplate.exchange(
                API_BASE_URL + "/orders",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Order>>() {}
        );
        List<Order> orders = orderResponse.getBody();

        model.addAttribute("payment", new Payment());
        model.addAttribute("orders", orders);
        return "admin/payment/index";
    }
	
	@PostMapping("/admin/payments/save")
    public String savePayment(@ModelAttribute Payment payment) {
        if (payment.getId() == null) {
            restTemplate.postForObject(API_BASE_URL + "/payments", payment, Payment.class);
        } else {
            restTemplate.put(API_BASE_URL + "/payments/" + payment.getId(), payment);
        }
        return "redirect:/admin/payments";
    }
	
	@GetMapping("/admin/payments/edit")
    public String editPaymentForm(@RequestParam Long id, Model model) {
        // Lấy payment theo ID
        Payment payment = restTemplate.getForObject(API_BASE_URL + "/payments/" + id, Payment.class);

        // Lấy danh sách order
        ResponseEntity<List<Order>> orderResponse = restTemplate.exchange(
                API_BASE_URL + "/orders",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Order>>() {}
        );
        List<Order> orders = orderResponse.getBody();

        // Lấy lại danh sách payment để hiện bảng
        ResponseEntity<List<Payment>> paymentResponse = restTemplate.exchange(
                API_BASE_URL + "/payments",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Payment>>() {}
        );
        List<Payment> payments = paymentResponse.getBody();

        model.addAttribute("payment", payment);
        model.addAttribute("payments", payments);
        model.addAttribute("orders", orders);

        return "admin/payment/index";
    }
	
	@GetMapping("/admin/payments/delete")
    public String deletePayment(@RequestParam Long id) {
        restTemplate.delete(API_BASE_URL + "/payments/" + id);
        return "redirect:/admin/payments";
    }
	
	//PaymentController end
	
	//ReviewController begin
	
	@GetMapping("/admin/reviews")
    public String indexReviews(Model model) {
        ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange(
                API_BASE_URL + "/reviews",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Review>>() {}
        );
        List<Review> reviews = reviewResponse.getBody();

        ResponseEntity<List<User>> userResponse = restTemplate.exchange(
                API_BASE_URL + "/users",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {}
        );
        List<User> users = userResponse.getBody();

        ResponseEntity<List<Product>> productResponse = restTemplate.exchange(
                API_BASE_URL + "/products",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Product>>() {}
        );
        List<Product> products = productResponse.getBody();

        model.addAttribute("review", new Review());
        model.addAttribute("reviews", reviews);
        model.addAttribute("users", users);
        model.addAttribute("products", products);
        return "admin/review/index";
    }
	
	@GetMapping("/admin/reviews/create")
    public String createReviewForm(Model model) {
        model.addAttribute("review", new Review());

        ResponseEntity<List<User>> userResponse = restTemplate.exchange(
                API_BASE_URL + "/users",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {}
        );
        List<User> users = userResponse.getBody();

        ResponseEntity<List<Product>> productResponse = restTemplate.exchange(
                API_BASE_URL + "/products",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Product>>() {}
        );
        List<Product> products = productResponse.getBody();

        model.addAttribute("users", users);
        model.addAttribute("products", products);
        return "admin/review/index";
    }
	
	@PostMapping("/admin/reviews/save")
    public String saveReview(@ModelAttribute Review review) {
        if (review.getId() == null) {
            restTemplate.postForObject(API_BASE_URL + "/reviews", review, Review.class);
        } else {
            restTemplate.put(API_BASE_URL + "/reviews/" + review.getId(), review);
        }
        return "redirect:/admin/reviews";
    }
	
	@GetMapping("/admin/reviews/edit")
    public String editReviewForm(@RequestParam Long id, Model model) {
        Review review = restTemplate.getForObject(API_BASE_URL + "/reviews/" + id, Review.class);

        ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange(
                API_BASE_URL + "/reviews",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Review>>() {}
        );
        List<Review> reviews = reviewResponse.getBody();

        ResponseEntity<List<User>> userResponse = restTemplate.exchange(
                API_BASE_URL + "/users",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {}
        );
        List<User> users = userResponse.getBody();

        ResponseEntity<List<Product>> productResponse = restTemplate.exchange(
                API_BASE_URL + "/products",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Product>>() {}
        );
        List<Product> products = productResponse.getBody();

        model.addAttribute("review", review);
        model.addAttribute("reviews", reviews);
        model.addAttribute("users", users);
        model.addAttribute("products", products);
        return "admin/review/index";
    }
	
	@GetMapping("/admin/reviews/delete")
    public String deleteReview(@RequestParam Long id) {
        restTemplate.delete(API_BASE_URL + "/reviews/" + id);
        return "redirect:/admin/reviews";
    }
}
