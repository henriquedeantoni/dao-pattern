package model.dao;

import java.util.List;

import model.entities.Client;
import model.entities.Order;
import model.entities.enums.OrderStatus;

public interface OrderDao {
	void insert(Order obj);
	void update(Order obj);
	void delete(Order obj);
	Order findById(Integer id);
	List<Order> findByMinimumValue(Double minValue);
	List<Order> findByOrderStatus(OrderStatus orderStatus);
	List<Order> findByClient(Client client);
}
