package dev.mayutama.employeeapi.service.impl;

import dev.mayutama.employeeapi.dao.EmployeeDAO;
import dev.mayutama.employeeapi.model.dto.TableRequest;
import dev.mayutama.employeeapi.model.entity.Employee;
import dev.mayutama.employeeapi.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeDAO employeeDAO;

    @Override
    public EmployeeDAO getDAO() {
        return employeeDAO;
    }

    @Override
    public Page<Employee> getListForPagination(TableRequest tableReq, HttpServletRequest httpReq) {
        return getDAO().getListForPagination(tableReq, httpReq);
    }

    @Override
    public Employee getById(Integer id) {
        return getDAO().getById(id);
    }

    @Override
    public Employee insert(Employee employee) {
        return getDAO().insert(employee);
    }

    @Override
    public Employee update(Employee employee) {
        return getDAO().update(employee);
    }

    @Override
    public Employee delete(Employee employee) {
        return getDAO().delete(employee);
    }
}
