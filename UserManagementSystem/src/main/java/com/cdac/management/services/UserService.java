package com.cdac.management.services;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cdac.management.entity.Role;
import com.cdac.management.entity.User;
import com.cdac.management.repositories.RoleRepository;
import com.cdac.management.repositories.UserRepository;



@Service
@Transactional
public class UserService {

	public static final int USERS_PER_PAGE = 4;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	
	
	
	
	// LIST ALL USERS
	public List<User> listAllUsers(){
		return (List<User>) userRepository.findAll();
	}
	
	
	// CREATE ROLE
	public Role saveRole(Role role) {
		
		return roleRepository.save(role);
	}
	
	
	
	
	// CREATE & UPDATE
		public User save(User user) {
			boolean isUpdatingUser = (user.getId() != null);
			
			if(isUpdatingUser) {
				User existingUser = userRepository.findById(user.getId()).get();
				
				if(user.getPassword().isEmpty()) {
					user.setPassword(existingUser.getPassword());
					
				} else {
					encodePassword(user);
				}
				
			}
			else {
				encodePassword(user);
			}
			
			return userRepository.save(user);
		}
		
		
		

		//password Encode
		private void encodePassword(User user) {
			String encodedPassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(encodedPassword);
			
		}
		

	
	
	// LIST ALL ROLES 
	public List<Role> listAllRoles() {
		return (List<Role>) roleRepository.findAll();
	}
	
	
	
	//Email is Unique ?
		public boolean isEmailUnique(Integer id, String email) {
			User userEmail = userRepository.getByUserEmail(email);
			
			if(userEmail == null) return true;
			
			boolean isCreatingNew = (id == null);
			
			if(isCreatingNew) {
				if(userEmail != null) return false;
				
			} else {
				if(userEmail.getId() != id) {
					return false;
				}
			}
			
			return true;
		}
	
		
		
	
	// DELETE USER
	public void delete(Integer id) throws UserNotFoundException {
		Long takeById = userRepository.countById(id);
		
		if(takeById == null || takeById == 0) {		// if user id null -> Throw Exception
			throw new UserNotFoundException("Could not find any user ID : "+id);
		}
		
		// user has been id then delete it.
		userRepository.deleteById(id);
	}


	
	
	// UPDATE USER  - GET USER BY ID
	public User getUserById(Integer id) throws UserNotFoundException {
		//if user has  id 
		try {
			return userRepository.findById(id).get();
			
		} catch(NoSuchElementException ex) {		// handle exception
			throw new UserNotFoundException("Could not find any user with ID : "+id); 		//throw our custom exception
		}
	}
	
	
	
	
	
	// USER STATUS UPDATE  -  ENABLED/DISABLED
	public void userStatus(Integer id, boolean enabled) {
		userRepository.userStatusEnableDisable(id, enabled);
	}
	
	
	/*  // PAGINATION BEFORE SORT
	public Page<User> listByPagination(int pageNum) {
		Pageable pageable = PageRequest.of(pageNum - 1 , USERS_PER_PAGE );
		return userRepository.findAll(pageable);
	}*/
	
	
	
	
	
	// PAGINATION WITH SORTING
	public Page<User> listByPagination(int pageNumber, String sortField, String sortDir, String searchKeyword){
		
		// for sort
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		
		
		// for paginate
		Pageable pageable = PageRequest.of(pageNumber - 1 , USERS_PER_PAGE, sort);
		
		
		// for search
		if(searchKeyword != null) {
			return userRepository.findAll(searchKeyword, pageable);
		}
		
		
		return userRepository.findAll(pageable);
	}


	
	// for Account DETAIL
	public User getByUserEmail(String email) {
		return userRepository.getByUserEmail(email);
		
	}


	
	/// Account Update
	public User accountUpdate(User userInForm) {
		User userInDB =	userRepository.findById(userInForm.getId()).get();
		
		// password is not empty
		if(!userInForm.getPassword().isEmpty()) {
			userInDB.setPassword(userInForm.getPassword());
			encodePassword(userInDB);
		}
		
		// photos is not null
		if(userInForm.getPhotos() != null) {
			userInDB.setPhotos(userInForm.getPhotos());
		}
		
		userInDB.setFirstName(userInForm.getFirstName());
		userInDB.setLastName(userInForm.getLastName());
		
		return userRepository.save(userInDB);
		
	}
	
	
	
}












