package com.onTime.project.model.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.onTime.project.model.es.Meeting;

@Repository("wedulPlayRepository")
public interface WedulPlayRepository extends ElasticsearchRepository<Meeting, String> {
	
	Meeting findByUser(String user);

}
