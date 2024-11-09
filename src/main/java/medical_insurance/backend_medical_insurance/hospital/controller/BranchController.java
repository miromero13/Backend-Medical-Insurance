package medical_insurance.backend_medical_insurance.hospital.controller;

import medical_insurance.backend_medical_insurance.common.utils.ResponseMessage;
import medical_insurance.backend_medical_insurance.hospital.dto.CreateBranchDto;
import medical_insurance.backend_medical_insurance.hospital.entity.BranchEntity;
import medical_insurance.backend_medical_insurance.hospital.service.BranchService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/branch")
@Tag(name = "Branch")
public class BranchController {

    @Autowired
    private BranchService branchService;

    @Operation()
    @PostMapping
    public ResponseEntity<ResponseMessage<BranchEntity>> createBranch(@RequestBody CreateBranchDto createBranchDto) {
        return ResponseEntity
                .ok(ResponseMessage.success(branchService.create(createBranchDto), "Branch created successfully",
                        1));
    }

    @Operation()
    @GetMapping
    public ResponseEntity<ResponseMessage<List<BranchEntity>>> getAllBranchs() {
        return ResponseEntity.ok(ResponseMessage.success(branchService.getAll().getData(), null,
                branchService.getAll().getData().size()));
    }

    @Operation()
    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage<BranchEntity>> getBranchById(@PathVariable UUID id) {
        return ResponseEntity.ok(ResponseMessage.success(branchService.getOneById(id), null, null));
    }

    @Operation()
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage<Void>> deleteBranch(@PathVariable UUID id) {
        return ResponseEntity.ok(branchService.delete(id));
    }

    @Operation()
    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage<BranchEntity>> updateBranch(@PathVariable UUID id,
            @RequestBody Optional<CreateBranchDto> updateBranchDto) {
        return ResponseEntity.ok(branchService.update(id, updateBranchDto));
    }

}
