package by.konovalchik.domesticservice.controller;


import by.konovalchik.domesticservice.dto.addressDTO.AllArgsAddressDTO;
import by.konovalchik.domesticservice.dto.cardDTO.TaskUserDTO;
import by.konovalchik.domesticservice.dto.taskDTO.*;
import by.konovalchik.domesticservice.dto.telephoneDTO.IdNumberTelDTO;
import by.konovalchik.domesticservice.entity.CategoryOfTask;
import by.konovalchik.domesticservice.entity.Task;
import by.konovalchik.domesticservice.entity.User;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/user/userAccount")
public class UserTaskController {


    @Autowired
    UserService userService;

    @Autowired
    TaskService taskService;





    @GetMapping("/createTask")
    public ModelAndView createTask(ModelAndView modelAndView){
        modelAndView.setViewName("createTask");
        modelAndView.addObject("categories", Arrays.asList(CategoryOfTask.values()));
        modelAndView.addObject("allArgTaskDTO", new AllArgsTaskDTO());
        return modelAndView;
    }



    @PostMapping("/createTask")
    public ModelAndView createTask(@Valid @ModelAttribute("allArgTaskDTO") AllArgsTaskDTO allArgsTaskDTO,
                                           BindingResult bindingResult, ModelAndView modelAndView){
        modelAndView.setViewName("createTask");
        if (!bindingResult.hasErrors()) {
            User user = userService.getCurrentUser();
            Task task = ConverterDTO.getAllArgsTaskDTO(allArgsTaskDTO);
                if (taskService.createTask(task, user)){
                    modelAndView.addObject("messageCreateTask1", ControllerMessageManager.SAVE_TASK_SUCCESSFULLY);
                } else {
                    modelAndView.addObject("messageCreateTask2", ControllerMessageManager.SAVE_TASK_FAIL);
                }
        }
        return modelAndView;
    }



    @GetMapping("/deleteTask/{id}")
    public ModelAndView deleteTask(@PathVariable long id, ModelAndView modelAndView){
        Optional<Task> taskOpt = taskService.getTaskById(id);
        if(taskOpt.isPresent()){
            User user = userService.getCurrentUser();
            if(taskService.delete(user, taskOpt.get())){
                modelAndView.addObject("messageDeleteTask1", ControllerMessageManager.DELETE_TASK_SUCCESSFULLY);
            }else{
                modelAndView.addObject("messageDeleteTask2", ControllerMessageManager.DELETE_TASK_FAIL);
            }
        }
        return modelAndView;
    }




    @GetMapping("/getTask")
    public ModelAndView userHome(ModelAndView modelAndView){
        modelAndView.setViewName("userAccount");
        User user = userService.getCurrentUser();
        Optional<List<Task>> tasks = taskService.getAllByUserId(user.getId());
        if(tasks.isPresent()){
            List<TaskUserDTO> listTaskDTO = ConverterDTO.getListTaskUserCard(tasks.get());
            modelAndView.addObject("listTask", listTaskDTO);
        }
        return modelAndView;
    }




    @GetMapping("/updateTaskCategory/{id}")
    public ModelAndView updateTaskCategory(@PathVariable long id, ModelAndView modelAndView){
        modelAndView.setViewName("updateTaskCategory");
        modelAndView.addObject("taskId", id);
        modelAndView.addObject("categories", Arrays.asList(CategoryOfTask.values()));
        modelAndView.addObject("updateTaskCategoryDTO", new CategoryTaskDTO());
        return modelAndView;
    }



    @PostMapping("/updateTaskCategory")
    public ModelAndView updateTaskCategory(@Valid @ModelAttribute("updateTaskCategoryDTO") CategoryTaskDTO categoryTaskDTO,
                                       BindingResult bindingResult, ModelAndView modelAndView){
        modelAndView.setViewName("updateTaskCategory");
        if (!bindingResult.hasErrors()) {
            if (taskService.updateCategory(categoryTaskDTO.getId(), categoryTaskDTO.getCategory())) {
                modelAndView.addObject("messageTaskCategory1", ControllerMessageManager.UPDATE_CATEGORY_SUCCESSFULLY);
            } else {
                modelAndView.addObject("messageTaskCategory2", ControllerMessageManager.UPDATE_CATEGORY_FAIL);
            }
        }
        return modelAndView;
    }





    @GetMapping("/updateTaskDescription/{id}")
    public ModelAndView updateTaskDescription(@PathVariable long id, ModelAndView modelAndView){
        modelAndView.setViewName("updateTaskDescription");
        modelAndView.addObject("taskId", id);
        modelAndView.addObject("updateTaskDescriptionDTO", new DescriptionTaskDTO());
        return modelAndView;
    }



