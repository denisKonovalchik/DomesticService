package by.konovalchik.domesticservice.cotrollerREST;


import by.konovalchik.domesticservice.dto.cardDTO.TaskCardDTO;
import by.konovalchik.domesticservice.entity.Task;
import by.konovalchik.domesticservice.entity.User;
import by.konovalchik.domesticservice.service.TaskService;
import by.konovalchik.domesticservice.service.UserService;
import by.konovalchik.domesticservice.utils.ConverterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/userInfo")
public class UserControllerREST {

    @Autowired
    UserService userService;

    @Autowired
    TaskService taskService;


    @GetMapping("/getUserTasks")
    public ResponseEntity<List<TaskCardDTO>> getAuthUser(){
        User user = userService.getCurrentUser();
        Optional<List<Task>> tasks = taskService.getAllByUserId(user.getId());
        if(tasks.isPresent()){
            List<TaskCardDTO> listTaskDTO = ConverterDTO.getListTaskUserCard(tasks.get(), user.getId());
            return ResponseEntity.ok(listTaskDTO);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


}
