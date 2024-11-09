package medical_insurance.backend_medical_insurance.hospital.dto;

import java.time.LocalTime;
import medical_insurance.backend_medical_insurance.common.enums.DayEnum;

public class ScheduleDto {
    public DayEnum dayOfWeek;
    public LocalTime startTime;
    public LocalTime endTime;

    public ScheduleDto(DayEnum dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}

