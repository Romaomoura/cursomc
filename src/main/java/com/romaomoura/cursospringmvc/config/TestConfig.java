package com.romaomoura.cursospringmvc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.romaomoura.cursospringmvc.services.DBService;

@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	private DBService dbSrvice;

	@Bean
	public boolean instantiateDatabase() throws ParseException {
		dbSrvice.instatieteTestDatabase();
		return true;
	}

}
