package com.unibell.uni_test.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import jakarta.persistence.*;

@Entity
@Table(name = "phone_number")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhoneNum {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Pattern(regexp = "\\d{11}", message = "only 11 digits of number")
    @NotNull(message = "number required")
    @NotEmpty(message = "number required")
    private String phoneNum;

    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;

}
