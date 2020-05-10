package com.nosql.redis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash
public class Person implements Serializable {
    @Id
    private String id;
    private String first_name;
    private String last_name;
    private String email;
    private String country;
    private String modified;
    private boolean vip;
}
