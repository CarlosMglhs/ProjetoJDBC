package MainJDBC;

import Enum.entities.WorkerLevel;
import model.entities.Department;
import model.entities.Seller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Department dep = new Department(1, "Computers");
        Seller seller = new Seller(1, "Carlos", "carlos@gmail.com.br", sdf.parse("25/07/2002"), WorkerLevel.SENIOR , dep);

        System.out.println(seller);
        System.out.println("DADOS A PARTE \nNome: " + seller.getName().toUpperCase()
                + "\nDepartamento: " +seller.getDep().getName());

    }
}
