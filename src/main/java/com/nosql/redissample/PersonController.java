package com.nosql.redissample;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Slf4j
@RestController
public class PersonController {

    private final List<Person> fake;
    private final PersonRepository personRepository;
    private final Gson gson = new Gson();

    public PersonController(List<Person> fake, PersonRepository personRepository) {
        this.fake = fake;
        this.personRepository = personRepository;
    }

    @GetMapping("/redis/random")
    ResponseEntity random() throws IOException {
        var person = personRepository.save(
                fake.get(new Random().nextInt(fake.size()))
        );
        log.info("msg=persisted, person=" + gson.toJson(person));
        return ResponseEntity.ok(Collections.singletonMap("personGenerated", person));
    }

    @GetMapping("/redis/person/{id}")
    ResponseEntity findById(@PathVariable("id") String id) {
        log.info("action=findById, id=" + id);
        return ResponseEntity.ok(personRepository.findById(id));
    }
}
