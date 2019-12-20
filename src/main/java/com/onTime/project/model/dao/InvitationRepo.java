package com.onTime.project.model.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.onTime.project.model.domain.Invitation;

public interface InvitationRepo extends CrudRepository<Invitation, Integer>{
	List<Invitation> findBySender(String userId);
	List<Invitation> findByRecipient(String userId);
}
