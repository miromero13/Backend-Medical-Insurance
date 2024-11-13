package medical_insurance.backend_medical_insurance.service_medic.controller;

import medical_insurance.backend_medical_insurance.common.utils.ResponseMessage;
import medical_insurance.backend_medical_insurance.service_medic.dto.FilledFormDto;
import medical_insurance.backend_medical_insurance.service_medic.entity.FilledFormEntity;
import medical_insurance.backend_medical_insurance.service_medic.service.FilledFormService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/filled-forms")
@Tag(name = "Filled Forms", description = "Manage filled forms")
public class FilledFormController {

    @Autowired
    private FilledFormService filledFormService;

    @Operation(summary = "Create a new filled form")
    @PostMapping
    public ResponseEntity<ResponseMessage<FilledFormEntity>> createFilledForm(@RequestBody FilledFormDto filledFormDto) {
        FilledFormEntity createdForm = filledFormService.create(filledFormDto);
        return ResponseEntity.ok(ResponseMessage.success(createdForm, "Filled form created successfully", 1));
    }

    @Operation(summary = "Get filled forms by appointment ID")
    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<ResponseMessage<List<FilledFormEntity>>> getFilledFormsByAppointmentId(@PathVariable UUID appointmentId) {
        List<FilledFormEntity> filledForms = filledFormService.getOneById(appointmentId);
        return ResponseEntity.ok(ResponseMessage.success(filledForms, "Filled forms for the appointment retrieved successfully", filledForms.size()));
    }
}
