package com.onTime.project.model.es;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PhoneService {
 
    @Autowired
    private PhoneRepository phoneRepository;
 
    public Phone save(Phone phone) {
        return phoneRepository.save(phone);
    }
 
    public void delete(Phone phone) {
        phoneRepository.delete(phone);
    }
 
    public Phone findOne(Integer id) {
        return phoneRepository.findOne(id);
    }
 
    public Iterable<Phone> findAll() {
        return phoneRepository.findAll();
    }
 
    public Page<Phone> findByAuthor(String author, PageRequest pageRequest) {
        return phoneRepository.findByAuthor(author, pageRequest);
    }
 
    public List<Phone> findByNumber(String number) {
        return phoneRepository.findByNumber(number);
    }
}
