package com.cdac.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.cdac.management.entity","com.cdac.management.repositories"})
public class UsermanagementsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsermanagementsystemApplication.class, args);
	}

}
