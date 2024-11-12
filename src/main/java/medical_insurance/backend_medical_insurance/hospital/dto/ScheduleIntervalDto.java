package medical_insurance.backend_medical_insurance.hospital.dto;

import java.time.LocalTime;

public class ScheduleIntervalDto {
    public LocalTime startTime;
    public LocalTime endTime;
    public boolean isAvailable;
}