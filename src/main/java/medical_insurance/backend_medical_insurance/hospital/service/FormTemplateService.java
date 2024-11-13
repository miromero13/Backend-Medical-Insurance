package medical_insurance.backend_medical_insurance.hospital.service;

import medical_insurance.backend_medical_insurance.hospital.dto.FormTemplateDto;
import medical_insurance.backend_medical_insurance.hospital.entity.FormTemplateEntity;
import medical_insurance.backend_medical_insurance.hospital.repository.FormTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FormTemplateService {

    @Autowired
    public FormTemplateRepository formTemplateRepository;

    public FormTemplateEntity create(FormTemplateDto dto) {
        FormTemplateEntity formTemplate = new FormTemplateEntity();
        formTemplate.formName = dto.formName;
        formTemplate.formStructure = dto.formStructure;

        return formTemplateRepository.save(formTemplate);
    }

    public List<FormTemplateEntity> getAll() {
        return formTemplateRepository.findAll();
    }

    public FormTemplateEntity getOneById(UUID id) {
        return formTemplateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Formulario predeterminado no encontrado"));
    }
}
