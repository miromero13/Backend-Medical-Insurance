package medical_insurance.backend_medical_insurance.inventory.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "DTO para la creación de una imagen")
public class CreateImageDto {

    @Schema(description = "Imagen del producto", required = true, format = "binary")
    @NotBlank(message = "La imagen es obligatoria")
    public MultipartFile image;

    @Schema(description = "ID de la opción de producto", example = "1", required = true)
    @NotBlank(message = "El ID de la opción de producto es obligatorio")
    public UUID productOptionId;
}
