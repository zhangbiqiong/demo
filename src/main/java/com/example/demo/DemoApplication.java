package com.example.demo;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	class Greeting {

		private final long id;
		private final String content;

		public Greeting(long id, String content) {
			this.id = id;
			this.content = content;
		}

		public long getId() {
			return id;
		}

		public String getContent() {
			return content;
		}
	}

	@RestController
	class GreetingController {

		// private static final String template = "Hello, %s!";
		private final AtomicLong counter = new AtomicLong();

		@Autowired
		private UserRepository userRepository;

		@RequestMapping("/greeting")
		public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {					
			return new Greeting(counter.incrementAndGet(), name);
		}

		@RequestMapping("/add")
		public Greeting add(@RequestParam(value = "name", defaultValue = "World") String name) {
			DemoUser o=new DemoUser();
			// o.setId(300);
			o.setName(name);
			o.setAge(20);
			userRepository.save(o);
			return new Greeting(counter.incrementAndGet(), o.getName());
		}

	}

}
