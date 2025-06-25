package dev.mayutama.employeeapi.dao.impl;

import dev.mayutama.employeeapi.dao.PositionDao;
import dev.mayutama.employeeapi.model.entity.Position;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PositionDAOImpl implements PositionDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Position> getList() {
        String hql = "FROM Position p";
        return entityManager.createQuery(hql, Position.class)
                .getResultList();
    }
}
