package by.konovalchik.domesticservice.service;

import by.konovalchik.domesticservice.entity.Telephone;

import by.konovalchik.domesticservice.repository.TelephoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;



@Service
public class TelephoneService  {

    @Autowired
    TelephoneRepository  telephoneRepository;

    
    public boolean updateTelNumber(Telephone telephone, String number) {
        Optional<Telephone> telephoneOpt = telephoneRepository.findById(telephone.getId());
        if (telephoneOpt.isPresent()) {
            Telephone telBase = telephoneOpt.get();
            telBase.setNumber(number);
            telephoneRepository.save(telBase);
            return true;
        } else {
            return false;
        }
    }


}
