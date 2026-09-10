package model.DAO;

import model.entities.Department;

import java.util.List;


public interface DepartmentDAO {
    void insertDep(Department dep);
    void updateDep(Department dep);
    void deleteDep(Integer id);
    Department searchById(Integer id);
    List<Department> findAll();
}
