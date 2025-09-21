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
			st.setInt(5, client.getId());
			
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
			st = conn.prepareStatement("SELECT FROM client WHERE Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if(rs.next()) {
				Client client = new Client();
				client.setId(rs.getInt("ClientId"));
				client.setAddress(rs.getString("Address"));
				client.setName(rs.getString("Name"));
				client.setEmail(rs.getString("Email"));
			}
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
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
