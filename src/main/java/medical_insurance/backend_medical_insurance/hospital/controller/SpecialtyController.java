package medical_insurance.backend_medical_insurance.hospital.controller;

import medical_insurance.backend_medical_insurance.common.utils.ResponseMessage;
import medical_insurance.backend_medical_insurance.hospital.dto.SpecialtyDto;
import medical_insurance.backend_medical_insurance.hospital.entity.SpecialtyEntity;
import medical_insurance.backend_medical_insurance.hospital.service.SpecialtyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/specialty")
@Tag(name = "Specialty")
public class SpecialtyController {

    @Autowired
    private SpecialtyService specialtyService;

    @Operation()
    @PostMapping
    public ResponseEntity<ResponseMessage<SpecialtyEntity>> createSpecialty(@RequestBody SpecialtyDto specialtyDto) {
        return ResponseEntity.ok(ResponseMessage.success(specialtyService.create(specialtyDto), "Specialty created successfully", 1));
    }

    @Operation()
    @GetMapping
    public ResponseEntity<ResponseMessage<List<SpecialtyEntity>>> getAllSpecialties() {
        return ResponseEntity.ok(specialtyService.getAll());
    }

    @Operation()
    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage<SpecialtyEntity>> getSpecialtyById(@PathVariable UUID id) {
        return ResponseEntity.ok(ResponseMessage.success(specialtyService.getOneById(id), null, null));
    }

    @Operation()
    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage<SpecialtyEntity>> updateSpecialty(@PathVariable UUID id,
                                                                            @RequestBody Optional<SpecialtyDto> specialtyDto) {
        return ResponseEntity.ok(specialtyService.update(id, specialtyDto));
    }

    @Operation()
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage<Void>> deleteSpecialty(@PathVariable UUID id) {
        return ResponseEntity.ok(specialtyService.delete(id));
    }
}
