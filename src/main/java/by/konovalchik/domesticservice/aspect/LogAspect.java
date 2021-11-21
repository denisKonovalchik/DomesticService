package by.konovalchik.domesticservice.aspect;


import by.konovalchik.domesticservice.dto.taskDTO.AllArgsTaskDTO;
import by.konovalchik.domesticservice.dto.userDTO.AllArgUserDTO;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;


@Aspect
@Component
public class LogAspect {

     private final Logger logger = LoggerFactory.getLogger(LogAspect.class.getName());



//-------Guest-------

    @Pointcut(value = "execution(public * by.konovalchik.domesticservice.controller.GuestController.registration(..)) && args(userDTO,..) ")
    public void registrationUser(AllArgUserDTO userDTO){}


    @AfterReturning(value = "registrationUser(userDTO)", returning = "modelAndView", argNames = "modelAndView,userDTO")
    public void regUser(ModelAndView modelAndView, AllArgUserDTO userDTO){
        if(modelAndView.isEmpty()) {
            logger.info("Registered new user {}!", userDTO.getUsername());
        }else{
            logger.warn("failed username {} registration attempt", userDTO.getUsername() );
        }
    }


    @Pointcut(value = "execution(public * by.konovalchik.domesticservice.service.UserService.loadUserByUsername(..)) && args(username,..) ")
    public void authUser(String username){}


    @AfterReturning(value = "authUser(username)", returning = "userDetails", argNames = "userDetails,username")
    public void authorizationUser(UserDetails userDetails, String username){
        if(!userDetails.getAuthorities().isEmpty()) {
            logger.info("User {} sign in!", username);
        }else{
            logger.warn("failed username {} authorization attempt", username);
        }
    }


//-------Task-------

    @Pointcut(value = "execution(public * by.konovalchik.domesticservice.controller.UserTaskController.createTask(..)) && args(allArgsTaskDTO,..) ")
    public void crTask(AllArgsTaskDTO allArgsTaskDTO){}


    @AfterReturning(value = "crTask(allArgsTaskDTO)",  argNames = "modelAndView ,allArgsTaskDTO",  returning = "modelAndView")
    public void createTask(ModelAndView modelAndView, AllArgsTaskDTO allArgsTaskDTO){
        if(modelAndView.getModel().containsKey("messageCreateTask1")) {
            logger.info("Task has been created.");
        }else{
            logger.warn("failed create task attempt.");
        }
    }




    @Pointcut(value = "execution(public * by.konovalchik.domesticservice.controller.UserTaskController.deleteTask(..)) && args(id,..) ")
    public void delTask(long id){}


    @AfterReturning(value = "delTask(id)",  argNames = "modelAndView,id",  returning = "modelAndView")
    public void deleteTask(ModelAndView modelAndView, long id){
        if(modelAndView.getModel().containsKey("messageDeleteTask1")) {
            logger.info("Task has been deleted. Task id: {}.", id);
        }else{
            logger.warn("Task {} has not been deleted", id);
        }
    }


    @Pointcut(value = "execution(public * by.konovalchik.domesticservice.service.TaskService.closeTask(..)) && args(id,..) ")
    public void clTask(long id){}


    @AfterReturning(value = "clTask(id)",  argNames = "flag,id",  returning = "flag")
    public void closeTask(Boolean flag, long id){
        if(flag) {
            logger.info("Task {} has been changed status to COMPLETE", id);
        }else{
            logger.warn("Task {} has not been changed status", id);
        }
    }


    @Pointcut(value = "execution(public * by.konovalchik.domesticservice.service.TaskService.changeExpress(..)) && args(id,..) ")
    public void chanExpress(long id){}


    @AfterReturning(value = "chanExpress(id)",  argNames = "flag,id",  returning = "flag")
    public void changeExpress(Boolean flag, long id){
        if(flag) {
            logger.info("Task {} has been changed to EXPRESS", id);
        }else{
            logger.warn("Task {} has not been changed to EXPRESS ", id);
        }
    }





    @Pointcut(value = "execution(public * by.konovalchik.domesticservice.service.TaskService.getTaskToWork(..)) && args(id,..) ")
    public void taskToWork(long id){}


    @AfterReturning(value = "taskToWork(id)",  argNames = "flag,id",  returning = "flag")
    public void toWork(Boolean flag, long id){
        if(flag) {
            logger.info("Task {} has been changed to WORK", id);
        }else{
            logger.warn("Task {} has not been changed to WORK ", id);
        }
    }




    @Pointcut(value = "execution(public * by.konovalchik.domesticservice.controller.ExecutorTaskController.taskToDone(..)) && args(id,..) ")
    public void taskToDone(long id){}


    @AfterReturning(value = "taskToDone(id)",  argNames = "modelAndView,id",  returning = "modelAndView")
    public void toDone(ModelAndView modelAndView, long id){
        if(modelAndView.getModel().containsKey("messageTaskToDone1")) {
            logger.info("Task {} has been changed to Done", id);
        }else{
            logger.warn("Task {} has not been changed to Done ", id);
        }
    }




    @Pointcut(value = "execution(public * by.konovalchik.domesticservice.service.RatingService.updateRatingUser(..)) && args(id,..) ")
    public void updUserRating(long id){}


    @AfterReturning(value = "updUserRating(id)",  argNames = "flag,id",  returning = "flag")
    public void userRating(Boolean flag, long id){
        if(flag) {
            logger.info("The user {} has rated task", id);
        }else{
            logger.warn("The user {} has not been rated task", id);
        }
    }


}
