package medical_insurance.backend_medical_insurance.user.service;

import medical_insurance.backend_medical_insurance.common.utils.ResponseMessage;
import medical_insurance.backend_medical_insurance.user.dto.CreateRoleDto;
import medical_insurance.backend_medical_insurance.user.dto.UpdateRoleDto;
import medical_insurance.backend_medical_insurance.user.entity.RoleEntity;
import medical_insurance.backend_medical_insurance.user.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.*;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;


    // Crear un nuevo rol
    public RoleEntity createRole(CreateRoleDto createRoleDto) {
        try {
            RoleEntity role = new RoleEntity();
            role.name = createRoleDto.nombre;
            roleRepository.save(role);
            return role;
        } catch (Exception ex) {
            throw new DataIntegrityViolationException("Error: " + ex.getMessage());
        }
    }

    // Actualizar un rol por su UUID
    public ResponseMessage<RoleEntity> updateRole(UUID id, UpdateRoleDto updateRoleDto) {
        try {
            RoleEntity role = this.getRoleById(id).getData();

            if (updateRoleDto.nombre != null && !updateRoleDto.nombre.isEmpty()) {
                role.name = updateRoleDto.nombre;
            }

            roleRepository.save(role);
            return ResponseMessage.success(role, "Role updated successfully", 1);
        } catch (RuntimeException ex) {
            return ResponseMessage.error("Error: " + ex.getMessage(), 404);
        } catch (Exception ex) {
            return ResponseMessage.error("An error occurred while updating the role: " + ex.getMessage(), 500);
        }
    }

    // Obtener un rol por su UUID
    public ResponseMessage<RoleEntity> getRoleById(UUID id) {
        try {
            RoleEntity role = roleRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));

            return ResponseMessage.success(role, "Role found", 1);
        } catch (RuntimeException ex) {
            return ResponseMessage.error("Error: " + ex.getMessage(), 404);
        } catch (Exception ex) {
            return ResponseMessage.error("An error occurred while retrieving the role: " + ex.getMessage(), 500);
        }
    }

    // Eliminar un rol por su UUID
    public ResponseMessage<Void> deleteRole(UUID id) {
        try {
            RoleEntity role = roleRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));

            roleRepository.delete(role);
            return ResponseMessage.success(null, "Role deleted successfully", null);
        } catch (RuntimeException ex) {
            return ResponseMessage.error("Error: " + ex.getMessage(), 404);
        } catch (Exception ex) {
            return ResponseMessage.error("An error occurred while deleting the role: " + ex.getMessage(), 500);
        }
    }

    // Obtener todos los roles
    public ResponseMessage<List<RoleEntity>> getAllRoles() {
        try {
            List<RoleEntity> roles = roleRepository.findAll();
            return ResponseMessage.success(roles, "Roles retrieved successfully", roles.size());
        } catch (Exception ex) {
            return ResponseMessage.error("An error occurred while retrieving roles: " + ex.getMessage(), 500);
        }
    }
}
