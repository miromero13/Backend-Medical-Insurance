package medical_insurance.backend_medical_insurance.user.dto;

import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

public class UpdateRoleDto {
    @Size(min = 3, max = 50, message = "Role name must be between 3 and 50 characters")
    public String nombre;

    public List<UUID> permissionsId;
}
