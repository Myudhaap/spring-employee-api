package dev.mayutama.employeeapi.model.dto;

import dev.mayutama.employeeapi.model.entity.Employee;
import dev.mayutama.employeeapi.model.entity.Position;

import java.util.List;

public class EmployeeDTO {
    private List<Position> positionList;
    private Employee employee;

    public List<Position> getPositionList() {
        return positionList;
    }

    public void setPositionList(List<Position> positionList) {
        this.positionList = positionList;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
