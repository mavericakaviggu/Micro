package com.project.organizationService.service;

import com.project.organizationService.dto.OrganizationDto;

import java.util.List;

public interface OrganizationService {
    OrganizationDto saveOrganization(OrganizationDto organizationDto);
    OrganizationDto getOrganizationByCode(String organizationCode);
    List<OrganizationDto> getAllOrganizations();

}



    


