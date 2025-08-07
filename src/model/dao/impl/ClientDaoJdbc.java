package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.ClientDao;
import model.entities.Client;

public class ClientDaoJdbc implements ClientDao {
	private Connection conn;
	
	public ClientDaoJdbc(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Client client) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"INSERT INTO Client"
					+"(Name, Email, TelNumber, Address)"
					+"VALUES"
					+"(?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS
					);
			st.setString(1, client.getName());
			st.setString(2, client.getEmail());
			st.setString(3, client.getTelNumber());
			st.setString(4, client.getAddress());
			
			int rowsAffected = st.executeUpdate();

			if(rowsAffected > 0) {
				if(rs.next()) {
					int id = rs.getInt(1);
					client.setId(id);
				}
				DB.closeResultSet(rs);
			}
			else
			{
				throw new DbException("Unexpected erro! No rows affected!");
			}
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(st);			
		}
	}
	
	
	@Override
	public void update(Client client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Client findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Client> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Client> findByInitial() {
		// TODO Auto-generated method stub
		return null;
	}
}
