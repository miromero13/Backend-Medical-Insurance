package medical_insurance.backend_medical_insurance.hospital.controller;

import medical_insurance.backend_medical_insurance.common.utils.ResponseMessage;
import medical_insurance.backend_medical_insurance.hospital.dto.FormTemplateDto;
import medical_insurance.backend_medical_insurance.hospital.entity.FormTemplateEntity;
import medical_insurance.backend_medical_insurance.hospital.service.FormTemplateService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/form-templates")
@Tag(name = "Form Templates", description = "Manage form templates")
public class FormTemplateController {

    @Autowired
    private FormTemplateService formTemplateService;

    @Operation(summary = "Create a new form template")
    @PostMapping
    public ResponseEntity<ResponseMessage<FormTemplateEntity>> createFormTemplate(@RequestBody FormTemplateDto dto) {
        FormTemplateEntity createdTemplate = formTemplateService.create(dto);
        return ResponseEntity.ok(ResponseMessage.success(createdTemplate, "Form template created successfully", 1));
    }

    @Operation(summary = "Get all form templates")
    @GetMapping
    public ResponseEntity<ResponseMessage<List<FormTemplateEntity>>> getAllFormTemplates() {
        List<FormTemplateEntity> formTemplates = formTemplateService.getAll();
        return ResponseEntity.ok(ResponseMessage.success(formTemplates, "Form templates retrieved successfully", formTemplates.size()));
    }

    @Operation(summary = "Get a form template by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage<FormTemplateEntity>> getFormTemplateById(@PathVariable UUID id) {
        FormTemplateEntity formTemplate = formTemplateService.getOneById(id);
        return ResponseEntity.ok(ResponseMessage.success(formTemplate, "Form template retrieved successfully", 1));
    }
}
