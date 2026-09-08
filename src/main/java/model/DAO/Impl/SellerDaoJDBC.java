package model.DAO.Impl;

import Enum.entities.WorkerLevel;
import db.DB;
import db.DbException;
import model.DAO.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class SellerDaoJDBC implements SellerDAO {

    PreparedStatement stPrep = null;
    Statement st = null;
    ResultSet rs = null;

    Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller seller) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            stPrep = conn.prepareStatement("INSERT INTO seller (nome, email, birthdate, base_salary, senioridade, department_id) VALUES (?,?,?,?,?,?)");
            stPrep.setString(1, seller.getName());
            stPrep.setString(2, seller.getEmail());
            java.util.Date dataUtil = sdf.parse(String.valueOf(seller.getBirthDate()));
            java.sql.Date dataSql = new java.sql.Date(dataUtil.getTime());
            stPrep.setDate(3, dataSql);
        } catch (SQLException | ParseException e) {
            throw new DbException(e.getMessage());
        }
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
            if (rs.next()) {
                Department dep1 = instantieteDepartment(rs); //faço uma função de instantiation
                Seller obj = instantieteSeller(rs, dep1); //faço uma função de instantiation
                System.out.println(" =-=-=-=-=-=- Vendedor com o id: " + id + " encontrado =-=-=-==-=-=-=\n" + obj);
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatementPrep(stPrep);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findAll() {
        List<Seller> depSeller = new ArrayList<>();
        HashMap<Integer, Department> map = new HashMap<>();
        try {
            st = conn.createStatement();
            //trago todos os vendedores de todos os departamentos.
            rs = st.executeQuery("SELECT seller.*, dep.nome AS depName "
                    + "FROM seller INNER JOIN department dep "
                    + "ON seller.department_id = dep.id "
                    + "ORDER BY id");
            while (rs.next()) {
                Department dep = map.get(rs.getInt("department_id"));
                if (dep == null) {
                    dep = instantieteDepartment(rs);
                    map.put(rs.getInt("department_id"), dep);
                }
                Seller obj = instantieteSeller(rs, dep);
                depSeller.add(obj);
            }
            System.out.println("\n=-=-=-=-=-=-=-= TODOS OS VENDEDORES E SEUS DEPARTAMENTOS =-=-=-=-=-=-=-=\n");
            depSeller.stream()
                    .sorted(Comparator.comparing(Seller::getId))
                    .forEach(System.out::println);

            return depSeller;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        List<Seller> depSeller = new ArrayList<>();
        HashMap<Integer, Department> map = new HashMap<>();
        try {
            stPrep = conn.prepareStatement("SELECT seller. *, dep.nome AS depName " +
                    "FROM seller INNER JOIN department dep "
                    + "ON seller.department_id = dep.id "
                    + "WHERE department_id = ? "
                    + "ORDER BY id");
            stPrep.setInt(1, department.getId());
            rs = stPrep.executeQuery();

            while (rs.next()) {
                Department dep = map.get(rs.getInt("department_id"));
                if (dep == null) {
                    dep = instantieteDepartment(rs);
                    map.put(rs.getInt("department_id"), dep); //chave: id & valor: departamento.
                }
                Seller obj = instantieteSeller(rs, dep);
                depSeller.add(obj);
            }
            System.out.println("Departamento: " + depSeller.get(0).getDep().getName());
            depSeller.stream()
                    .sorted(Comparator.comparing(Seller::getId))
                    .forEach(System.out::println);
            return depSeller;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatementPrep(stPrep);
            DB.closeResultSet(rs);
        }
    }

    //transformo minha tabela Seller em Objeto
    private Seller instantieteSeller(ResultSet rs, Department dep) throws SQLException {
        Seller obj = new Seller();
        obj.setId(rs.getInt("id"));
        obj.setName(rs.getString("nome"));
        obj.setEmail(rs.getString("email"));
        obj.setBirthDate(rs.getDate("birthdate"));
        obj.setBaseSalary(rs.getDouble("base_salary"));
        obj.setSenioridade(WorkerLevel.valueOf(rs.getString("senioridade")));
        obj.setDep(dep);
        return obj;
    }

    //transformo minha tabela Department em Objeto
    private Department instantieteDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("department_id"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }
}