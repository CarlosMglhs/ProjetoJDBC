package model.DAO.Impl;

import Enum.entities.WorkerLevel;
import db.DB;
import db.DbException;
import model.DAO.SellerDAO;
import model.entities.Department;
import model.entities.Seller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SellerDaoJDBC implements SellerDAO {

    PreparedStatement stPrep = null;
    ResultSet rs = null;

    Connection conn;
    public SellerDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Seller seller) {

    }

    @Override
    public void update(Seller seller) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {

        try {
            stPrep = conn.prepareStatement(
                    "SELECT seller. *, dep.nome as DepName "
                            + "FROM seller INNER JOIN department dep "
                            + "ON seller.department_id = dep.id "
                            + "WHERE seller.id = ?");

            stPrep.setInt(1, id);
            rs = stPrep.executeQuery();
            Seller obj = null;
            if (rs.next()) {
                Department dep = new Department();
                dep.setId(rs.getInt("department_id"));
                dep.setName(rs.getString("DepName"));

                obj = new Seller();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("nome"));
                obj.setEmail(rs.getString("email"));
                obj.setBirthDate(rs.getDate("birthdate"));
                obj.setBaseSalary(rs.getDouble("base_salary"));
                obj.setSenioridade(WorkerLevel.valueOf(rs.getString("senioridade")));
                obj.setDep(dep);
                System.out.println(obj);
                return obj;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatementPrep(stPrep);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findAll() {
        return List.of();
    }
}


//LEMBRAR DE COMMITAR