package medical_insurance.backend_medical_insurance.hospital.dto;

import medical_insurance.backend_medical_insurance.hospital.entity.ScheduleEntity;

import java.util.List;
import java.util.UUID;

public class DoctorDto {
    public String licenseNumber;
    public UUID specialtyId;
    public List<ScheduleDto> scheduleIds;
    public UUID userId;
    public DoctorDto(String licenseNumber, List<ScheduleDto> scheduleIds, UUID specialtyId, UUID userId) {
        this.licenseNumber = licenseNumber;
        this.specialtyId = specialtyId;
        this.scheduleIds = scheduleIds;
        this.userId = userId;
    }
}
