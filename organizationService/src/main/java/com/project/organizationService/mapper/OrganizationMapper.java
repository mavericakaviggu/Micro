package com.project.organizationService.mapper;

import com.project.organizationService.dto.OrganizationDto;
import com.project.organizationService.entity.Organization;

public class OrganizationMapper {

    // Converting organization JPA entity to DTO
    public static OrganizationDto mapToOrganizationDto(Organization organization) {
        OrganizationDto organizationDto = new OrganizationDto(
                organization.getId(),
                organization.getOrganizationName(),
                organization.getOrganizationDescription(),
                organization.getOrganizationCode(),
                organization.getCreatedDate()
        );
        return organizationDto;
    }

    //Converting organization DTO into JPA entity
    public static Organization mapToOrganization(OrganizationDto organizationDto) {
        Organization organization = new Organization(
                organizationDto.getId(),
                organizationDto.getOrganizationName(),
                organizationDto.getOrganizationDescription(),
                organizationDto.getOrganizationCode(),
                organizationDto.getCreatedDate()
        );
        return organization;
    }
}
