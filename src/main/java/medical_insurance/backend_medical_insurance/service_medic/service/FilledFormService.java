package medical_insurance.backend_medical_insurance.service_medic.service;

import medical_insurance.backend_medical_insurance.hospital.entity.FormTemplateEntity;
import medical_insurance.backend_medical_insurance.hospital.repository.FormTemplateRepository;
import medical_insurance.backend_medical_insurance.hospital.service.FormTemplateService;
import medical_insurance.backend_medical_insurance.service_medic.dto.FilledFormDto;
import medical_insurance.backend_medical_insurance.service_medic.entity.AppointmentEntity;
import medical_insurance.backend_medical_insurance.service_medic.entity.FilledFormEntity;
import medical_insurance.backend_medical_insurance.service_medic.repository.AppointmentRepository;
import medical_insurance.backend_medical_insurance.service_medic.repository.FilledFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class FilledFormService {

    @Autowired
    public FilledFormRepository filledFormRepository;

    @Autowired
    public FormTemplateService formTemplateService;

    @Autowired
    public AppointmentRepository appointmentRepository;

    public FilledFormEntity create(FilledFormDto filledFormDto) {

        AppointmentEntity appointment = appointmentRepository.findById(filledFormDto.appointmentId)
                .orElseThrow(() -> new RuntimeException("Cita not found"));

        FilledFormEntity filledForm = new FilledFormEntity();
        filledForm.formTemplate = formTemplateService.getOneById(filledFormDto.formTemplateId);
        filledForm.appointment = appointment;
        filledForm.filledData = filledFormDto.filledData;
        filledForm.dateFilled = LocalDate.now();

        return filledFormRepository.save(filledForm);
    }

    public List<FilledFormEntity> getOneById(UUID appointmentId) {
        return filledFormRepository.findByAppointmentId(appointmentId);
    }
}

