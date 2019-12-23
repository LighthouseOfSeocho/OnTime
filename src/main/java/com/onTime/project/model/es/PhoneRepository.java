package com.onTime.project.model.es;

import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

public interface PhoneRepository extends ElasticsearchCrudRepository<Phone, Integer> {
    Page<Phone> findByAuthor(String author, Pageable pageable);
    List<Phone> findByNumber(String number);
}
