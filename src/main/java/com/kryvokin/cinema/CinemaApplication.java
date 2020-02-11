package com.kryvokin.cinema;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class CinemaApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(CinemaApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Set<String> optionalArguments = args.getOptionNames();
		args.containsOption("lol");
		args.getOptionValues("lol");
	}
}
