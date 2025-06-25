package dev.mayutama.employeeapi.dao;

import dev.mayutama.employeeapi.model.entity.Position;

import java.util.List;

public interface PositionDao {
    List<Position> getList();
}
