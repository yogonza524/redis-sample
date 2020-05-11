package com.nosql.redissample;

import com.google.gson.Gson;
import com.nosql.redis.models.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    private final PersonService personService;
    private final Gson gson = new Gson();
    private final RedisTemplate<String, Object> redisTemplate;
    private final CacheManager cacheManager;

    public PersonController(List<Person> fake, PersonRepository personRepository, PersonService personService, RedisTemplate<String, Object> redisTemplate, CacheManager cacheManager) {
        this.fake = fake;
        this.personRepository = personRepository;
        this.personService = personService;
        this.redisTemplate = redisTemplate;
        this.cacheManager = cacheManager;
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
    public ResponseEntity findById(@PathVariable("id") String id) {
        log.info("action=findById, id=" + id);
        return ResponseEntity.ok(personService.findById(id));
    }

    @DeleteMapping("/redis/person/{id}")
    public ResponseEntity deleteKeyById(@PathVariable("id") String id) {

        cacheManager.getCache("persons").evictIfPresent(id);
        return ResponseEntity.ok(Collections.singletonMap("deleted", "Ok, I'll try"));
    }
}
