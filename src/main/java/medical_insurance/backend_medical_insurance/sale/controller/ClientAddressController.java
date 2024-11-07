package medical_insurance.backend_medical_insurance.sale.controller;

import medical_insurance.backend_medical_insurance.common.utils.ResponseMessage;
import medical_insurance.backend_medical_insurance.sale.dto.CreateClientAddressDto;
import medical_insurance.backend_medical_insurance.sale.entity.ClientAddressEntity;
import medical_insurance.backend_medical_insurance.sale.service.ClientAddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/client-address")
@Tag(name = "Client Addresses")
public class ClientAddressController {

    @Autowired
    private ClientAddressService clientAddressService;

    @PostMapping("/")
    public ResponseMessage<ClientAddressEntity> createClientAddress(@RequestBody CreateClientAddressDto createClientAddressDto) {
        return ResponseMessage.success(clientAddressService.createClientAddress(createClientAddressDto), "Client address created successfully", 1);
    }

    @GetMapping("/{id}")
    public ResponseMessage<ClientAddressEntity> getClientAddressById(@PathVariable UUID id) {
        return ResponseMessage.success(clientAddressService.getOneById(id), "Client address found successfully", 1);
    }

    @GetMapping("/")
    public ResponseMessage<List<ClientAddressEntity>> getAllClientAddresses() {
        return ResponseMessage.success(clientAddressService.getAllClientAddresses(), "Client addresses retrieved successfully", 1);
    }

    @PutMapping("/{id}")
    public ResponseMessage<ClientAddressEntity> updateClientAddress(@PathVariable UUID id, @RequestBody CreateClientAddressDto updateClientAddressDto) {
        return ResponseMessage.success(clientAddressService.updateClientAddress(id, updateClientAddressDto), "Client address updated successfully", 1);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<Void> deleteClientAddress(@PathVariable UUID id) {
        return clientAddressService.deleteClientAddress(id);
    }
}
