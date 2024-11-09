package medical_insurance.backend_medical_insurance.hospital.service;

import medical_insurance.backend_medical_insurance.common.utils.ResponseMessage;
import medical_insurance.backend_medical_insurance.hospital.dto.SpecialtyDto;
import medical_insurance.backend_medical_insurance.hospital.entity.BranchEntity;
import medical_insurance.backend_medical_insurance.hospital.entity.SpecialtyEntity;
import medical_insurance.backend_medical_insurance.hospital.repository.SpecialtyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class SpecialtyService {

    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Autowired
    private BranchService branchService;

    public SpecialtyEntity create(SpecialtyDto specialtyDto) {
        try {
            SpecialtyEntity specialty = new SpecialtyEntity();
            specialty.name = specialtyDto.name;
            specialty.description = specialtyDto.description;
            if(specialtyDto.branchId != null) {
                specialty.branch = branchService.getOneById(specialtyDto.branchId);
            }
            return specialtyRepository.save(specialty);
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while creating the specialty: " + ex.getMessage());
        }
    }

    public ResponseMessage<List<SpecialtyEntity>> getAll() {
        try {
            List<SpecialtyEntity> specialties = specialtyRepository.findAll();
            return ResponseMessage.success(specialties, "Specialties found", specialties.size());
        } catch (Exception ex) {
            return ResponseMessage.error("An error occurred while getting the specialties: " + ex.getMessage(), 400);
        }
    }

    public SpecialtyEntity getOneById(UUID id) {
        try {
            return specialtyRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Specialty not found with id: " + id));
        } catch (Exception ex) {
            throw new RuntimeException("Specialty not found with id: " + id);
        }
    }

    public ResponseMessage<Void> delete(UUID id) {
        try {
            SpecialtyEntity specialty = specialtyRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Specialty not found with id: " + id));
            specialtyRepository.delete(specialty);
            return ResponseMessage.success(null, "Specialty deleted successfully", 1);
        } catch (RuntimeException ex) {
            return ResponseMessage.error("Error: " + ex.getMessage(), 404);
        } catch (Exception ex) {
            return ResponseMessage.error("An error occurred while deleting the specialty: " + ex.getMessage(), 500);
        }
    }

    public ResponseMessage<SpecialtyEntity> update(UUID id, Optional<SpecialtyDto> updateSpecialtyDto) {
        try {
            SpecialtyEntity specialty = specialtyRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Specialty not found with id: " + id));
            if (updateSpecialtyDto.isPresent()) {
                SpecialtyDto dto = updateSpecialtyDto.get();
                specialty.name = dto.name != null ? dto.name : specialty.name;
                specialty.description = dto.description != null ? dto.description : specialty.description;
                specialtyRepository.save(specialty);
            }
            return ResponseMessage.success(specialty, "Specialty updated successfully", 1);
        } catch (RuntimeException ex) {
            return ResponseMessage.error("Error: " + ex.getMessage(), 404);
        } catch (Exception ex) {
            return ResponseMessage.error("An error occurred while updating the specialty: " + ex.getMessage(), 500);
        }
    }
}
