package dev.mayutama.employeeapi.model.entity;

import dev.mayutama.employeeapi.constant.DbPath;
import jakarta.persistence.*;
import org.hibernate.annotations.Check;

@Entity
@Table(name = DbPath.POSITION)
@Check(constraints = "IS_DELETE IN (0, 1)")
public class Position {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, name = "CODE", nullable = false)
    private String code;

    @Column(length = 100, name = "NAME", nullable = false)
    private String name;

    @Column(name = "IS_DELETE", nullable = false)
    private Integer isDelete;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}
