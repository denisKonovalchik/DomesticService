package by.konovalchik.domesticservice.controller;


import by.konovalchik.domesticservice.dto.cardDTO.TaskUserDTO;
import by.konovalchik.domesticservice.entity.Task;
import by.konovalchik.domesticservice.entity.User;
import by.konovalchik.domesticservice.service.TaskService;
import by.konovalchik.domesticservice.service.UserService;
import by.konovalchik.domesticservice.utils.ControllerMessageManager;
import by.konovalchik.domesticservice.utils.ConverterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/executor/executorAccount")
public class ExecutorTaskController {

    @Autowired
    UserService userService;

    @Autowired
    TaskService taskService;




    @GetMapping("/getTask")
    public ModelAndView userHome(ModelAndView modelAndView){
        modelAndView.setViewName("executorAccount");
        User user = userService.getCurrentUser();
        Optional<List<Task>> tasks = taskService.getAllActiveAndWorkTask(user.getId());
        if(tasks.isPresent()){
            List<TaskUserDTO> listTaskDTO = ConverterDTO.getListTaskExecutorCard(tasks.get());
            modelAndView.addObject("listTaskEx", listTaskDTO);
        }
        return modelAndView;
    }



    @GetMapping("/taskToWork/{id}")
    public ModelAndView taskToWork(@PathVariable long id, ModelAndView modelAndView){
        modelAndView.setViewName("taskToWork");
        User user = userService.getCurrentUser();
        if(taskService.getTaskToWork(id, user)){
            modelAndView.addObject("messageTaskToWork1", ControllerMessageManager.TASK_TO_WORK_SUCCESSFULLY);
        }else{
            modelAndView.addObject("messageTaskToWork2", ControllerMessageManager.TASK_TO_WORK_FAIL);
        }
        return modelAndView;
    }

}
