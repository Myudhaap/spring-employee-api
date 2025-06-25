package dev.mayutama.employeeapi.service;

import dev.mayutama.employeeapi.dao.EmployeeDAO;
import dev.mayutama.employeeapi.model.dto.TableRequest;
import dev.mayutama.employeeapi.model.entity.Employee;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

public interface EmployeeService {
    EmployeeDAO getDAO();
    Page<Employee> getListForPagination(TableRequest tableReq, HttpServletRequest httpReq);
    Employee getById(Integer id);
    Employee insert(Employee employee);
    Employee update(Employee employee);
    Employee delete(Employee employee);
}
