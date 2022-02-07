package by.konovalchik.domesticservice.controller;


import by.konovalchik.domesticservice.dto.taskDTO.AllArgsTaskDTO;
import by.konovalchik.domesticservice.dto.userDTO.AllArgUserDTO;
import by.konovalchik.domesticservice.entity.*;
import by.konovalchik.domesticservice.service.RatingService;
import by.konovalchik.domesticservice.service.TaskService;
import by.konovalchik.domesticservice.service.UserService;
import by.konovalchik.domesticservice.utils.ConverterDTO;
import net.minidev.json.JSONUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class TaskControllerTest {

    @Autowired
    UserService userService;

    @Autowired
    TaskService taskService;



    @BeforeAll
    void initUserAndTask(){
        AllArgUserDTO allArgUserDTO = AllArgUserDTO.builder()
                .role(Role.ROLE_USER)
                .firstName("Jon")
                .lastName("Smith")
                .email("smith@gmail.com")
                .telephone("375339991100")
                .picture("https://basaschools.co.za/wp-content/uploads/2021/04/boy-avator.png")
                .username("userUser")
                .password("1qaz!QAZ")
                .build();
        User user = ConverterDTO.getAllArgUsersDTO(allArgUserDTO);
        userService.save(user);

        AllArgsTaskDTO taskDTO = AllArgsTaskDTO.builder()
                .name("Need Help")
                .description("Need help setting up a personal computer")
                .category(CategoryOfTask.ELECTRICIAN)
                .telephone("375299991133")
                .city("Minsk")
                .street("Odincova")
                .house("34")
                .apartment("1")
                .price(100)
                .express(true)
                .status(TaskStatus.ACTIVE)
                .build();
        Task task = ConverterDTO.getAllArgsTaskDTO(taskDTO);
        User userBase= (User)userService.loadUserByUsername("userUser");
        taskService.createTask(task, userBase);


    }


    @ParameterizedTest
    @EnumSource(CategoryOfTask.class)
    void categoryOfTaskDetectionTest(CategoryOfTask category){
        assertNotNull(category);
    }


    @ParameterizedTest
    @EnumSource(TaskStatus.class)
    void taskStatusDetectionTest(TaskStatus status){
        assertNotNull(status);
    }





    @Test
    void createTaskTest(){
        AllArgsTaskDTO taskDTO = AllArgsTaskDTO.builder()
                .name("Assembly of furniture from IKEA")
                .description("Need help assembling a large cabinet")
                .category(CategoryOfTask.FURNITURE)
                .telephone("375443335599")
                .city("Minsk")
                .street("Slavinskogo")
                .house("21")
                .apartment("85")
                .price(150)
                .express(true)
                .status(TaskStatus.ACTIVE)
                .build();
        Task task = ConverterDTO.getAllArgsTaskDTO(taskDTO);
        User userBase= (User)userService.loadUserByUsername("userUser");

        assertTrue(taskService.createTask(task, userBase));
    }


    @Test
    void getTaskTest() {
        User user= (User)userService.loadUserByUsername("userUser");
        Optional<List<Task>> tasks = taskService.getAllByUserId(user.getId());

        assertTrue(tasks.isPresent());
        assertEquals(2, tasks.get().size());

    }



     @Test
    void updateTaskCategoryTest(){
        taskService.updateCategory(3, CategoryOfTask.OTHER);
        Optional<Task> taskOpt = taskService.getTaskById(3);
         taskOpt.ifPresent(task -> assertEquals(CategoryOfTask.OTHER, task.getCategory()));
    }




    @ParameterizedTest
    @CsvSource({
            "Grodno, Minskaya, 12, 1",
             "Brest, Mira, 34, 23"

    })
    void updateTaskAddress(String city, String street, String house, String apartment){
        AddressTask addressTask = AddressTask.builder()
                .city(city)
                .street(street)
                .house(house)
                .apartment(apartment)
                .build();
        taskService.updateAddress(3, addressTask);
        Optional<Task> taskOpt = taskService.getTaskById(3);
        taskOpt.ifPresent(task -> assertEquals(task.getAddressTask().getStreet(), street));
    }






    @AfterAll
    void deleteUserByUsername(){
        userService.delete("userUser");
    }


}
