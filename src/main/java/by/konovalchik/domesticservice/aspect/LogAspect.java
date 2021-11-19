package by.konovalchik.domesticservice.aspect;


import by.konovalchik.domesticservice.dto.taskDTO.AllArgsTaskDTO;
import by.konovalchik.domesticservice.dto.userDTO.AllArgUserDTO;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;




@Aspect
@Component
public class LogAspect {

     private final Logger logger = LoggerFactory.getLogger(LogAspect.class.getName());



    @Pointcut(value = "execution(public * by.konovalchik.domesticservice.controller.GuestController.registration(..)) && args(userDTO,..) ")
    public void registrationUser(AllArgUserDTO userDTO){}


    @AfterReturning(value = "registrationUser(userDTO)", returning = "flag", argNames = "flag,userDTO")
    public void regUser(Boolean flag, AllArgUserDTO userDTO){
        if(flag) {
            logger.info("Registered new user {}!", userDTO.getUsername());
        }
    }




    @Pointcut(value = "execution(public * by.konovalchik.domesticservice.service.UserService.loadUserByUsername(..)) && args(username,..) ")
    public void authUser(String username){}


    @AfterReturning(value = "authUser(username)", returning = "flag", argNames = "flag, username")
    public void authorizationUser(Boolean flag, String username){
        if(flag) {
            logger.info("User {} sign in!", username);
        }else{
            logger.info("Failed login attempt! ");
        }
    }





    @Pointcut(value = "execution(public * by.konovalchik.domesticservice.controller.UserTaskController.createTask(..)) && args(allArgsTaskDTO,..) ")
    public void crTask(AllArgsTaskDTO allArgsTaskDTO){}


    @AfterReturning(value = "crTask(allArgsTaskDTO)",  argNames = "flag,allArgsTaskDTO",  returning = "flag")
    public void createTask(Boolean flag, AllArgsTaskDTO allArgsTaskDTO){
        if(flag) {
            logger.info("Task has been created {}!", allArgsTaskDTO.getId());
        }
    }




    @Pointcut(value = "execution(public * by.konovalchik.domesticservice.controller.UserTaskController.deleteTask(..)) && args(id,..) ")
    public void delTask(long id){}


    @AfterReturning(value = "delTask(id)",  argNames = "flag,id",  returning = "flag")
    public void deleteTask(Boolean flag, long id){
        if(flag) {
            logger.info("Task has been deleted! Task id: {} !", id);
        }
    }


}
