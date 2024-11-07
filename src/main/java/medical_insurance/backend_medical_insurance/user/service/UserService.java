package medical_insurance.backend_medical_insurance.user.service;

import medical_insurance.backend_medical_insurance.inventory.entity.BranchEntity;
import medical_insurance.backend_medical_insurance.inventory.service.BranchService;
import medical_insurance.backend_medical_insurance.user.dto.CreateUserDto;
import medical_insurance.backend_medical_insurance.user.dto.UpdateUserDto;
import medical_insurance.backend_medical_insurance.user.entity.RoleEntity;
import medical_insurance.backend_medical_insurance.user.entity.UserEntity;
import medical_insurance.backend_medical_insurance.user.repository.RoleRepository;
import medical_insurance.backend_medical_insurance.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private BranchService branchService;

    public UserEntity createUser(CreateUserDto createUserDto) {
        try {
            // Crear una nueva entidad de usuario
            UserEntity user = new UserEntity();
            user.name = createUserDto.name;
            user.phone = createUserDto.phone;
            user.gender = createUserDto.gender;
            user.email = createUserDto.email;
            user.ci = createUserDto.ci;
            user.password = passwordEncoder.encode(createUserDto.password);
            RoleEntity role = roleRepository.findById(createUserDto.roleId)
                    .orElseThrow(() -> new RuntimeException("Role not found with id: " + createUserDto.roleId));
            user.role = role;

            // asignar una branch
            if (createUserDto.branchId != null) {
                // asignar una branch
                BranchEntity branch = branchService.getOneById(createUserDto.branchId);
                user.branch = branch;
            }

            return userRepository.save(user);
        } catch (RuntimeException ex) {
            throw new RuntimeException("An error occurred while creating the user: " + ex.getMessage());
        }
    }

    // Actualizar un usuario por su UUID
    public UserEntity updateUser(UUID id, UpdateUserDto updateUserDto) {
        try {
            UserEntity user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

            if (updateUserDto.name != null && !updateUserDto.name.isEmpty()) {
                user.name = updateUserDto.name;
            }
            if (updateUserDto.phone != null && !updateUserDto.phone.isEmpty()) {
                user.phone = updateUserDto.phone;
            }
            if (updateUserDto.gender != null) {
                user.gender = updateUserDto.gender;
            }
            if (updateUserDto.email != null && !updateUserDto.email.isEmpty()) {
                user.email = updateUserDto.email;
            }
            if (updateUserDto.password != null && !updateUserDto.password.isEmpty()) {
                // Encriptar la nueva contraseña
                user.password = passwordEncoder.encode(updateUserDto.password);
            }

            if (updateUserDto.branchId != null) {
                BranchEntity branch = branchService.getOneById(updateUserDto.branchId);
                user.branch = branch;
            }
            if (updateUserDto.roleId != null) {
                RoleEntity role = roleRepository.findById(updateUserDto.roleId)
                        .orElseThrow(() -> new RuntimeException("Role not found with id: " + updateUserDto.roleId));
                user.role = role;
            }

            userRepository.save(user);
            return user;
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while updating the user: " + ex.getMessage());
        }
    }

    // Obtener un usuario por su UUID
    public UserEntity getUserById(UUID id) {
        try {
            UserEntity user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

            return user;
        } catch (Exception ex) {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    // get user by email
    public UserEntity getUserByEmail(String email) {
        try {
            UserEntity user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

            return user;
        } catch (Exception ex) {
            throw new RuntimeException("User not found with email: " + email);
        }
    }

    // Eliminar un usuario por su UUID
    public Void deleteUser(UUID id) {
        try {
            UserEntity user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

            userRepository.delete(user);
            return null;
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while deleting the user: " + ex.getMessage());
        }
    }

    // Obtener todos los usuarios
    public List<UserEntity> getAllUsers() {
        try {
            List<UserEntity> users = userRepository.findAll();
            return users
                    .stream()
                    .toList();
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while getting the users: " + ex.getMessage());
        }
    }
}
