package medical_insurance.backend_medical_insurance.hospital.repository;

import medical_insurance.backend_medical_insurance.common.enums.DayEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import medical_insurance.backend_medical_insurance.hospital.entity.ScheduleEntity;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, UUID> {
    @Query("SELECT s FROM ScheduleEntity s WHERE s.doctor.id = :doctorId AND s.dayOfWeek = :dayOfWeek")
    List<ScheduleEntity> findByDoctorIdAndDayOfWeek(@Param("doctorId") UUID doctorId, @Param("dayOfWeek") DayEnum dayOfWeek);
}
