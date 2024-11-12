package medical_insurance.backend_medical_insurance.common.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;
import java.util.Optional;

@Schema(description = "Estados posibles de una cita médica")
public enum AppointmentStatusEnum {

    @Schema(description = "Cita programada", example = "programado")
    SCHEDULE("programado"),

    @Schema(description = "Cita completada", example = "completado")
    COMPLETED("completado"),

    @Schema(description = "Cita cancelada", example = "cancelada")
    CANCELLED("cancelada"),

    @Schema(description = "El paciente no se presentó", example = "no_se_presento")
    NO_SHOW("no_se_presento");

    private final String displayName;

    AppointmentStatusEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Optional<AppointmentStatusEnum> fromDisplayName(String displayName) {
        return Arrays.stream(AppointmentStatusEnum.values())
                .filter(status -> status.displayName.equalsIgnoreCase(displayName))
                .findFirst();
    }
}

