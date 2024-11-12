package medical_insurance.backend_medical_insurance.hospital.dto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

@Schema(description = "Datos del doctor", example = """
{
  "licenseNumber": "123456789",
  "specialtyId": "550e8400-e29b-41d4-a716-446655440000",
  "scheduleIds": [
    {
      "dayOfWeek": "MONDAY",
      "startTime": "08:00:00",
      "endTime": "12:00:00"
    }
  ],
  "userId": "123e4567-e89b-12d3-a456-426614174000"
}
""")
public class DoctorDto {

    @Schema(description = "Número de licencia único del doctor",
            example = "123456789")
    public String licenseNumber;

    @Schema(description = "ID de la especialidad médica asociada al doctor",
            example = "550e8400-e29b-41d4-a716-446655440000")
    public UUID specialtyId;

    @Schema(description = "Lista de horarios asociados al doctor")
    public List<ScheduleDto> scheduleIds;

    @Schema(description = "ID del usuario asociado al doctor",
            example = "123e4567-e89b-12d3-a456-426614174000")
    public UUID userId;



    public DoctorDto(String licenseNumber, List<ScheduleDto> scheduleIds, UUID specialtyId, UUID userId) {
        this.licenseNumber = licenseNumber;
        this.specialtyId = specialtyId;
        this.scheduleIds = scheduleIds;
        this.userId = userId;
    }
}
