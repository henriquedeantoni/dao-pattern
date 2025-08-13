package model.dao.impl;

import java.util.Date;
import java.util.List;

import model.dao.OrderDao;
import model.entities.Client;
import model.entities.Order;
import model.entities.Seller;
import model.entities.enums.OrderStatus;

public class OrderDaoJdbc implements OrderDao {

	@Override
	public void insert(Order obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Order obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Order obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Order findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> findByMinimumValue(Double minValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> findByOrderStatus(OrderStatus orderStatus) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> findByClient(Client client) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> findBySeller(Seller seller) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> filterBySellerAndClient(Client client, Seller seller) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> filterByDate(Date date) {
		// TODO Auto-generated method stub
		return null;
	}

}
