package medical_insurance.backend_medical_insurance.user.dto;

import medical_insurance.backend_medical_insurance.common.enums.GenderEnum;

import java.util.UUID;

public class UserDto {
  public String ci;
  public String name;
  public String phone;
  public String email;
  public GenderEnum gender;
  public Boolean active;
  public UUID role_id;
  public UUID branch_id;

  public UserDto(String ci, String name, String phone, String email, GenderEnum gender, Boolean active, UUID role_id, UUID branch_id) {
    this.ci = ci;
    this.name = name;
    this.phone = phone;
    this.email = email;
    this.active = active;
    this.gender = gender;
    this.role_id = role_id;
    this.branch_id = branch_id;
  }
}
