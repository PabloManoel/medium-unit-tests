package medium.clientregistration.controller;

import medium.clientregistration.dto.request.ClientDTO;
import medium.clientregistration.entity.ClientEntity;
import medium.clientregistration.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/{document}")
    public ResponseEntity<ClientDTO> findClient(@PathVariable(value = "document") String document) throws Exception {
        var client = clientService.findByDocument(document);
        return ResponseEntity.ok(client);
    }


    @GetMapping
    public ResponseEntity<List<ClientDTO>> listClients() {
        var clients = clientService.list();
        return ResponseEntity.ok(clients);
    }

    @PostMapping
    public ResponseEntity<Void> registerClient(@RequestBody ClientDTO clientDTO) throws Exception {
        clientService.register(clientDTO);
        return ResponseEntity.created(null).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteClient(@RequestBody ClientDTO clientDTO) throws Exception {
        clientService.delete(clientDTO.document());
        return ResponseEntity.noContent().build();
    }

}
