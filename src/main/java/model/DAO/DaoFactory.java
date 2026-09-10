package model.DAO;

import db.DB;
import model.DAO.Impl.DepartmentDaoJDBC;
import model.DAO.Impl.SellerDaoJDBC;
import model.entities.Department;

public class DaoFactory {

    public static SellerDAO createSellerDAO(){
        return new SellerDaoJDBC(DB.getConnection());
    }

    public static DepartmentDAO createDepDAO(){
        return new DepartmentDaoJDBC(DB.getConnection());
    }

}
