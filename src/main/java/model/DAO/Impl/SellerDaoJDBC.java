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
import java.util.stream.Collectors;

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
                Department dep1 = instacieteDepartment(rs); //faço uma função de instantiation
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
        return List.of();
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        try {
            stPrep = conn.prepareStatement("SELECT seller. *, dep.nome AS depName " +
                    "FROM seller INNER JOIN department dep "
                    + "ON seller.department_id = dep.id "
                    + "WHERE department_id = ? "
                    + "ORDER BY nome");
            stPrep.setInt(1, department.getId());
            rs = stPrep.executeQuery();

            List<Seller> depSeller = new ArrayList<>();
            HashMap<Integer, Department> map = new HashMap<>();

            while (rs.next()) {
                Department dep = map.get(rs.getInt("department_id"));
                if (dep == null) {
                    dep = instacieteDepartment(rs);
                    map.put(rs.getInt("department_id"), dep); //chave: id & valor: departamento.
                }
                Seller obj = instantieteSeller(rs, dep);
                depSeller.add(obj);
            }
            List<Seller> listaOrdenadaId = depSeller.stream() //listaOrdenada pelo ID
                    .sorted(Comparator.comparing(Seller::getName))
                    .collect(Collectors.toList());
            listaOrdenadaId.forEach(System.out::println);

            return listaOrdenadaId;
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
    private Department instacieteDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("department_id"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }
}