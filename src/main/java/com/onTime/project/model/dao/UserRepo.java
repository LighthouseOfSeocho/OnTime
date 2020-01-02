package com.onTime.project.model.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.onTime.project.model.domain.User;

public interface UserRepo extends CrudRepository<User, String> {

}
