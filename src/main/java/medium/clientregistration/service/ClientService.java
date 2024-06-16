package medium.clientregistration.service;

import jakarta.transaction.Transactional;
import medium.clientregistration.dto.request.ClientDTO;
import medium.clientregistration.entity.ClientEntity;
import medium.clientregistration.enums.DocumentType;
import medium.clientregistration.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public ClientDTO findByDocument(String document) throws Exception {
        var clientEntity = clientRepository.findByDocument(document);

        if (clientEntity == null) {
            throw new Exception("client not found");
        }

        ClientDTO clientDTO = toDTO(clientEntity);

        return clientDTO;
    }

    @Transactional
    public void register(ClientDTO clientDTO) throws Exception {

        var client = clientRepository.findByDocument(clientDTO.document());

        if (client != null) {
            throw new Exception("Client already exists");
        }

        var clientEntity = toEntity(clientDTO);

        clientRepository.save(clientEntity);
    }

    public List<ClientDTO> list() {
        var clients = clientRepository.findAll();

        if (clients == null) {
            return Arrays.asList();
        }

        List<ClientDTO> clientDTOList = clients.stream()
                .map(client -> toDTO(client))
                .toList();

        return clientDTOList;
    }

    @Transactional
    public void delete(String document) {
        clientRepository.deleteByDocument(document);
    }

    private ClientEntity toEntity(ClientDTO clientDTO) {
        return ClientEntity.builder()
                .firstName(clientDTO.firstName())
                .lastName(clientDTO.lastName())
                .documentType(DocumentType.valueOf(clientDTO.documentType()))
                .document(clientDTO.document())
                .build();
    }

    private ClientDTO toDTO(ClientEntity clientEntity) {
        return new ClientDTO(
                clientEntity.getFirstName(),
                clientEntity.getLastName(),
                clientEntity.getDocumentType().name(),
                clientEntity.getDocument()
        );
    }
}
