package no.satyam.demo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(initializers = DockerPostgreDataSourceInitializer.class)
public class CustomerRepositoryTest {
    
    @Autowired
    CustomerRepository repository;

    @Test
    public void testRepository() {
        Customer customer = new Customer("Ole", "Gunnar");
        repository.save(customer);

        Customer customerSaved = repository.findById(1);

        Assertions.assertThat(customerSaved).isNotNull();
        Assertions.assertThat(customerSaved.getFirstName()).isEqualTo("Ole");
        Assertions.assertThat(customerSaved.getLastName()).isEqualTo("Gunnar");

    }
}
