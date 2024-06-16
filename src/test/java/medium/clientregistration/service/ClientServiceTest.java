package medium.clientregistration.service;

import medium.clientregistration.dto.request.ClientDTO;
import medium.clientregistration.entity.ClientEntity;
import medium.clientregistration.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    ClientRepository clientRepository;

    @InjectMocks
    ClientService clientService;

    @Test
    void register_shouldThrowException_when_clientAlreadyExists() {
        var existingDocument = "11122233344";
        var existingEntity = ClientEntity.builder()
                .document(existingDocument)
                .build();

        var clientDTO = new ClientDTO(
                "random",
                "guy",
                "CPF",
                existingDocument
        );

        when(clientRepository.findByDocument(existingDocument))
                .thenReturn(existingEntity);

        var exception = assertThrows(Exception.class, () ->
                clientService.register(clientDTO)
        );

        assertEquals("Client already exists", exception.getMessage());

        Mockito.verify(clientRepository, never()).save(any());
    }

    @Test
    void register_shouldSaveClientSuccessfully(){
        var existingDocument = "11122233344";

        var clientDTO = new ClientDTO(
                "random",
                "guy",
                "CPF",
                existingDocument
        );

        when(clientRepository.findByDocument(existingDocument))
                .thenReturn(null);

        assertDoesNotThrow(() -> clientService.register(clientDTO));

        Mockito.verify(clientRepository, times(1)).save(any());
    }
}
