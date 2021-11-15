package by.konovalchik.domesticservice.service;


import by.konovalchik.domesticservice.entity.*;
import by.konovalchik.domesticservice.repository.AddressTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskAddressService {

    @Autowired
    AddressTaskRepository addressRepository;



}
