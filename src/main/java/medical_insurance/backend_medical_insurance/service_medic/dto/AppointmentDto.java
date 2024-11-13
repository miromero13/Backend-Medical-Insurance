package medical_insurance.backend_medical_insurance.service_medic.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class AppointmentDto {
    public UUID doctorId;
    public UUID patientId;
    public LocalDateTime appointmentDate;

    public AppointmentDto(UUID doctorId, UUID patientId, LocalDateTime appointmentDate) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.appointmentDate = appointmentDate;
    }
}
