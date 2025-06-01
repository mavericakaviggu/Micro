package com.project.authService.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.project.authService.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByVerifyOtp(String verifyOtp);

    Optional<UserEntity> findByResetOtp(String resetOtp);

    Boolean existsByEmail(String email);

}
