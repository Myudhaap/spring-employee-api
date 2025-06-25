package dev.mayutama.employeeapi.controller;

import dev.mayutama.employeeapi.constant.AppPath;
import dev.mayutama.employeeapi.model.dto.EmployeeDTO;
import dev.mayutama.employeeapi.model.dto.RequestById;
import dev.mayutama.employeeapi.model.dto.Response;
import dev.mayutama.employeeapi.model.dto.TableRequest;
import dev.mayutama.employeeapi.model.entity.Employee;
import dev.mayutama.employeeapi.model.entity.Position;
import dev.mayutama.employeeapi.service.EmployeeService;
import dev.mayutama.employeeapi.service.PositionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AppPath.EMPLOYEE)
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    private final PositionService positionService;

    @GetMapping
    public ResponseEntity<?> index(
        @ModelAttribute TableRequest tableRequest,
        HttpServletRequest httpReq
    ){
        Page<Employee> employees = employeeService.getListForPagination(tableRequest, httpReq);
        return ResponseEntity.ok(employees);
    }

    @PostMapping("/add-edit")
    public ResponseEntity<?> addEdit(
        @RequestBody RequestById req
    ){
        List<Position> positionList = positionService.getList();
        Employee employee = employeeService.getById(req.getId());
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployee(employee);
        employeeDTO.setPositionList(positionList);

        return ResponseEntity.ok(employeeDTO);
    }

    @PostMapping
    public ResponseEntity<?> insert(
        @RequestBody Employee employee
    ) {
        Employee res = employeeService.insert(employee);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(Response.<Employee>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successful create employee")
                .data(res)
                .build()
            );
    }

    @PutMapping
    public ResponseEntity<?> update(
        @RequestBody Employee employee
    ) {
        Employee res = employeeService.update(employee);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(Response.<Employee>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successful update employee")
                .data(res)
                .build()
            );
    }

    @DeleteMapping
    public ResponseEntity<?> delete(
        @RequestBody Employee employee
    ) {
        Employee res = employeeService.delete(employee);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(Response.<Employee>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successful delete employee")
                .data(res)
                .build()
            );
    }
}
