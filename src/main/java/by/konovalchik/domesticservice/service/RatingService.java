package by.konovalchik.domesticservice.service;


import by.konovalchik.domesticservice.entity.User;
import by.konovalchik.domesticservice.entity.UserGrade;
import by.konovalchik.domesticservice.entity.UserRating;
import by.konovalchik.domesticservice.repository.UserGradeRepository;
import by.konovalchik.domesticservice.repository.UserRatingRepository;

import by.konovalchik.domesticservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class RatingService {

    @Autowired
    private UserRatingRepository ratingRepository;

    @Autowired
    private UserGradeRepository gradeRepository;



    public boolean updateRating(UserGrade grade){
        if(ratingRepository.existsById(grade.getUserRating().getId())){
            if(saveGrades(grade)){
               Optional<Double> score = gradeRepository.getScore(grade.getUserRating().getId());
               if(score.isPresent()){
                  double newScore = score.get();
                  Optional<UserRating> userRating = ratingRepository.findById(grade.getUserRating().getId());
                  if(userRating.isPresent()){
                      UserRating ratingBase = userRating.get();
                      ratingBase.setScore(newScore);
                      ratingRepository.save(ratingBase);
                      return true;
                   }
                }
            }
        }else{
           gradeRepository.save(grade);
           return true;
        }
        return false;
    }


    private boolean saveGrades(UserGrade grade){
       Optional<UserGrade> gradeOpt = gradeRepository.findGradeByUserAndRating(grade.getUser().getId(), grade.getUserRating().getId());
        if(gradeOpt.isPresent()){
            return false;
        }else{
            gradeRepository.saveGrade(grade.getUserRating().getId(),grade.getUser().getId(), grade.getGrade());
            return true;
        }
    }


    public boolean deleteRating(long  id){
        if(ratingRepository.existsById(id)){
            ratingRepository.deleteById(id);
            return  true;
        }
        return false;
    }


    public Optional<UserRating> getRatingById(long id){
        return ratingRepository.findById(id);
    }


    public boolean isExistRatingById(long id){
        return ratingRepository.existsById(id);
    }




}
