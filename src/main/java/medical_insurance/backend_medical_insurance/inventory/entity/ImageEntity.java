package medical_insurance.backend_medical_insurance.inventory.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import medical_insurance.backend_medical_insurance.common.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "image")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageEntity extends BaseEntity {

  @Column(nullable = false)
  public String name; // usar camelCase

  @Column(nullable = false)
  public String type;

  @JsonIgnore
  @Lob
  @Column(name = "imagedata")
  public byte[] imageData;

  // @OneToOne
  // @JoinColumn(name = "productOption_id", nullable = true)
  // public ProductOptionEntity productOption;
}
