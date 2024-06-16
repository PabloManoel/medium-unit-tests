package medium.clientregistration.repository;

import jakarta.servlet.ServletContext;
import medium.clientregistration.entity.ClientEntity;
import medium.clientregistration.enums.DocumentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.images.builder.Transferable;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import static org.junit.Assert.assertEquals;

@DataJpaTest
@Testcontainers
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@PropertySource("classpath:application-test.properties")
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Container
    public static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0.32")
            .withCopyFileToContainer(MountableFile.forClasspathResource("init-db.sql"), "/docker-entrypoint-initdb.d/init-db.sql")
            .withDatabaseName("db")
            .withUsername("mysql")
            .withPassword("mysql");

    @DynamicPropertySource
    public static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

    @BeforeAll
    public static void setUp() {
        mySQLContainer.start();
    }

    @Test
    public void testSave() {
        ClientEntity clientEntity = ClientEntity.builder()
                .id(1L)
                .document("45077465827")
                .documentType(DocumentType.CPF)
                .build();

        var clientEntitySaved = clientRepository.save(clientEntity);

        assertEquals(clientEntity.getId(), clientEntitySaved.getId());
        assertEquals(clientEntity.getDocument(), clientEntitySaved.getDocument());

    }


}
