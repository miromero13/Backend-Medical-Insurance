package medical_insurance.backend_medical_insurance.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import medical_insurance.backend_medical_insurance.hospital.entity.DoctorEntity;

import javax.print.Doc;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, UUID> {
    @Query("SELECT d FROM DoctorEntity d LEFT JOIN FETCH d.user WHERE d.id = :id")
    Optional<DoctorEntity> findByIdWithUser(@Param("id") UUID id);
}
