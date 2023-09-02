package com.unibell.uni_test.core.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Contact {

    private List<String> numbers = new ArrayList<>();

    private List<String> emails = new ArrayList<>();
}
