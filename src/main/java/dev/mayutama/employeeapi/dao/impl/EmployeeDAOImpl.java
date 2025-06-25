package dev.mayutama.employeeapi.dao.impl;

import dev.mayutama.employeeapi.dao.EmployeeDAO;
import dev.mayutama.employeeapi.exception.ApplicationException;
import dev.mayutama.employeeapi.model.dto.TableRequest;
import dev.mayutama.employeeapi.model.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Employee> getListForPagination(TableRequest tableReq, HttpServletRequest httpReq) {
        String hql = "FROM Employee e WHERE e.isDelete = 0";

        List<Employee> employeeList = entityManager.createQuery(hql, Employee.class)
                .setFirstResult((tableReq.getPage() - 1) * tableReq.getSize())
                .setMaxResults(tableReq.getSize())
                .getResultList();

        Long totalItem = count();
        Pageable pageable = PageRequest.of(tableReq.getPage(), tableReq.getSize());

        return new PageImpl<>(employeeList, pageable, totalItem);
    }

    @Override
    public Employee getById(Integer id) {
        if (id == null) return null;

        String hql = "FROM Employee e WHERE e.id = :id";
        TypedQuery<Employee> query = entityManager.createQuery(hql, Employee.class);
        query.setParameter("id", id);

        try {
            return query.getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

    @Override
    @Transactional
    public Employee insert(Employee employee) {
        employee.setIsDelete(0);
        entityManager.persist(employee);
        return employee;
    }

    @Override
    @Transactional
    public Employee update(Employee employee) {
        Employee currEmployee = getById(employee.getId());

        if (currEmployee == null) throw new ApplicationException(null, "Employee not found", HttpStatus.NOT_FOUND);

        currEmployee.setName(employee.getName());
        currEmployee.setGender(employee.getGender());
        currEmployee.setBirthDate(employee.getBirthDate());
        currEmployee.setPosition(employee.getPosition());
        currEmployee.setIdNumber(employee.getIdNumber());
        entityManager.merge(currEmployee);

        return currEmployee;
    }

    @Override
    @Transactional
    public Employee delete(Employee employee) {
        Employee currEmployee = getById(employee.getId());

        if (currEmployee == null) throw new ApplicationException(null, "Employee not found", HttpStatus.NOT_FOUND);
        currEmployee.setIsDelete(1);

        entityManager.merge(currEmployee);
        return currEmployee;
    }

    private Long count() {
        String hql = "SELECT COUNT(e) FROM Employee e";
        TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
        return query.getSingleResult();
    }
}
