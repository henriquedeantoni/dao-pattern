package model.dao;

import java.util.Date;
import java.util.List;

import model.entities.Client;
import model.entities.Order;
import model.entities.Seller;
import model.entities.enums.OrderStatus;

public interface OrderDao {
	void insert(Order obj);
	void update(Order obj);
	void delete(Order obj);
	Order findById(Integer id);
	List<Order> findByMinimumValue(Double minValue);
	List<Order> findByOrderStatus(OrderStatus orderStatus);
	List<Order> findByClient(Client client);
	List<Order> findBySeller(Seller seller);
	List<Order> filterBySellerAndClient(Client client, Seller seller);
	List<Order> filterByMonth(Date date);
	List<Order> filterByYear(Date date);
}
