package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.OrderDao;
import model.entities.Client;
import model.entities.Order;
import model.entities.Seller;
import model.entities.enums.OrderStatus;
import model.exceptions.NotFoundException;

public class OrderDaoJdbc implements OrderDao {

	private Connection conn;
	
	public OrderDaoJdbc(Connection conn) {
		this.conn = conn;
	}	
	
	@Override
	public void insert(Order obj) {
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement(
					"INSERT INTO Department "
					+"(TotalHoursService, Title, Description, TotalPrice, OrderStatus, PaymentMethod, SellerId, ClientId ) "
					+"VALUES "
					+"(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS
					);
			st.setInt(1, obj.getTotalHoursService());
			st.setString(2, obj.getTitle());
			st.setString(3, obj.getDescription());
			st.setDouble(4, obj.getTotalPrice());
			st.setInt(5, obj.getOrderStatus().getCode());
			st.setInt(6, obj.getPaymentMethod().getCode());
			st.setInt(7, obj.getSeller().getId());
			st.setInt(8, obj.getClient().getId());
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected>0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}
			else
			{
				throw new DbException("Unexpected erro! No rows affected!");
			}
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeConnection();
		}
	}

	@Override
	public void update(Order obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE client"
					+ " SET (TotalHoursService = ?, Title = ?, Description = ?, TotalPrice = ?, OrderStatus = ?, PaymentMethod = ?, SellerId = ?, ClientId = ? )"
					+ " WHERE id = ?");
			st.setInt(1, obj.getTotalHoursService());
			st.setString(2, obj.getTitle());
			st.setString(3, obj.getDescription());
			st.setDouble(4, obj.getTotalPrice());
			st.setInt(5, obj.getOrderStatus().getCode());
			st.setInt(6, obj.getPaymentMethod().getCode());
			st.setInt(7, obj.getSeller().getId());
			st.setInt(8, obj.getClient().getId());
			st.setInt(9, obj.getId());
			
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void delete(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM order WHERE Id = ?");
			st.setInt(1, id);
			int rows = st.executeUpdate();
			
			if(rows == 0) {
				throw new NotFoundException();
			}
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
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
	public List<Order> filterByMonth(Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> filterByYear(Date date) {
		// TODO Auto-generated method stub
		return null;
	}

}
