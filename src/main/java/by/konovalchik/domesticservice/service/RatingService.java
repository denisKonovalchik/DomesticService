package by.konovalchik.domesticservice.service;


import by.konovalchik.domesticservice.entity.Task;
import by.konovalchik.domesticservice.entity.User;
import by.konovalchik.domesticservice.entity.UserGrade;
import by.konovalchik.domesticservice.entity.UserRating;
import by.konovalchik.domesticservice.repository.TaskRepository;
import by.konovalchik.domesticservice.repository.UserGradeRepository;
import by.konovalchik.domesticservice.repository.UserRatingRepository;

import by.konovalchik.domesticservice.repository.UserRepository;
import org.apache.tomcat.util.digester.ArrayStack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class RatingService {

    @Autowired
    private UserRatingRepository ratingRepository;

    @Autowired
    private UserGradeRepository gradeRepository;

    @Autowired
    private TaskRepository taskRepository;




    public long getRatingIdUserByUserIdTaskId(long userId,long taskId ){
        Optional<Task> taskOpt = taskRepository.findTaskById(taskId);
        if(taskOpt.isPresent()){
            Task task = taskOpt.get();
            Optional<User> userOpt= task.getUsers().stream().filter(u -> u.getRating().getId() != userId).findFirst();
            if(userOpt.isPresent()){
                return userOpt.get().getRating().getId();
            }
        }
        return 0;
    }



    private boolean saveGrades(int grade, long userId, long taskId, long ratingId){
        if(ratingId > 0) {
            Optional<UserGrade> gradeOpt = gradeRepository.findGradeByUserIdRatingIdTaskId(userId, ratingId, taskId);
            if (gradeOpt.isPresent()) {
                return false;
            } else {
              gradeRepository.save(UserGrade.builder()
                        .grade(grade)
                        .task(Task.builder().id(taskId).build())
                        .user(User.builder().id(userId).build())
                        .userRating(UserRating.builder().id(ratingId).build())
                        .build());
                return true;
            }
        }
        return  false;
    }



    public boolean updateRatingUser(int grade, long userId,long taskId ) {
        long userRatingId = getRatingIdUserByUserIdTaskId(userId, taskId);
        if (saveGrades(grade, userId, taskId, userRatingId)) {
            Optional<UserRating> userRatingOpt = ratingRepository.findById(userRatingId);
            if (userRatingOpt.isPresent()) {
                UserRating userRating = userRatingOpt.get();
                Optional <Double>score = gradeRepository.getScore(userRatingId);
                if (score.isPresent()) {
                    userRating.setScore(score.get());
                    ratingRepository.save(userRating);
                    return true;
                }
            }
        }
        return false;
    }



    public Optional<Double> getRatingScoreById(long ratingUserId){
         return gradeRepository.getScore(ratingUserId);
     }



    public Optional<UserRating> getRatingById(long id){
        return ratingRepository.findById(id);
    }



}
