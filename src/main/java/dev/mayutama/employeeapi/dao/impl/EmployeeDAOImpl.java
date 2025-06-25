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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
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
        Pageable pageable = PageRequest.of(tableReq.getPage() - 1, tableReq.getSize());

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
        String sql = """
                INSERT INTO t2_employee (name, birth_date, position_id, id_number, gender, is_delete)
                VALUES (:name, :birthDate, :positionId, :idNumber, :gender, 0) RETURNING id
                """;
        Integer idEmployee = (Integer) entityManager.createNativeQuery(sql)
                .setParameter("name", employee.getName())
                .setParameter("gender", employee.getGender())
                .setParameter("birthDate", employee.getBirthDate())
                .setParameter("positionId", employee.getPosition().getId())
                .setParameter("idNumber", employee.getIdNumber())
                .getSingleResult();

        return getById(idEmployee);
    }

    @Override
    @Transactional
    public Employee update(Employee employee) {
        Employee currEmployee = getById(employee.getId());

        if (currEmployee == null) throw new ApplicationException(null, "Employee not found", HttpStatus.NOT_FOUND);

        String hql = """
                UPDATE Employee e
                SET
                e.name = :name,
                e.gender = :gender,
                e.birthDate = :birthDate,
                e.position = :position,
                e.idNumber = :idNumber
                WHERE e.id = :id
                """;

        int rowExecute = entityManager.createQuery(hql)
                .setParameter("name", employee.getName() != null ? employee.getName() : currEmployee.getName())
                .setParameter("gender", employee.getGender() != null ? employee.getGender() : currEmployee.getGender())
                .setParameter("birthDate", employee.getBirthDate() != null ? employee.getBirthDate() : currEmployee.getBirthDate())
                .setParameter("position", employee.getPosition() != null ? employee.getPosition() : currEmployee.getPosition())
                .setParameter("idNumber", employee.getIdNumber() != null ? employee.getIdNumber() : currEmployee.getIdNumber())
                .setParameter("id", currEmployee.getId())
                .executeUpdate();

        if (rowExecute == 0) throw new ApplicationException(null, "Update error", HttpStatus.BAD_REQUEST);

        entityManager.refresh(currEmployee);
        return currEmployee;
    }

    @Override
    @Transactional
    public Employee delete(Employee employee) {
        Employee currEmployee = getById(employee.getId());

        if (currEmployee == null) throw new ApplicationException(null, "Employee not found", HttpStatus.NOT_FOUND);

        /* Delete hql
        String hql = "DELETE FROM Employee e WHERE e.id = :id";
        entityManager.createQuery(hql)
                .setParameter("id", currEmployee.getId())
                .executeUpdate();
         */

        String hql = """
                UPDATE Employee e
                SET
                e.isDelete = :isDelete
                WHERE e.id = :id
                """;

        int rowExecute = entityManager.createQuery(hql)
                .setParameter("isDelete", 1)
                .setParameter("id", currEmployee.getId())
                .executeUpdate();

        if (rowExecute == 0) throw new ApplicationException(null, "Delete error", HttpStatus.BAD_REQUEST);

        entityManager.refresh(currEmployee);
        return currEmployee;
    }

    private Long count() {
        String hql = "SELECT COUNT(e) FROM Employee e WHERE e.isDelete = 0";
        TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
        return query.getSingleResult();
    }
}
