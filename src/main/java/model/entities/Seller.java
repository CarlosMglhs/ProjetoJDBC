package model.entities;

import Enum.entities.WorkerLevel;

import java.util.Date;
import java.util.Objects;

public class Seller {
    private Integer id;
    private String name;
    private String email;
    private Date birthDate;
    private Double baseSalary;
    private WorkerLevel senioridade;

    private Department dep;

    public Seller() {
    }

    public Seller(Integer id, String name, String email, Date birthDate, Double baseSalary, WorkerLevel senioridade, Department dep) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.baseSalary = baseSalary;
        this.senioridade = senioridade;
        this.dep = dep;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(Double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public WorkerLevel getSenioridade() {
        return senioridade;
    }

    public void setSenioridade(WorkerLevel senioridade) {
        this.senioridade = senioridade;
    }

    public Department getDep() {
        return dep;
    }

    public void setDep(Department dep) {
        this.dep = dep;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Seller seller = (Seller) o;
        return Objects.equals(getId(), seller.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "\nid = " + this.getId() +
                "\nNome = " + this.getName() +
                "\nEmail = " + this.getEmail() +
                "\nBirth_Date = " + this.getBirthDate() +
                "\nSenioridade = " + getSenioridade() +
                "\nDepartamento = " + this.getDep().getName();
    }
}