    @PostMapping("/updateTaskDescription")
    public ModelAndView updateTaskDescription(@Valid @ModelAttribute("updateTaskDescriptionDTO") DescriptionTaskDTO descriptionTaskDTO,
                                       BindingResult bindingResult, ModelAndView modelAndView) {
        modelAndView.setViewName("updateTaskDescription");
        if (!bindingResult.hasErrors()) {
            if (taskService.updateDescription(descriptionTaskDTO.getId(), descriptionTaskDTO.getDescription())) {
                if (taskService.updateName(descriptionTaskDTO.getId(), descriptionTaskDTO.getName())) {
                    modelAndView.addObject("messageTaskDescription1", ControllerMessageManager.UPDATE_DESCRIPTION_SUCCESSFULLY);
                } else {
                    modelAndView.addObject("messageTaskDescription2", ControllerMessageManager.UPDATE_DESCRIPTION_FAIL);
                }
            }
        }
        return modelAndView;
    }



    @GetMapping("/updateTaskTelephone/{id}")
    public ModelAndView updateTaskTelephone(@PathVariable long id, ModelAndView modelAndView){
        modelAndView.setViewName("updateTaskTelephone");
        modelAndView.addObject("taskId", id);
        modelAndView.addObject("updateTaskTelephoneDTO", new IdNumberTelDTO());
        return modelAndView;
    }



    @PostMapping("/updateTaskTelephone")
    public ModelAndView updateTaskTelephone(@Valid @ModelAttribute("updateTaskTelephoneDTO") IdNumberTelDTO idNumberTelDTO,
                                              BindingResult bindingResult, ModelAndView modelAndView) {
        modelAndView.setViewName("updateTaskTelephone");
        if (!bindingResult.hasErrors()) {
            if (taskService.updateTelephone(idNumberTelDTO.getId(), idNumberTelDTO.getTelephone())) {
                modelAndView.addObject("messageTaskTelephone1", ControllerMessageManager.UPDATE_NUMBER_SUCCESSFULLY);
            } else {
                modelAndView.addObject("messageTaskTelephone2", ControllerMessageManager.UPDATE_NUMBER_FAIL);
            }
        }
        return modelAndView;
    }



    @GetMapping("/updateTaskAddress/{id}")
    public ModelAndView updateTaskAddress(@PathVariable long id, ModelAndView modelAndView){
        modelAndView.setViewName("updateTaskAddress");
        modelAndView.addObject("taskId", id);
        modelAndView.addObject("updateTaskAddressDTO", new AllArgsAddressDTO());
        return modelAndView;
    }



    @PostMapping("/updateTaskAddress")
    public ModelAndView updateTaskAddress(@Valid @ModelAttribute("updateTaskAddressDTO") AllArgsAddressDTO allArgsAddressDTO,
                                            BindingResult bindingResult, ModelAndView modelAndView) {
        modelAndView.setViewName("updateTaskAddress");
        if (!bindingResult.hasErrors()) {
            if (taskService.updateAddress(allArgsAddressDTO.getId(), ConverterDTO.getAddressTask(allArgsAddressDTO))) {
                modelAndView.addObject("messageTaskAddress1", ControllerMessageManager.UPDATE_ADDRESS_SUCCESSFULLY);
            } else {
                modelAndView.addObject("messageTaskAddress2", ControllerMessageManager.UPDATE_ADDRESS_FAIL);
            }
        }
        return modelAndView;
    }


    @GetMapping("/updateTaskPrice/{id}")
    public ModelAndView updateTaskPrice(@PathVariable long id, ModelAndView modelAndView){
        modelAndView.setViewName("updateTaskPrice");
        modelAndView.addObject("taskId", id);
        modelAndView.addObject("updateTaskPriceDTO", new PriceTaskDTO());
        return modelAndView;
    }



    @PostMapping("/updateTaskPrice")
    public ModelAndView updateTaskPrice(@Valid @ModelAttribute("updateTaskPriceDTO") PriceTaskDTO priceTaskDTO ,
                                            BindingResult bindingResult, ModelAndView modelAndView) {
        modelAndView.setViewName("updateTaskPrice");
        if (!bindingResult.hasErrors()) {
            if (taskService.updatePrice(priceTaskDTO.getId(), priceTaskDTO.getPrice())) {
                modelAndView.addObject("messageTaskPrice1", ControllerMessageManager.UPDATE_PRICE_SUCCESSFULLY);
            } else {
                modelAndView.addObject("messageTaskPrice2", ControllerMessageManager.UPDATE_PRICE_FAIL);
            }
        }
        return modelAndView;
    }





    @GetMapping("/updateTaskExpress/{id}")
    public ModelAndView updateTaskExpress(@PathVariable long id, ModelAndView modelAndView){
        modelAndView.setViewName("updateTaskExpress");
        modelAndView.addObject("taskId", id);
        modelAndView.addObject("updateTaskExpressDTO", new ExpressTaskDTO());
        return modelAndView;
    }



    @PostMapping("/updateTaskExpress")
    public ModelAndView updateTaskExpress(@Valid @ModelAttribute("updateTaskExpressDTO") ExpressTaskDTO expressTaskDTO,
                                            BindingResult bindingResult, ModelAndView modelAndView) {
        modelAndView.setViewName("updateTaskExpress");
        if (!bindingResult.hasErrors()) {
                taskService.changeExpress(expressTaskDTO.getId());
                modelAndView.addObject("messageTaskExpress1", ControllerMessageManager.UPDATE_EXPRESS_SUCCESSFULLY);
        }
        return modelAndView;
    }



}
