package by.konovalchik.domesticservice.service;


import by.konovalchik.domesticservice.repository.TelephoneTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TaskTelephoneService {

    @Autowired
    TelephoneTaskRepository telephoneRepository;



}
