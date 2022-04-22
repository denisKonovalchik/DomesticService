package by.konovalchik.domesticservice.controller;


import by.konovalchik.domesticservice.dto.cardDTO.TaskCardDTO;
import by.konovalchik.domesticservice.dto.gradeDTO.GradeDTO;
import by.konovalchik.domesticservice.entity.Task;
import by.konovalchik.domesticservice.entity.TaskStatus;
import by.konovalchik.domesticservice.entity.User;
import by.konovalchik.domesticservice.service.RatingService;
import by.konovalchik.domesticservice.service.TaskService;
import by.konovalchik.domesticservice.service.UserService;
import by.konovalchik.domesticservice.utils.ControllerMessageManager;
import by.konovalchik.domesticservice.utils.ConverterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/executor/executorAccount")
public class ExecutorTaskController {

    @Autowired
    UserService userService;

    @Autowired
    TaskService taskService;

    @Autowired
    RatingService ratingService;



    @GetMapping("/getTask")
    public ModelAndView userHome(ModelAndView modelAndView){
        modelAndView.setViewName("executorAccount");
        User user = userService.getCurrentUser();
        Optional<List<Task>> tasks = taskService.getAllByUserIdOrStatus(user.getId(), "ACTIVE");
        if(tasks.isPresent()){
            List<TaskCardDTO> listTaskDTO = ConverterDTO.getListTaskUserCard(tasks.get(), user.getId());
            modelAndView.addObject("listTaskEx", listTaskDTO);
        }
        return modelAndView;
    }



    @GetMapping("/getTaskStatus/{statusTask}")
    public ModelAndView userHomeStatus(@PathVariable("statusTask") @NotEmpty String status, ModelAndView modelAndView){
        modelAndView.setViewName("executorAccount");
        User user = userService.getCurrentUser();
        Optional<List<Task>> tasks = taskService.getAllByExecutorIdAndStatus(user.getId(), status);
        if(tasks.isPresent()){
            List<TaskCardDTO> listTaskDTO = ConverterDTO.getListTaskUserCard(tasks.get(), user.getId());
            modelAndView.addObject("listTaskEx", listTaskDTO);
        }
        return modelAndView;
    }



    @GetMapping("/getTaskCategory/{categoryTask}")
    public ModelAndView userHomeCategory(@PathVariable("categoryTask") @NotEmpty String category, ModelAndView modelAndView){
        modelAndView.setViewName("executorAccount");
        User user = userService.getCurrentUser();
        Optional<List<Task>> tasks = taskService.getAllByUserIdOrStatusAndCategory(user.getId(), "ACTIVE", category);
        if(tasks.isPresent()){
            List<TaskCardDTO> listTaskDTO = ConverterDTO.getListTaskUserCard(tasks.get(), user.getId());
            modelAndView.addObject("listTaskEx", listTaskDTO);
        }
        return modelAndView;
    }



    @GetMapping("/taskToWork/{id}")
    public ModelAndView taskToWork(@PathVariable @Min(value=1) long id, ModelAndView modelAndView){
        modelAndView.setViewName("taskToWork");
        User user = userService.getCurrentUser();
        if(taskService.getTaskToWork(id, user)){
            modelAndView.addObject("messageTaskToWork1", ControllerMessageManager.TASK_TO_WORK_SUCCESSFULLY);
        }else{
            modelAndView.addObject("messageTaskToWork2", ControllerMessageManager.TASK_TO_WORK_FAIL);
        }
        return modelAndView;
    }



    @GetMapping("/taskToDone/{id}")
    public ModelAndView taskToDone(@PathVariable  @Min(value=1) long id, ModelAndView modelAndView){
        modelAndView.addObject("taskIdToDone", id);
        modelAndView.addObject("gradeDTOex", new GradeDTO());
        modelAndView.setViewName("taskToDone");
        if(taskService.updateStatus(id, TaskStatus.DONE)){
            modelAndView.addObject("messageTaskToDone1", ControllerMessageManager.TASK_TO_DONE_SUCCESSFULLY);
        }else{
            modelAndView.addObject("messageTaskToDone2", ControllerMessageManager.TASK_TO_DONE_FAIL);
        }
        return modelAndView;
    }



    @PostMapping("/taskToDone")
    public ModelAndView taskToDone(@Valid @ModelAttribute("gradeDTOex") GradeDTO gradeDTO, BindingResult bindingResult, ModelAndView modelAndView){
        modelAndView.setViewName("ratingToUpdateEx");
        if(!bindingResult.hasErrors()){
            User user = userService.getCurrentUser();
            if(ratingService.updateRatingUser(gradeDTO.getGrade(), user.getId(), gradeDTO.getId())){
                modelAndView.addObject("messageRatingUpdate1", ControllerMessageManager.UPDATE_RATING_SUCCESSFULLY);
            }else{
                modelAndView.addObject("messageRatingUpdate2", ControllerMessageManager.UPDATE_RATING_FAIL);
            }
        }
            return modelAndView;
    }


}
