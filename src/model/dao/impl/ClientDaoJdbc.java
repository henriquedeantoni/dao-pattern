package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.ClientDao;
import model.entities.Client;
import model.entities.Department;
import model.entities.Seller;
import model.exceptions.NotFoundException;

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
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE client"
					+ " SET (Name = ?, Email = ?, TelNumber = ?, Address = ?, BirthDate = ?)"
					+ " WHERE id = ?");
			st.setString(1, client.getName());
			st.setString(2, client.getEmail());
			st.setString(3, client.getTelNumber());
			st.setString(4, client.getAddress());
			st.setDate(5, new java.sql.Date(client.getBirthDate().getTime()));
			st.setInt(6, client.getId());
			
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM client WHERE Id = ?");
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
	public Client findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM client WHERE Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if(rs.next()) {
				Client client = instantiateClient(rs);
				return client;
			}
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		return null;
	}
	
	private Client instantiateClient(ResultSet rs) throws SQLException {
		Client client = new Client();
		client.setId(rs.getInt("ClientId"));
		client.setAddress(rs.getString("Address"));
		client.setName(rs.getString("Name"));
		client.setBirthDate(rs.getDate("BirthDate"));
		client.setEmail(rs.getString("Email"));
		
		return client;
	}

	@Override
	public List<Client> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM client "
					+"ORDER BY Name"
					);
			rs = st.executeQuery();
			
			List<Client> resultsList = new ArrayList<>();
			
			while(rs.next()) {
				
				Client client = instantiateClient(rs);
				resultsList.add(client);
			}
			return resultsList;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Client> findByNameSegment(String segment) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM client "
					+"WHERE Name Like ('%', ?, '%')"
					);
			
			st.setString(1, segment);
			
			rs = st.executeQuery();
			List<Client> resultsList = new ArrayList<>();
			
			while(rs.next()) {
				
				Client client = instantiateClient(rs);
				resultsList.add(client);
			}
			return resultsList;
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
}
