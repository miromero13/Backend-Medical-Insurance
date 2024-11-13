package medical_insurance.backend_medical_insurance.common.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;
import java.util.Optional;

@Schema(description = "Días de la semana, con su representación en español")
public enum DayEnum {

    @Schema(description = "Lunes", example = "lunes")
    MONDAY("lunes"),

    @Schema(description = "Martes", example = "martes")
    TUESDAY("martes"),

    @Schema(description = "Miércoles", example = "miercoles")
    WEDNESDAY("miercoles"),

    @Schema(description = "Jueves", example = "jueves")
    THURSDAY("jueves"),

    @Schema(description = "Viernes", example = "viernes")
    FRIDAY("viernes"),

    @Schema(description = "Sábado", example = "sabado")
    SATURDAY("sabado"),

    @Schema(description = "Domingo", example = "domingo")
    SUNDAY("domingo");

    private final String displayName;

    DayEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Optional<DayEnum> fromDisplayName(String displayName) {
        return Arrays.stream(DayEnum.values())
                .filter(day -> day.displayName.equalsIgnoreCase(displayName))
                .findFirst();
    }
}
