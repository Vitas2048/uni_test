package com.unibell.uni_test.repository;

import com.unibell.uni_test.model.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, Integer> {

    List<Client> findAll();

    Optional<Client> findById(int id);

    Optional<Client> findByName(String name);
}
