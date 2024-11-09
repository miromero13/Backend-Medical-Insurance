package medical_insurance.backend_medical_insurance.common.enums;

import java.util.Arrays;
import java.util.Optional;

public enum DayEnum {
    MONDAY("lunes"),
    TUESDAY("martes"),
    WEDNESDAY("miercoles"),
    THURSDAY("jueves"),
    FRIDAY("viernes"),
    SATURDAY("sabado"),
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

