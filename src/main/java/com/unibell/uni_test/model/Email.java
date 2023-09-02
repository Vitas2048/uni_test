package com.unibell.uni_test.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import jakarta.persistence.*;

@Entity
@Table(name = "email")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Email {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "value required")
    @NotEmpty(message = "value required")
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;
}
