package medical_insurance.backend_medical_insurance.hospital.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class WeeklyScheduleDto {
    public LocalDate date;
    public String day; // Name of the day
    public List<ScheduleIntervalDto> hours; // List of time intervals
}
