package model.DAO.Impl;

import db.DB;
import db.DbException;
import model.DAO.DepartmentDAO;
import model.entities.Department;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDAO {

    PreparedStatement stPrep = null;
    Statement st = null;
    ResultSet rs = null;

    Connection conn;

    public DepartmentDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public  void insertDep(Department dep) {
        try {
            stPrep = conn.prepareStatement("INSERT INTO department (id, nome) "
                    + "VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            stPrep.setInt(1, dep.getId());
            stPrep.setString(2, dep.getName());
            stPrep.executeUpdate();
            ResultSet rs = stPrep.getGeneratedKeys();
            if(rs.next()){
                System.out.println("\n=-=-==-=-= DEPARTAMENTO INSERIDO COM SUCESSO =-=-=-=-=-=\n");
                Department dep1 = instantieteDep(rs);
                System.out.println(dep1);
            }
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatementPrep(stPrep);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public void updateDep(Department dep) {
        try {
            stPrep = conn.prepareStatement("UPDATE department "
                    + "SET nome = ? "
                    + "WHERE id = ?");
            stPrep.setString(1, dep.getName());
            stPrep.setInt(2, dep.getId());
            int rowsAffect = stPrep.executeUpdate();
            if(rowsAffect > 0){
                    System.out.println("\n =-=-=-=-=-=-= Departamento atualizado com sucesso!! =-=-=-=-=-=-= \n");
                    System.out.println(dep);
            }else{
                System.out.println("DEPARTAMENTO NÃO ENCONTRADO OU JÁ FOI ATUALIZADO ANTERIORMENTE");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatementPrep(stPrep);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public void deleteDep(Integer id) {
        try {
            stPrep = conn.prepareStatement("DELETE FROM department "
                    + "WHERE id = ?");
            stPrep.setInt(1, id);
            int rowsAffected = stPrep.executeUpdate();
            if(rowsAffected > 0){
                System.out.println("=-=-=-=-=-= Departamento do id " + id + " deletado com sucesso!! =-=-=-=-=");
            }else {
                System.out.println("Nenhum departamento encontrado com o id " + id + " para deletar.");
            }
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatementPrep(stPrep);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public Department searchById(Integer id) {
        try {
            stPrep = conn.prepareStatement("SELECT department.* "
                    + "FROM department "
                    + "WHERE department.id = ?");
            stPrep.setInt(1, id);
            rs = stPrep.executeQuery();
            if(rs.next()){
                System.out.println("DEPARTAMENTO ENCONTRADO");
                Department dep = instantieteDep(rs);
                System.out.println(dep);
                return dep;
            }else {
                System.out.println("DEPARTAMENTO NÃO ENCONTRADO");
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
    public List<Department> findAll() {
        List<Department> depList = new ArrayList<>();
        try {
            st = conn.createStatement();
            rs = st.executeQuery("SELECT department. * "
                    + "FROM department");
            while(rs.next()){
                Department dep = instantieteDep(rs);
                depList.add(dep);
            }
            System.out.println("\n=-=-=-=-= LISTANDO TODOS OS DEPARTAMENTOS =-=-=-=-=\n");
            depList.stream()
                    .sorted(Comparator.comparing(Department::getId))
                    .forEach(System.out::println);
            return depList;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    public Department sout(Integer id){
        return searchById(id);
    }

    private Department instantieteDep(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("id"));
        dep.setName(rs.getString("nome"));
        return dep;
    }

    private static void closeConnection(){
        DB.closeConnection();
    }
}
