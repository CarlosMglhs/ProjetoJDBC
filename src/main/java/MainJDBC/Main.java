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

public class Main {
    public static void main(String[] args) throws ParseException {
        SellerDAO sellerDao = DaoFactory.createSellerDAO();
        Seller seller = sellerDao.findById(2);

        Department dep = new Department(1, null);
        List<Seller> list = sellerDao.findByDepartment(dep);

    }
}
