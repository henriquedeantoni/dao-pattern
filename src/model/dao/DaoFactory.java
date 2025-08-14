package model.dao;

import db.DB;
import model.dao.impl.ClientDaoJdbc;
import model.dao.impl.DepartmentDaoJdbc;
import model.dao.impl.OrderDaoJdbc;
import model.dao.impl.SellerDaoJdbc;

public class DaoFactory {
	
	public static SellerDao createSellerDao() {
		return new SellerDaoJdbc(DB.getConnection());
	}
	
	public static DepartmentDao createDepartmentDao() {
		return new DepartmentDaoJdbc(DB.getConnection());
	}
	
	public static ClientDao createClientDao() {
		return new ClientDaoJdbc(DB.getConnection());
	}
	
	public static OrderDao createOrderDao() {
		return new OrderDaoJdbc(DB.getConnection());
	}
}
