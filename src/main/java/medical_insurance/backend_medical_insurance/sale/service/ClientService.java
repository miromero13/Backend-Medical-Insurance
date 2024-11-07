package medical_insurance.backend_medical_insurance.sale.service;

import medical_insurance.backend_medical_insurance.common.utils.ResponseMessage;
import medical_insurance.backend_medical_insurance.sale.dto.CreateClientDto;
import medical_insurance.backend_medical_insurance.sale.dto.UpdateClientDto;
import medical_insurance.backend_medical_insurance.sale.entity.ClientEntity;
import medical_insurance.backend_medical_insurance.sale.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Crear un nuevo cliente (devolviendo la entidad creada)
    public ClientEntity createClient(CreateClientDto createClientDto) {
        // Validación: Verificar si el email o el celular ya existe
        if (createClientDto.email != null) {
            Optional<ClientEntity> existingClientByEmail = clientRepository.findByEmail(createClientDto.email);
            if (existingClientByEmail.isPresent()) {
                throw new RuntimeException("Client with this email already exists");
            }
        }

        if (createClientDto.phone != null) {
            Optional<ClientEntity> existingClientByPhone = clientRepository.findByPhone(createClientDto.phone);
            if (existingClientByPhone.isPresent()) {
                throw new RuntimeException("Client with this phone number already exists");
            }
        }

        // Crear una nueva instancia de ClientEntity
        ClientEntity newClient = new ClientEntity();
        newClient.name = createClientDto.name;
        newClient.phone = createClientDto.phone;
        newClient.email = createClientDto.email;

        // Encriptar la contraseña antes de guardarla
        if (createClientDto.password != null && !createClientDto.password.isEmpty()) {
            newClient.password = passwordEncoder.encode(createClientDto.password);
        }

        // Guardar el cliente en la base de datos
        clientRepository.save(newClient);

        return newClient;
    }

    // Obtener un cliente por ID
    public ClientEntity getOneById(UUID clientId) {
        return clientRepository.findById(clientId)
            .orElseThrow(() -> new RuntimeException("Client not found"));
    }

    // Obtener todos los clientes (devuelve lista de entidades)
    public List<ClientEntity> getAllClients() {
        return clientRepository.findAll();
    }

    // Actualizar un cliente (devolviendo la entidad actualizada)
    public ClientEntity updateClient(UUID clientId, UpdateClientDto updateClientDto) {
        ClientEntity client = clientRepository.findById(clientId)
            .orElseThrow(() -> new RuntimeException("Client not found"));

        if (updateClientDto.name != null) {
            client.name = updateClientDto.name;
        }

        if (updateClientDto.phone != null) {
            client.phone = updateClientDto.phone;
        }

        if (updateClientDto.email != null) {
            client.email = updateClientDto.email;
        }

        if (updateClientDto.password != null && !updateClientDto.password.isEmpty()) {
            client.password = passwordEncoder.encode(updateClientDto.password);
        }

        clientRepository.save(client);
        return client;
    }

    // Eliminar un cliente
    public ResponseMessage<Void> deleteClient(UUID clientId) {
        ClientEntity client = clientRepository.findById(clientId)
            .orElseThrow(() -> new RuntimeException("Client not found"));
        clientRepository.delete(client);
        return ResponseMessage.success(null, "Client deleted successfully", 1);
    }
}
