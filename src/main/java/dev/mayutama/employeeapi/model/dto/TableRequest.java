package dev.mayutama.employeeapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TableRequest {
    private Integer page;
    private Integer size;
    private String sortBy;

    public Integer getPage() {
        return page != null && page > 0 ? page : 1;
    }

    public Integer getSize() {
        return size != null ? size : 10;
    }
}
