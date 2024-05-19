package medium.clientregistration.repository;

import medium.clientregistration.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

    ClientEntity findByDocument(String document);

    void deleteByDocument(String document);
}
