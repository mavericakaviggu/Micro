package com.project.organizationService.service.impl;

import com.project.organizationService.dto.OrganizationDto;
import com.project.organizationService.entity.Organization;
import com.project.organizationService.mapper.OrganizationMapper;
import com.project.organizationService.repository.OrganizationRepository;
import com.project.organizationService.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public OrganizationDto saveOrganization(OrganizationDto organizationDto) {
        Organization organization = OrganizationMapper.mapToOrganization(organizationDto);
        Organization savedOrganization = organizationRepository.save(organization);
        organizationDto = OrganizationMapper.mapToOrganizationDto(savedOrganization);
        return organizationDto;
    }

    @Override
    public OrganizationDto getOrganizationByCode(String organizationCode) {
        Optional<Organization> organization = organizationRepository.findByOrganizationCode(organizationCode);
        if (organization.isPresent()) {
            return OrganizationMapper.mapToOrganizationDto(organization.get());
        }
        return null;
    }

    @Override
    public List<OrganizationDto> getAllOrganizations() {
        List<Organization> organizations = organizationRepository.findAll();
        return organizations.stream()
            .map(OrganizationMapper::mapToOrganizationDto)  
            .collect(Collectors.toList());
        }
        


    // @Override
    // public OrganizationDto updateOrganization(OrganizationDto organizationDto) {
    //     Optional<Organization> organization = organizationRepository.findById(organizationDto.getId());
    //     if (organization.isPresent()) {
    //         Organization updatedOrganization = organizationRepository.save(OrganizationMapper.mapToOrganization(organizationDto));
    //         return OrganizationMapper.mapToOrganizationDto(updatedOrganization);
    //     }
    //     return null;
    // }

    // @Override
    // public void deleteOrganization(Long id) {
    //     organizationRepository.deleteById(id);
    // }

}
