package dev.mayutama.employeeapi.service.impl;

import dev.mayutama.employeeapi.dao.PositionDao;
import dev.mayutama.employeeapi.model.entity.Position;
import dev.mayutama.employeeapi.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {
    private final PositionDao positionDao;

    @Override
    public PositionDao getDAO() {
        return positionDao;
    }

    @Override
    public List<Position> getList() {
        return getDAO().getList();
    }
}
