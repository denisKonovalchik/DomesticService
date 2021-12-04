package by.konovalchik.domesticservice.utils;

import by.konovalchik.domesticservice.dto.addressDTO.AllArgsAddressDTO;
import by.konovalchik.domesticservice.dto.cardDTO.TaskUserDTO;
import by.konovalchik.domesticservice.dto.cardDTO.UserInfoDTO;
import by.konovalchik.domesticservice.dto.taskDTO.AllArgsTaskDTO;
import by.konovalchik.domesticservice.dto.telephoneDTO.NumberTelDTO;
import by.konovalchik.domesticservice.dto.userDTO.AllArgUserDTO;
import by.konovalchik.domesticservice.dto.userDTO.UsernamePasswordUserDTO;
import by.konovalchik.domesticservice.entity.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class ConverterDTO {






    public static User getAllArgUsersDTO(AllArgUserDTO allArgUserDTO) {
        Set<Role> roleSet = new HashSet<>();
        Role role = allArgUserDTO.getRole();
        roleSet.add(role);
        return User.builder()
                .roles(roleSet)
                .firstName(allArgUserDTO.getFirstName())
                .lastName(allArgUserDTO.getLastName())
                .telephone(Telephone.builder()
                        .number(allArgUserDTO.getTelephone())
                        .build())
                .picture(allArgUserDTO.getPicture())
                .email(allArgUserDTO.getEmail())
                .username(allArgUserDTO.getUsername())
                .password(allArgUserDTO.getPassword())
                .build();
    }


    public static User getUsernamePasswordUserDTO(UsernamePasswordUserDTO usernamePasswordUserDTO) {
        return User.builder()
                .username(usernamePasswordUserDTO.getUsername())
                .password(usernamePasswordUserDTO.getPassword())
                .build();
    }


    public static Telephone getTelDTO(NumberTelDTO numberTelDTO) {
        return Telephone.builder()
                .number(numberTelDTO.getTelephone())
                .build();
    }


    public static Telephone getTelDTO(String number) {
        return Telephone.builder()
                .number(number)
                .build();
    }


    public static Task getAllArgsTaskDTO(AllArgsTaskDTO taskDTO){
          return  Task.builder()
                    .name(taskDTO.getName())
                    .description(taskDTO.getDescription())
                    .telephoneTask(TelephoneTask.builder()
                            .number(taskDTO.getTelephone())
                            .build())
                    .addressTask(AddressTask.builder()
                            .city(taskDTO.getCity())
                            .street(taskDTO.getStreet())
                            .house(taskDTO.getHouse())
                            .apartment(taskDTO.getApartment())
                            .build())
                    .price(taskDTO.getPrice())
                    .category(taskDTO.getCategory())
                    .status(TaskStatus.ACTIVE)
                    .express(taskDTO.isExpress())
                    .localDateTime(LocalDateTime.now())
                    .build();
    }



    public static UserInfoDTO getUserInfoCard (User user){

        return  UserInfoDTO.builder().user(User.builder().username(user.getUsername())
                                                         .firstName(user.getFirstName())
                                                         .lastName(user.getLastName())
                                                         .email(user.getEmail())
                                                         .telephone(user.getTelephone())
                                                         .rating(user.getRating())
                                                         .picture(user.getPicture())
                                                         .build())
                                                         .build();

    }




    public static TaskUserDTO getTaskUserCard(Task task) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(Patterns.TIME_FORMAT);
        String time = task.getLocalDateTime().format(format);
        Optional<User> userUsOpt = task.getUsers().stream().filter(user -> user.getRoles().contains(Role.ROLE_EXECUTOR)).findFirst();
        if (userUsOpt.isPresent()) {
            User userExecutor = userUsOpt.get();
            return TaskUserDTO.builder()
                    .task(task)
                    .user(User.builder().id(userExecutor.getId()).username(userExecutor.getUsername()).rating(userExecutor.getRating()).build())
                    .createdTime(time)
                    .build();
        }
        return TaskUserDTO.builder()
                .task(task)
                .user(User.builder().id(0).build())
                .createdTime(time)
                .build();
    }



    public static TaskUserDTO getTaskExecutorCard(Task task) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(Patterns.TIME_FORMAT);
        String time = task.getLocalDateTime().format(format);
        Optional<User> userExOpt = task.getUsers().stream().filter(user -> user.getRoles().contains(Role.ROLE_USER)).findFirst();
        if (userExOpt.isPresent()) {
            User userUser = userExOpt.get();
            return TaskUserDTO.builder()
                    .task(task)
                    .user(User.builder().id(userUser.getId()).username(userUser.getUsername()).rating(userUser.getRating()).build())
                    .createdTime(time)
                    .build();
        }
        return TaskUserDTO.builder()
                .task(task)
                .user(User.builder().id(0).build())
                .createdTime(time)
                .build();
    }



    public static List<TaskUserDTO> getListTaskUserCard(List<Task> tasks){
        List<TaskUserDTO> tasksDTO = new ArrayList<>();
        for (Task task: tasks) {
           tasksDTO.add(getTaskUserCard(task));
        }
        return tasksDTO;
    }


    public static List<TaskUserDTO> getListTaskExecutorCard(List<Task> tasks){
        List<TaskUserDTO> tasksDTO = new ArrayList<>();
        for (Task task: tasks) {
            tasksDTO.add(getTaskExecutorCard(task));
        }
        return tasksDTO;
    }



    public static AddressTask getAddressTask(AllArgsAddressDTO allArgsAddressDTO){

        return AddressTask.builder()
                                             .city(allArgsAddressDTO.getCity())
                                             .street(allArgsAddressDTO.getStreet())
                                             .house(allArgsAddressDTO.getHouse())
                                             .apartment(allArgsAddressDTO.getApartment())
                                             .build();
    }



}
