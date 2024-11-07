package medical_insurance.backend_medical_insurance.user.controller;

import medical_insurance.backend_medical_insurance.common.utils.ResponseMessage;
import medical_insurance.backend_medical_insurance.user.dto.CreateUserDto;
import medical_insurance.backend_medical_insurance.user.dto.UpdateUserDto;
import medical_insurance.backend_medical_insurance.user.entity.UserEntity;
import medical_insurance.backend_medical_insurance.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "API for managing users")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation()
    @PostMapping
    public ResponseEntity<ResponseMessage<UserEntity>> createUser(@RequestBody CreateUserDto createUserDto) {
        return ResponseEntity.ok(ResponseMessage.success(userService.createUser(createUserDto), null, 1));
    }

    @Operation()
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseMessage<List<UserEntity>>> getAllUsers() {
        List<UserEntity> users = userService.getAllUsers();
        return ResponseEntity.ok(ResponseMessage.success(users, null, users.size()));
    }

    @Operation()
    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage<UserEntity>> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(ResponseMessage.success(
                userService.getUserById(id), null, null));
    }

    @Operation()
    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage<UserEntity>> updateUser(@PathVariable UUID id,
            @RequestBody UpdateUserDto updateUserDto) {
        return ResponseEntity.ok(ResponseMessage.success(
                userService.updateUser(id, updateUserDto), "User updated successfully", null));
    }

    @Operation()
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage<Void>> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ResponseMessage.ok("User deleted successfully", HttpStatus.NO_CONTENT));
    }
}
