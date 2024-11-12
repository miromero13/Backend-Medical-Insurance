package medical_insurance.backend_medical_insurance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BackendMedicalInsuranceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendMedicalInsuranceApplication.class, args);
	}

}
