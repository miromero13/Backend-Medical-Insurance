package medical_insurance.backend_medical_insurance.seeder;

import medical_insurance.backend_medical_insurance.inventory.dto.*;
import medical_insurance.backend_medical_insurance.inventory.entity.*;
import medical_insurance.backend_medical_insurance.inventory.service.*;
import medical_insurance.backend_medical_insurance.user.dto.CreateRoleDto;
import medical_insurance.backend_medical_insurance.user.dto.CreateUserDto;
import medical_insurance.backend_medical_insurance.user.entity.RoleEntity;
import medical_insurance.backend_medical_insurance.user.entity.UserEntity;
import medical_insurance.backend_medical_insurance.user.service.RoleService;
import medical_insurance.backend_medical_insurance.user.service.UserService;
import medical_insurance.backend_medical_insurance.common.enums.GenderEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SeederController {

  @Autowired
  private RoleService roleService;

  @Autowired
  private UserService userService;

  @Autowired
  private BranchService branchService;

  @GetMapping("/public/seeder")
  public String runSeeder() {
    try {
      // Crear rol
      CreateRoleDto roleDto = new CreateRoleDto();
      roleDto.nombre = "ADMIN";
      RoleEntity role1 = this.roleService.createRole(roleDto);

      CreateBranchDto branchDto = new CreateBranchDto();
      branchDto.name = "Sucursal 1";
      branchDto.address = "Santa Cruz - Bolivia";
      branchDto.email = "scz@gmail.com";
      branchDto.phone = "78010833";
      BranchEntity branch1 = this.branchService.create(branchDto);

      // Crear usuario administrador
      CreateUserDto userDto = new CreateUserDto();
      userDto.name = "Admin";
      userDto.email = "luis@gmail.com";
      userDto.password = "12345678";
      userDto.phone = "78010833";
      userDto.ci = "9807687";
      userDto.gender = GenderEnum.MALE;
      userDto.roleId = role1.getId();
      userDto.branchId = branch1.getId();

      UserEntity user = this.userService.createUser(userDto);
      if (user == null) {
        return "Error creando usuario administrador";
      }

      return "Seeders ejecutados correctamente. Datos iniciales creados con éxito.";
    } catch (Exception e) {
      return "Error ejecutando los seeders: " + e.getMessage();
    }
  }
}
