package com.unibell.uni_test.core.message;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {

    private int id;

    private String name;

    private List<
            @Pattern(regexp = "(^8|7|\\+7)((\\d{10})|(\\s\\(\\d{3}\\)\\s\\d{3}\\s\\d{2}\\s\\d{2}))",
                    message = "only number of phone (Russia)")
            @NotEmpty(message = "value required")
            @NotNull(message = "value required")
            String> numbers = new ArrayList<>();


    private List<
            @NotEmpty(message = "value required")
            @NotNull(message = "value required")
            @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)" +
                    "*(\\.[A-Za-z]{2,})$",
                    message = "email address only")
            String> emails = new ArrayList<>();
}
