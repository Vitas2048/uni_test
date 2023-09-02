package com.unibell.uni_test.repository;

import com.unibell.uni_test.model.Email;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmailRepository extends CrudRepository<Email, Long> {

    List<Email> findByClientId(int id);
}
