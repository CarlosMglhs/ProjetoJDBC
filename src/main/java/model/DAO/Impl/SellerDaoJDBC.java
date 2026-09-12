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
import java.util.stream.Stream;

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
        try {
            stPrep = conn.prepareStatement("INSERT INTO seller (nome, email, birthdate, base_salary, senioridade, department_id) "
                    + "VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            stPrep.setString(1, seller.getName());
            stPrep.setString(2, seller.getEmail());
            java.sql.Date dataSql = new java.sql.Date(seller.getBirthDate().getTime());
            stPrep.setDate(3, dataSql);
            stPrep.setDouble(4, seller.getBaseSalary());
            stPrep.setString(5, String.valueOf(seller.getSenioridade()));
            stPrep.setInt(6, seller.getDep().getId());
            stPrep.executeUpdate();

            ResultSet rs = stPrep.getGeneratedKeys();

            if (rs.next()) {
                // 2. Extrai o ID novo (primeira coluna do resultado)
                int idGerado = rs.getInt(1);
                // 3. Coloca esse ID novo dentro do seu objeto seller (deixa de ser null)
                seller.setId(idGerado);
                Department dep = new Department(seller.getId(), seller.getName());
                Seller sell = instantieteSeller(rs, dep);
                System.out.println("\n=-=-=-=-=-=-= NOVO FUNCIONARIO CADASTRADO =-=-=-=-=-=-=\n" + sell);
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void update(Seller seller) {
        try {
            stPrep = conn.prepareStatement("UPDATE seller "
                    + "SET nome = ?,  base_salary = ?, senioridade = ? "
                    + "WHERE seller.id = ?");
            stPrep.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {
        try {
            stPrep = conn.prepareStatement("DELETE FROM seller "
                    + "WHERE id = ?");
            stPrep.setInt(1, id);
            int rowsAffect = stPrep.executeUpdate();
            if(rowsAffect > 0){
                System.out.println("=-=-=-=-=-= Usuário do id " + id + "deletado com sucesso!! =-=-=-=-=");

            }else {
                System.out.println("Nenhum usuário encontrado com o id " + id + " para deletar.");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    public Seller teste(Integer id){
        System.out.println("\n=-=-=-=-=-VENDEDOR ENCONTRADO COM SUCESSO!! =-=-=-=-=-\n");
        return findById(id);
    }

    @Override
    public Seller findById(Integer id) {
        try {
            stPrep = conn.prepareStatement(
                    "SELECT seller. *, dep.nome as DepName "
                            + "FROM seller INNER JOIN department dep "
                            + "ON seller.department_id = dep.id "
                            + "WHERE seller.id = ?", Statement.RETURN_GENERATED_KEYS);

            stPrep.setInt(1, id);
            rs = stPrep.executeQuery();
            if (rs.next()) {
                Department dep1 = instantieteDepartment(rs); //faço uma função de instantiation
                Seller obj = instantieteSeller(rs, dep1); //faço uma função de instantiation
                System.out.println(" =-=-=-=-=-=- Vendedor com o id: " + id + " =-=-=-==-=-=-=\n" + obj);
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
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
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
            System.out.println("\nDepartamento: " + depSeller.get(0).getDep().getName());
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