package medical_insurance.backend_medical_insurance.user.controller;

import medical_insurance.backend_medical_insurance.common.utils.ResponseMessage;
import medical_insurance.backend_medical_insurance.user.dto.CreateRoleDto;
import medical_insurance.backend_medical_insurance.user.dto.UpdateRoleDto;
import medical_insurance.backend_medical_insurance.user.entity.RoleEntity;
import medical_insurance.backend_medical_insurance.user.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/roles")
@Tag(name = "Roles", description = "API for managing roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Operation(summary = "Create a new role")
    @PostMapping
    public ResponseEntity<ResponseMessage<RoleEntity>> createRole(@RequestBody CreateRoleDto createRoleDto) {
        return ResponseEntity
                .ok(ResponseMessage.success(roleService.createRole(createRoleDto), "Role created successfully", 1));
    }

    @Operation(summary = "Get all roles")
    @GetMapping
    public ResponseEntity<ResponseMessage<List<RoleEntity>>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @Operation(summary = "Update an existing role")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage<RoleEntity>> updateRole(@PathVariable UUID id,
            @RequestBody UpdateRoleDto updateRoleDto) {
        return ResponseEntity.ok(roleService.updateRole(id, updateRoleDto));
    }

    @Operation(summary = "Get a role by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage<RoleEntity>> getRoleById(@PathVariable UUID id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @Operation(summary = "Delete a role by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage<Void>> deleteRole(@PathVariable UUID id) {
        return ResponseEntity.ok(roleService.deleteRole(id));
    }

}
