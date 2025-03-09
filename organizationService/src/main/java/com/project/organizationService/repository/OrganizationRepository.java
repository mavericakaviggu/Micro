package com.project.organizationService.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.organizationService.entity.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    Optional<Organization> findByOrganizationCode(String organizationCode);


}
