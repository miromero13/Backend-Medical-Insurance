package medical_insurance.backend_medical_insurance.service_medic.service;

import medical_insurance.backend_medical_insurance.common.utils.ResponseMessage;
import medical_insurance.backend_medical_insurance.service_medic.dto.CreateClientAddressDto;
import medical_insurance.backend_medical_insurance.service_medic.entity.ClientAddressEntity;
import medical_insurance.backend_medical_insurance.service_medic.entity.ClientEntity;
import medical_insurance.backend_medical_insurance.service_medic.repository.ClientAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClientAddressService {

    @Autowired
    private ClientAddressRepository clientAddressRepository;

    @Autowired
    private ClientService clientService;

    public ClientAddressEntity createClientAddress(CreateClientAddressDto createClientAddressDto) {
        ClientEntity client = clientService.getOneById(createClientAddressDto.clientId);
        ClientAddressEntity address = new ClientAddressEntity();
        address.address = createClientAddressDto.address;
        address.country = createClientAddressDto.country;
        address.zipcode = createClientAddressDto.zipcode;
        address.phone = createClientAddressDto.phone;
        address.name = createClientAddressDto.name;
        address.client = client;
        clientAddressRepository.save(address);
        return address;
    }

    public ClientAddressEntity getOneById(UUID id) {
        return clientAddressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client address not found with ID: " + id));
    }

    public List<ClientAddressEntity> getAllClientAddresses() {
        return clientAddressRepository.findAll();
    }

    public ClientAddressEntity updateClientAddress(UUID id, CreateClientAddressDto updateAddressDto) {
        ClientAddressEntity address = getOneById(id);
        if (updateAddressDto.address != null) {
            address.address = updateAddressDto.address;
        }
        if (updateAddressDto.country != null) {
            address.country = updateAddressDto.country;
        }
        if (updateAddressDto.zipcode != null) {
            address.zipcode = updateAddressDto.zipcode;
        }
        if (updateAddressDto.phone != null) {
            address.phone = updateAddressDto.phone;
        }
        if (updateAddressDto.name != null) {
            address.name = updateAddressDto.name;
        }
        clientAddressRepository.save(address);
        return address;
    }

    public ResponseMessage<Void> deleteClientAddress(UUID id) {
        ClientAddressEntity address = getOneById(id);
        clientAddressRepository.delete(address);
        return ResponseMessage.success(null, "Client address deleted successfully", 1);
    }
}
