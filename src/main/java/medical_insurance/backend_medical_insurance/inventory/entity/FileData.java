package medical_insurance.backend_medical_insurance.inventory.entity;

import lombok.Builder;
import medical_insurance.backend_medical_insurance.common.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "fileData")
@Builder
public class FileData extends BaseEntity {

    public String name;
    public String type;
    public String filePath;
}
