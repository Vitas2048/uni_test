package com.unibell.uni_test.repository;

import com.unibell.uni_test.model.PhoneNum;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PhoneNumRepository extends CrudRepository<PhoneNum, Long> {

    List<PhoneNum> findByClientId(int id);
}
