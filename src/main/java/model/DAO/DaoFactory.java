package model.DAO;

import db.DB;
import model.DAO.Impl.SellerDaoJDBC;

public class DaoFactory {

    public static SellerDAO createSellerDAO(){
        return new SellerDaoJDBC(DB.getConnection());
    }

}
