package by.konovalchik.domesticservice.controller;


import by.konovalchik.domesticservice.dto.addressDTO.AllArgsAddressDTO;
import by.konovalchik.domesticservice.dto.cardDTO.TaskCardDTO;
import by.konovalchik.domesticservice.dto.gradeDTO.GradeDTO;
import by.konovalchik.domesticservice.dto.taskDTO.*;
import by.konovalchik.domesticservice.dto.telephoneDTO.IdNumberTelDTO;
import by.konovalchik.domesticservice.entity.CategoryOfTask;
import by.konovalchik.domesticservice.entity.Task;
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

    @Autowired
    RatingService ratingService;



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
    public ModelAndView deleteTask(@PathVariable @Min(value=1) long id, ModelAndView modelAndView){
        modelAndView.setViewName("taskToDelete");
        User user = userService.getCurrentUser();
        if(taskService.delete(user, id)){
             modelAndView.addObject("messageDeleteTask1", ControllerMessageManager.DELETE_TASK_SUCCESSFULLY);
        }else{
             modelAndView.addObject("messageDeleteTask2", ControllerMessageManager.DELETE_TASK_FAIL);
        }
        return modelAndView;
    }



    @GetMapping("/closeTask/{id}")
    public ModelAndView closeTask(@PathVariable @Min(value=1) long id,ModelAndView modelAndView){
        modelAndView.addObject("taskIdToClose", id);
        modelAndView.addObject("gradeDTO", new GradeDTO());
        modelAndView.setViewName("taskToCompleted");
        User user = userService.getCurrentUser();
        if(taskService.closeTask(id, user)){
            modelAndView.addObject("messageTaskCompleted1", ControllerMessageManager.CLOSE_TASK_SUCCESSFULLY);
        }else{
            modelAndView.addObject("messageTaskCompleted2", ControllerMessageManager.CLOSE_TASK_FAIL);
        }
        return modelAndView;
    }



    @PostMapping("/closeTask")
    public ModelAndView closeTask(@Valid @ModelAttribute("GradeDTO") GradeDTO gradeDTO, BindingResult bindingResult, ModelAndView modelAndView){
        modelAndView.setViewName("ratingToUpdateUs");
        if(!bindingResult.hasErrors()) {
            User user = userService.getCurrentUser();
            if (ratingService.updateRatingUser(gradeDTO.getGrade(), user.getId(), gradeDTO.getId())) {
                modelAndView.addObject("messageRatingUpdate1", ControllerMessageManager.UPDATE_RATING_SUCCESSFULLY);
            } else {
                modelAndView.addObject("messageRatingUpdate2", ControllerMessageManager.UPDATE_RATING_FAIL);
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
            List<TaskCardDTO> listTaskDTO = ConverterDTO.getListTaskUserCard(tasks.get(), user.getId());
            modelAndView.addObject("listTask", listTaskDTO);
        }
        return modelAndView;
    }



    @GetMapping("/getTaskStatus/{statusTask}")
    public ModelAndView userHomeStatus(@PathVariable("statusTask") @NotEmpty String status, ModelAndView modelAndView){
        modelAndView.setViewName("userAccount");
        User user = userService.getCurrentUser();
        Optional<List<Task>> tasks = taskService.getAllByUserIdAndStatus(user.getId(), status);
        if(tasks.isPresent()){
            List<TaskCardDTO> listTaskDTO = ConverterDTO.getListTaskUserCard(tasks.get(), user.getId());
            modelAndView.addObject("listTask", listTaskDTO);
        }
        return modelAndView;
    }



    @GetMapping("/getTaskCategory/{categoryTask}")
    public ModelAndView userHomeCategory(@PathVariable("categoryTask") @NotEmpty String category, ModelAndView modelAndView){
        modelAndView.setViewName("userAccount");
        User user = userService.getCurrentUser();
        Optional<List<Task>> tasks = taskService.getAllByUserIdAndCategory(user.getId(), category);
        if(tasks.isPresent()){
            List<TaskCardDTO> listTaskDTO = ConverterDTO.getListTaskUserCard(tasks.get(), user.getId());
            modelAndView.addObject("listTask", listTaskDTO);
        }
        return modelAndView;
    }



    @GetMapping("/updateTaskCategory/{id}")
    public ModelAndView updateTaskCategory(@PathVariable @Min(value=1) long id, ModelAndView modelAndView){
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
    public ModelAndView updateTaskDescription(@PathVariable @Min(value = 1) long id, ModelAndView modelAndView){
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
    public ModelAndView updateTaskTelephone(@PathVariable @Min(value=1) long id, ModelAndView modelAndView){
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
    public ModelAndView updateTaskAddress(@PathVariable @Min(value=1) long id, ModelAndView modelAndView){
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
    public ModelAndView updateTaskPrice(@PathVariable @Min(value=1) long id, ModelAndView modelAndView){
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
    public ModelAndView updateTaskExpress(@PathVariable @Min(value=1) long id, ModelAndView modelAndView){
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
