package dev.mayutama.employeeapi.model.entity;

import dev.mayutama.employeeapi.constant.DbPath;
import jakarta.persistence.*;
import org.hibernate.annotations.Check;

import java.time.LocalDate;

@Entity
@Table(name = DbPath.EMPLOYEE)
@Check(constraints = "IS_DELETE IN (0, 1)")
@Check(constraints = "GENDER IN (1, 2)")
public class Employee {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100, name = "NAME", nullable = false)
    private String name;

    @Column(name = "BIRTH_DATE", nullable = false)
    private LocalDate birthDate;

    @ManyToOne
    @JoinColumn(name = "POSITION_ID", nullable = false)
    private Position position;

    @Column(name = "ID_NUMBER", nullable = false)
    private Integer idNumber;

    @Column(name = "GENDER", nullable = false)
    private Integer gender;

    @Column(name = "IS_DELETE", nullable = false)
    private Integer isDelete;

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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Integer getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(Integer idNumber) {
        this.idNumber = idNumber;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}
