package model.DAO;

import db.DB;
import model.DAO.Impl.SellerDaoJDBC;
import model.entities.Seller;

public class DaoFactory {
    public static SellerDAO createSellerDAO(){
        return new SellerDaoJDBC(DB.getConnection());
    }
}
