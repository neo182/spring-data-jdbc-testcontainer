package no.satyam.demo;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("Customer")
public record Customer(

        @Id
        Long id,
        @Column("firstname")
        String firstName,
        @Column("lastname")
        String lastName
) {}