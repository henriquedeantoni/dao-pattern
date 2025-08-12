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
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;
import model.exceptions.NotFoundException;

public class SellerDaoJdbc implements SellerDao {

	private Connection conn;
	
	public SellerDaoJdbc(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Seller seller) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT * FROM seller WHERE Email = ?"					
					);
			
			st.setString(1, seller.getEmail());
			rs = st.executeQuery();
			
			if(rs.next()) {
				throw new DbException("Email has already registered!");
			}
			
			st = conn.prepareStatement(
				"INSERT INTO seller "
				+"(Name, Email, BirthDate, BaseSalary, DepartmentId) "
				+"VALUES "
				+"(? ,? ,? ,?, ?)",
				Statement.RETURN_GENERATED_KEYS
				);
			st.setString(1, seller.getName());
			st.setString(2, seller.getEmail());
			st.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
			st.setDouble(4, seller.getBaseSalary());
			st.setInt(5, seller.getDepartment().getId());
		
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				if(rs.next()) {
					int id = rs.getInt(1);
					seller.setId(id);
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
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Seller seller) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE seller "
 					+"SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
					+"WHERE Id = ?"
				);
			st.setString(1, seller.getName());
			st.setString(2, seller.getEmail());
			st.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
			st.setDouble(4, seller.getBaseSalary());
			st.setInt(5, seller.getDepartment().getId());
			st.setInt(6, seller.getId());
			
			st.executeUpdate();
			
		}
		catch(SQLException e) {
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
					"DELETE FROM seller WHERE Id = ?"
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
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT seller.*,department.Name as DepName "
				+ "FROM seller INNER JOIN department "
				+ "ON seller.DepartmentId = department.Id "
				+ "WHERE seller.Id = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if(rs.next()) {
				Department dep =  instantiateDepartment(rs);
				Seller seller = instantiateSeller(rs, dep);
				
				return seller;
			}
			return null;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		
		return dep;
	}
	
	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller seller = new Seller();
		seller.setId(rs.getInt("Id"));
		seller.setName(rs.getString("Name"));
		seller.setEmail(rs.getString("Email"));
		seller.setBirthDate(rs.getDate("BirthDate"));
		seller.setBaseSalary(rs.getDouble("BaseSalary"));
		seller.setDepartment(dep);
		
		return seller;
	}

	@Override
	public List<Seller> findAll() {

		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+"FROM seller INNER JOIN department "
					+"ON seller.DepartmentId = department.Id "
					+"ORDER BY Name"
					);

			rs = st.executeQuery();
			
			List<Seller> resultsList = new ArrayList<>();
			Map<Integer, Department> mapDepartment = new HashMap<>();
			
			while(rs.next()) {
				
				Department dep = mapDepartment.get(rs.getInt("DepartmentId"));
				
				if(dep==null) {
					dep = instantiateDepartment(rs);
					mapDepartment.put(rs.getInt("DepartmentId"), dep);
				}
				
				Seller seller = instantiateSeller(rs, dep);
				resultsList.add(seller);
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
	public List<Seller> findByDepartment(Department department) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+"FROM seller INNER JOIN department "
					+"ON seller.DepartmentId = department.Id "
					+"where DepartmentId = ? "
					+"ORDER BY Name"
					);
			
			st.setInt(1, department.getId());
			rs = st.executeQuery();
			
			List<Seller> resultsList = new ArrayList<>();
			Map<Integer, Department> mapDepartment = new HashMap<>();
			
			while(rs.next()) {
				
				Department dep = mapDepartment.get(rs.getInt("DepartmentId"));
				
				if(dep==null) {
					dep = instantiateDepartment(rs);
					mapDepartment.put(rs.getInt("DepartmentId"), dep);
				}
				
				Seller seller = instantiateSeller(rs, dep);
				resultsList.add(seller);
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
	
	public List<Seller> findBySalaryMinimum(Double minValSalary){
		
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+"FROM seller INNER JOIN department "
					+"ON seller.DepartmentId = department.Id "
					+"WHERE baseSalary >= ? "
					+"ORDER by name"
					);
			
			st.setDouble(1, minValSalary);
			rs = st.executeQuery();
			List<Seller> resultsListBySalary = new ArrayList<>();
			Map<Integer, Department> mapDepartment = new HashMap<>();
			
			while(rs.next()) {
				
				Department dep = mapDepartment.get(rs.getInt("DepartmentId"));
				
				if(dep==null) {
					dep = instantiateDepartment(rs);
					mapDepartment.put(rs.getInt("DepartmentId"), dep);
				}
				
				Seller seller = instantiateSeller(rs, dep);
				resultsListBySalary.add(seller);				
			}
			return resultsListBySalary;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	public List<Seller> findBySalaryRange(Double minValSalary, Double maxValSalary){
		
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+"FROM seller INNER JOIN department "
					+"ON seller.DepartmentId = department.Id "
					+"WHERE baseSalary >= ? AND baseSalary < ? "
					+"ORDER by name"
					);
			
			st.setDouble(1, minValSalary);
			st.setDouble(2, maxValSalary);
			rs = st.executeQuery();
			List<Seller> resultsListBySalary = new ArrayList<>();
			Map<Integer, Department> mapDepartment = new HashMap<>();
			
			while(rs.next()) {
				
				Department dep = mapDepartment.get(rs.getInt("DepartmentId"));
				
				if(dep==null) {
					dep = instantiateDepartment(rs);
					mapDepartment.put(rs.getInt("DepartmentId"), dep);
				}
				
				Seller seller = instantiateSeller(rs, dep);
				resultsListBySalary.add(seller);				
			}
			return resultsListBySalary;
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
	public List<Seller> findByMonth(int month) {
		// TODO Auto-generated method stub
		return null;
	}
}
