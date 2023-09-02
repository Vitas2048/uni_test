package com.unibell.uni_test.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;


@Entity
@Table(name = "client")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Setter@Getter
@NoArgsConstructor
public class Client {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "name required")
    @NotEmpty(message = "name required")
    private String name;
}
