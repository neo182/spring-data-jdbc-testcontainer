package no.satyam.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.function.Function;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ContextConfiguration(initializers = DockerPostgreDataSourceInitializer.class)
public class CustomerRepositoryTest {
    
    @Autowired
    CustomerRepository repository;

    Function<Long, RuntimeException> notFound = key ->
            new RuntimeException(String.format("Not found Customer %d", key));

    @Test
    public void givenRepository_when_find_knownCustomer_then_Ok() {

        Customer customer = new Customer(null, "Ole", "Gunnar");
        repository.save(customer);

        long firstRecord = 1l;
        Customer customerSaved = repository.findById(firstRecord)
                .orElseThrow(() -> notFound.apply(firstRecord));

        assertThat(customerSaved.id()).isEqualTo(firstRecord);
        assertThat(customerSaved.firstName()).isEqualTo("Ole");
        assertThat(customerSaved.lastName()).isEqualTo("Gunnar");
    }

    @Test
    public void givenRepository_when_find_unknownCustomer_then_Ok() {

        long firstRecord = 2l;
        assertThrows(RuntimeException.class, () -> {
            repository.findById(firstRecord)
                    .orElseThrow(() -> notFound.apply(firstRecord));
        });
    }
}
