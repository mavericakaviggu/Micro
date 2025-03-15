package com.project.departmentService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

//Department model info for swagger UI using @schema for documentation
@Schema(description = "Department DTO model information")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDto {
    private long id;
    @Schema(description = "Department Name")
    private String departmentName;
    @Schema(description = "Department Description")
    private String departmentDescription;
    @Schema(description = "Department Code")
    private String departmentCode;

}
