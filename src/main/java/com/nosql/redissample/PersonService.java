package com.nosql.redissample;

import com.nosql.redis.models.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Cacheable(value = "persons", cacheManager = "cacheManager")
    public Optional<Person> findById(String id) {
        log.info("action=findById, msg=This is not cached");
        return personRepository.findById(id);
    }
}
