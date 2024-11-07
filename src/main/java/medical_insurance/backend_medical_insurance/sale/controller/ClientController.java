package medical_insurance.backend_medical_insurance.sale.controller;

import medical_insurance.backend_medical_insurance.common.utils.ResponseMessage;
import medical_insurance.backend_medical_insurance.sale.dto.CreateClientDto;
import medical_insurance.backend_medical_insurance.sale.dto.UpdateClientDto;
import medical_insurance.backend_medical_insurance.sale.entity.ClientEntity;
import medical_insurance.backend_medical_insurance.sale.service.ClientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/client")
@Tag(name = "Clientes")
public class ClientController {

    @Autowired
    private ClientService clientService;

    // Crear un nuevo cliente
    @PostMapping("/")
    public ResponseMessage<ClientEntity> createClient(@Valid @RequestBody CreateClientDto createClientDto) {
        return ResponseMessage.success(clientService.createClient(createClientDto), "Client created successfully", 1);
    }

    // Obtener un cliente por ID
    @GetMapping("/{clientId}")
    public ResponseMessage<ClientEntity> getClientById(@PathVariable UUID clientId) {
        return ResponseMessage.success(clientService.getOneById(clientId), "Client found successfully", 1);
    }

    // Obtener todos los clientes
    @GetMapping("/")
    public ResponseMessage<List<ClientEntity>> getAllClients() {
        return ResponseMessage.success(clientService.getAllClients(), "Clients found successfully", 1);
    }

    // Actualizar un cliente
    @PutMapping("/{clientId}")
    public ResponseMessage<ClientEntity> updateClient(@PathVariable UUID clientId, @RequestBody UpdateClientDto updateClientDto) {
        return ResponseMessage.success(clientService.updateClient(clientId, updateClientDto), "Client updated successfully", 1);
    }

    // Eliminar un cliente
    @DeleteMapping("/{clientId}")
    public ResponseMessage<Void> deleteClient(@PathVariable UUID clientId) {
        return clientService.deleteClient(clientId);
    }
}
