package medical_insurance.backend_medical_insurance.hospital.dto;
import java.util.UUID;

public class SpecialtyDto {
    public String name;
    public String description;
    public UUID branchId;

    public SpecialtyDto(String name, String description, UUID branchId) {
        this.name = name;
        this.description = description;
        this.branchId = branchId;
    }
}
