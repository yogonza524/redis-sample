package com.nosql.redissample;

import com.google.common.io.Resources;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.nosql.redis.models.Person;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

@ComponentScan(
		basePackages = {
				"com.nosql"
		}
)
@SpringBootApplication
@EnableRedisRepositories(basePackages = {
		"com.nosql.redis.models"
})
public class RedisExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisExampleApplication.class, args);
	}

	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		RedisStandaloneConfiguration conf =
				new RedisStandaloneConfiguration(
						System.getenv("REDIS_HOST"),
						Integer.valueOf(System.getenv("REDIS_PORT"))
				);
		return new JedisConnectionFactory(conf);
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(jedisConnectionFactory());
		return template;
	}

	@Bean
	List<Person> fake() throws IOException {
		URL url = Resources.getResource("peopleMock.json");
		String text = Resources.toString(url, StandardCharsets.UTF_8);

		final Type TYPE = new TypeToken<List<Person>>() {}.getType();
		return new Gson().fromJson(text, TYPE);
	}
}
