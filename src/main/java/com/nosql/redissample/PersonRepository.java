package com.nosql.redissample;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface PersonRepository extends CrudRepository<Person, String> {
}
