package com.cdac.management.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cdac.management.entity.Role;




@Repository
public interface RoleRepository extends CrudRepository<Role,Integer> {

}