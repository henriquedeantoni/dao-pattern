package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;
import model.exceptions.NotFoundException;

public class DepartmentDaoJdbc implements DepartmentDao {

	private Connection conn;
	
	public DepartmentDaoJdbc(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Department department) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"INSERT INTO Department "
					+"(Name) "
					+"VALUES "
					+"(?)",
					Statement.RETURN_GENERATED_KEYS
					);
			st.setString(1, department.getName());
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected>0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					department.setId(id);
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
	public void update(Department obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE department "
					+ "SET (name = ?) "
					+ "WHERE id = ?"
					);
			st.setString(1, obj.getName());
			st.setInt(1, obj.getId());
			
			st.executeUpdate();
		}
		catch(SQLException e)
		{
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
			st = conn.prepareStatement(
					"DELETE FROM department WHERE Id = ?"
					);
			
			st.setInt(1, id);
			
			int rows = st.executeUpdate();
			
			if(rows == 0) {
				throw new NotFoundException();
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Department findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Department> findAll() {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT * FROM department "
					+"ORDER BY name"
					);
			
			rs = st.executeQuery();
			
			List<Department> listDepartment = new ArrayList<>();
			
			while(rs.next())
			{
				listDepartment.add(new Department(rs.getInt("DepartmentId"), rs.getString("DepartmentName")));
			}
			
			return listDepartment;
			
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
