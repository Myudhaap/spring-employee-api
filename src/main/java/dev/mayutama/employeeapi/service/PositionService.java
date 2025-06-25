package dev.mayutama.employeeapi.service;

import dev.mayutama.employeeapi.dao.PositionDao;
import dev.mayutama.employeeapi.model.entity.Position;

import java.util.List;

public interface PositionService {
    PositionDao getDAO();
    List<Position> getList();
}
