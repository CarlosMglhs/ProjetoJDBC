package MainJDBC;

import Enum.entities.WorkerLevel;
import model.DAO.DaoFactory;
import model.DAO.Impl.SellerDaoJDBC;
import model.DAO.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static Enum.entities.WorkerLevel.*;

public class Main {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dataFixa = sdf.parse("20/08/1998");
        Scanner sc = new Scanner(System.in);
        SellerDAO sellerDao = DaoFactory.createSellerDAO();
        Department dep = new Department(3, null );

    }
}
