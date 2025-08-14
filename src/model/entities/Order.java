package model.entities;

import model.entities.enums.OrderStatus;
import model.entities.enums.PaymentMethod;

public class Order {
	
	private Integer id;
	private Integer totalHoursService;
	private String title;
	private String description;
	private double totalPrice;
	private OrderStatus orderStatus;
	private PaymentMethod paymentMethod;
	
	private Seller seller;
	private Client client;
	
	public Order() {
	}


	public Order(Integer id, Integer totalHoursService, String title, String description, double totalPrice,
			OrderStatus orderStatus, PaymentMethod paymentMethod, Seller seller, Client client) {
		super();
		this.id = id;
		this.totalHoursService = totalHoursService;
		this.title = title;
		this.description = description;
		this.totalPrice = totalPrice;
		this.orderStatus = orderStatus;
		this.paymentMethod = paymentMethod;
		this.seller = seller;
		this.client = client;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTotalHoursService() {
		return totalHoursService;
	}

	public void setTotalHoursService(Integer totalHoursService) {
		this.totalHoursService = totalHoursService;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}


	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
