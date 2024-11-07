package medical_insurance.backend_medical_insurance.inventory.service;

import medical_insurance.backend_medical_insurance.common.utils.ResponseMessage;
import medical_insurance.backend_medical_insurance.inventory.dto.CreateBranchDto;
import medical_insurance.backend_medical_insurance.inventory.entity.BranchEntity;
import medical_insurance.backend_medical_insurance.inventory.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class BranchService {

    @Autowired
    private BranchRepository branchRepository;

    public BranchEntity create(CreateBranchDto createBranchDto) {
        try {
            BranchEntity branch = new BranchEntity();
            branch.name = createBranchDto.name;
            branch.address = createBranchDto.address != null ? createBranchDto.address : "";
            branch.phone = createBranchDto.phone;
            branch.email = createBranchDto.email;
            branch.active = true;
            branchRepository.save(branch);
            return branch;
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while creating the branch: " + ex.getMessage());
        }
    }

    public ResponseMessage<List<BranchEntity>> getAll() {
        try {
            List<BranchEntity> branches = branchRepository.findAll()
                    .stream()
                    .filter(branch -> branch.active)
                    .toList();
            return ResponseMessage.success(branches, "Branches found", branches.size());
        } catch (Exception ex) {
            return ResponseMessage.error("An error occurred while getting the branches: " + ex.getMessage(), 400);
        }
    }

    public BranchEntity getOneById(UUID id) {
        try {
            BranchEntity branch = branchRepository.findById(id)
                    .filter(branchEntity -> branchEntity.active)
                    .orElseThrow(() -> new RuntimeException("Branch not found with id: " + id));
            return branch;
        } catch (Exception ex) {
            throw new RuntimeException("Branch not found with id: " + id);
        }
    }

    public ResponseMessage<Void> delete(UUID id) {
        try {
            BranchEntity branch = branchRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Branch not found with id: " + id));
            branch.active = false;
            branchRepository.save(branch);
            return ResponseMessage.success(null, "Branch deleted successfully", 1);
        } catch (RuntimeException ex) {
            return ResponseMessage.error("Error: " + ex.getMessage(), 404);
        } catch (Exception ex) {
            return ResponseMessage.error("An error occurred while deleting the user: " + ex.getMessage(), 500);
        }
    }

    public ResponseMessage<BranchEntity> update(UUID id, Optional<CreateBranchDto> updateBranchDto) {
        try {
            BranchEntity branch = branchRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Branch not found with id: " + id));
            if (updateBranchDto.isPresent()) {
                branch.name = updateBranchDto.get().name != null ? updateBranchDto.get().name : branch.name;
                branch.address = updateBranchDto.get().address != null ? updateBranchDto.get().address : branch.address;
                branch.phone = updateBranchDto.get().phone != null ? updateBranchDto.get().phone : branch.phone;
                branch.email = updateBranchDto.get().email != null ? updateBranchDto.get().email : branch.email;
                branchRepository.save(branch);
            }
            return ResponseMessage.success(branch, "Branch updated successfully", 1);
        } catch (RuntimeException ex) {
            return ResponseMessage.error("Error: " + ex.getMessage(), 404);
        } catch (Exception ex) {
            return ResponseMessage.error("An error occurred while updating the branch: " + ex.getMessage(), 500);
        }
    }

}
