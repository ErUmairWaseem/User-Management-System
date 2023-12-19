package com.cdac.management.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cdac.management.entity.User;



@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

	
	// countById 
	public Long countById(Integer id);
	
	
	
	// FOR isEmailUnique
	@Query("SELECT u FROM  User u WHERE u.email = :email")
	public User getByUserEmail(@Param("email") String email);
	
	
	
	
	
	
	// FOR Status enabled/disabled
	@Query("UPDATE User u SET  u.enabled = ?2 WHERE u.id=?1")
	@Modifying
	public void userStatusEnableDisable(Integer id, boolean enabled);
		/* NOTE
		 * The @Modifying annotation is used to enhance the @Query annotation to execute
		 *  not only SELECT
		 *  queries but also INSERT, UPDATE, DELETE, and even DDL queries.
		 * */
	
	
	// FOR SEARCH
	@Query("SELECT u FROM User u WHERE  CONCAT(u.id,' ', u.email,' ', u.firstName, ' ',u.lastName)  LIKE %?1%") 										
	public Page<User> findAll(String searchKeyword, Pageable pageable); 
	
	
	
}