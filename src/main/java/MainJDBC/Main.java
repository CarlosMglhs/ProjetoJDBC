package MainJDBC;

import Enum.entities.WorkerLevel;
import model.DAO.DaoFactory;
import model.DAO.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ParseException {
        Scanner sc = new Scanner(System.in);
        SellerDAO sellerDao = DaoFactory.createSellerDAO();
        Seller seller = sellerDao.findById(2);
        //System.out.println("DIGITE O ID DO DEPARTAMENTO: ");
        //int dep = sc.nextInt();
        Department dep = new Department(1, null );
        sellerDao.findByDepartment(dep);
        sellerDao.findAll();
    }
}
