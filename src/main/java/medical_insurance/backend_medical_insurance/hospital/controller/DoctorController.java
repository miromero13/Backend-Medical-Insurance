package medical_insurance.backend_medical_insurance.hospital.controller;

import medical_insurance.backend_medical_insurance.common.utils.ResponseMessage;
import medical_insurance.backend_medical_insurance.hospital.dto.DoctorDto;
import medical_insurance.backend_medical_insurance.hospital.entity.DoctorEntity;
import medical_insurance.backend_medical_insurance.hospital.service.DoctorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/doctor")
@Tag(name = "Doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Operation()
    @PostMapping
    public ResponseEntity<ResponseMessage<DoctorEntity>> createDoctor(@RequestBody DoctorDto doctorDto) {
        return ResponseEntity
                .ok(doctorService.createDoctorWithSchedules(doctorDto));
    }

    @Operation()
    @GetMapping
    public ResponseEntity<ResponseMessage<List<DoctorEntity>>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @Operation()
    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage<DoctorEntity>> getDoctorById(@PathVariable UUID id) {
        return ResponseEntity.ok(ResponseMessage.success(doctorService.getOneDoctor(id), null, null));
    }

    @Operation()
    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage<DoctorEntity>> updateDoctor(
            @PathVariable UUID id,
            @RequestBody Optional<DoctorDto> doctorDto) {
        return ResponseEntity.ok(doctorService.updateDoctor(id, doctorDto));
    }

    @Operation()
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage<Void>> deleteDoctor(@PathVariable UUID id) {
        return ResponseEntity.ok(doctorService.deleteDoctor(id));
    }
}
