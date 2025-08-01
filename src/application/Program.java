package application;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.println("## First Test: Seller Find By Id ##");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);

		System.out.println("## Second Test: Seller Find By Department");
		Department departmentTest = new Department(2, null);
		List<Seller> listSeller = sellerDao.findByDepartment(departmentTest);
		for (Seller s : listSeller) {
			System.out.println(s);
		}
		
		System.out.println("## Third Test: Seller Find All");
		List<Seller> listSellerFindAll = sellerDao.findAll();
		for (Seller s : listSellerFindAll) {
			System.out.println(s);
		}
		
		System.out.println("## Fourth Test: Seller insert");
		Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 5400.00, departmentTest);
		sellerDao.insert(newSeller);
		System.out.println("Inserted! new Id = " + newSeller.getId());
		

		System.out.println("## Fifth Test: Seller update");
		seller = sellerDao.findById(1);
		seller.setName("Felipe");
		sellerDao.update(seller);
		System.out.println("Updated complete");
		
		System.out.println("## Sixth Test: Seller delete");
		sellerDao.deleteById(9);
		System.out.println("Delete completed");
		
	}

}
