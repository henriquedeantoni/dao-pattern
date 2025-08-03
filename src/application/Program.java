package application;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		
		System.out.println("## Test: Seller Find By Id ##");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);

		System.out.println("## Test: Seller Find By Department");
		Department departmentTest = new Department(2, null);
		List<Seller> listSeller = sellerDao.findByDepartment(departmentTest);
		for (Seller s : listSeller) {
			System.out.println(s);
		}
		
		System.out.println("## Test: Seller Find All");
		List<Seller> listSellerFindAll = sellerDao.findAll();
		for (Seller s : listSellerFindAll) {
			System.out.println(s);
		}
		
		System.out.println("## Test: Seller insert");
		Seller newSeller = new Seller(null, "Joshua", "joshua@gmail.com", new Date(), 3800.00, departmentTest);
		sellerDao.insert(newSeller);
		System.out.println("Inserted! new Id = " + newSeller.getId());
		

		System.out.println("## Test: Seller find by Id");
		seller = sellerDao.findById(1);
		seller.setName("Felipe");
		sellerDao.update(seller);
		System.out.println("Updated complete");
		
		System.out.println("## Test: Seller find by Min Salary Value");
		List<Seller> listSellerBySalary = sellerDao.findBySalaryMinimum(5000.00);
		for(Seller s1 : listSellerBySalary) {
			System.out.println(s1);
		}
		
		System.out.println("## Test: Seller find by Salary Range");
		List<Seller> listSellerBySalaryRange = sellerDao.findBySalaryRange(3500.00, 4200.00);
		for(Seller s1 : listSellerBySalaryRange) {
			System.out.println(s1);
		}
		
		/*
		System.out.println("## Test: Seller delete");
		sellerDao.deleteById(9);
		System.out.println("Delete completed");
		*/
		
		System.out.println("## Test: Department insert");
		Department department = new Department(null, "Juridico");
		departmentDao.insert(department);
		System.out.println("Inserted! new Id = " + newSeller.getId());
	}

}
