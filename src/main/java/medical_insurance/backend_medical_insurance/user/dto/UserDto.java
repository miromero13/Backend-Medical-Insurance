package medical_insurance.backend_medical_insurance.user.dto;

import medical_insurance.backend_medical_insurance.common.enums.GenderEnum;

public class UserDto {
  public String ci;
  public String name;
  public String phone;
  public String email;
  public GenderEnum gender;
  public Boolean active;
  // public UUID role_id;
  // public UUID branch_id;

  public UserDto() {
  }

  public UserDto(String ci, String name, String phone, String email, GenderEnum gender, Boolean active) {
    this.ci = ci;
    this.name = name;
    this.phone = phone;
    this.email = email;
    this.active = active;
    this.gender = gender;
  }
}
