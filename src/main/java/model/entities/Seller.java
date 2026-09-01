package model.entities;

import Enum.entities.WorkerLevel;

import java.util.Date;
import java.util.Objects;

public class Seller {
    private Integer id;
    private String name;
    private String email;
    private Date birthDate;
    private WorkerLevel senioridade;

    private Department dep;

    public Seller() {
    }

    public Seller(Integer id, String name, String email, Date birthDate, WorkerLevel senioridade, Department dep) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.senioridade = senioridade;
        this.dep = dep;
    }



    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
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
        return "Seller{" +
                "id=" + this.getId() +
                ", name='" + this.getName() + '\'' +
                ", email='" + this.getEmail() + '\'' +
                ", birthDate=" + this.getBirthDate() +
                ", senioridade=" + getSenioridade() +
                ", dep=" + this.getDep().getName() +
                '}';
    }
}
