package model.DAO;
import model.entities.Department;
import model.entities.Seller;

import java.sql.SQLException;
import java.util.List;

public interface SellerDAO {
    void insert(Seller seller) throws SQLException;
    void update(Seller seller);
    void deleteById(Integer id);
    Seller findById(Integer id);

    List<Seller> findAll();
}
