package com.cdac.management.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import com.cdac.management.entity.Role;
import com.cdac.management.entity.User;
import com.cdac.management.repositories.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private  TestEntityManager entityManager;
	
	

	
	// create user with one role
	@Test
	public void createUserWithOneRoleTest() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		Role adminRole = entityManager.find(Role.class,1);
		
		User userUgurHmz = new User("Ugur","Hamzaoglu","craxx67@gmail.com","1994ugur");
		userUgurHmz.addRole(adminRole);
		userUgurHmz.setEnabled(true);
		System.out.println("pw : "+ userUgurHmz.getPassword());
		
		 String encodedPassword = passwordEncoder.encode(userUgurHmz.getPassword());
		userUgurHmz.setPassword(encodedPassword);
		
		
		User userSaved = userRepo.save(userUgurHmz);
		
		assertThat(userSaved.getId()).isGreaterThan(0);
	}
	
	
	
	// crate multiple roles test
	@Test
	public void createMultipleRolesTest() {
		User userElif = new User("Elif","Duman","elif@gmail.com","1231231");
		
		Role editorRole = new Role(1);		//Admin id -> 1
		Role shipperRole = new Role(4);		//Shipper id -> 4
		Role assistantRole = new Role(5);	//Assistant id -> 5
		
		
		userElif.addRole(assistantRole);
		userElif.addRole(shipperRole);
		userElif.addRole(editorRole);
		userElif.setEnabled(true);
		assertThat(userRepo.save(userElif).getId()).isGreaterThan(0);
		
	}
	
	// test all users
	@Test
	public void listAllUsersTest() {
		Iterable<User>  users = userRepo.findAll();
		
		for(User user : users) {
			System.out.println(user);
		}
	}
	
	
	
	// test update user
	@Test
	public void updateUserTest() {
		User userUgur= userRepo.findById(1).get();
		
		userUgur.setEnabled(true);
		
		userRepo.save(userUgur);
	}
	
	
	
	// Pagination test
	@Test
	public void paginationUserListTest() {
		int pageNumber = 0;
		int pageSize = 4;
		
		// import domain...
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = userRepo.findAll(pageable);
		
		List<User> listUsers = page.getContent();
		listUsers.forEach( user -> System.out.println(user+ " "));
		
		assertThat(listUsers.size()).isEqualTo(pageSize);
	}

	
	
	
	// Search test
	@Test
	public void searchTest() {
		String searchKeyword = "a";
		
		int pageNumber = 0;
		int pageSize = 4;
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = userRepo.findAll(searchKeyword,pageable);
		
		
		
		List<User> listUsers = page.getContent();
		listUsers.forEach(user -> System.out.println("Found user : "+user));
		
		assertThat(listUsers.size()).isGreaterThan(0);
		
	}
	
	
	
	
	
	
}










