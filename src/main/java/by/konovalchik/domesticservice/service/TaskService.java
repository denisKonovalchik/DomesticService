package by.konovalchik.domesticservice.service;

import by.konovalchik.domesticservice.entity.*;
import by.konovalchik.domesticservice.repository.AddressTaskRepository;
import by.konovalchik.domesticservice.repository.TaskRepository;
import by.konovalchik.domesticservice.repository.TelephoneTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TelephoneTaskRepository telephoneRepository;

    @Autowired
    AddressTaskRepository addressRepository;


    public boolean createTask(Task task, User user) {
           if(user.getRoles().contains(Role.ROLE_USER)) {
               List<User> users = new ArrayList<>();
               users.add(user);
               task.setUsers(users);
               taskRepository.save(task);
               return true;
           }
        return false;
    }


    public boolean delete(User user, Task task) {
        if (task.getStatus().equals(TaskStatus.ACTIVE)){
            if ((user.getRoles().contains(Role.ROLE_USER) || user.getRoles().contains(Role.ROLE_ADMIN))){
                  taskRepository.deleteById(task.getId());
                   return true;
            }
        }
       return true;
    }



    public boolean updateName(long taskId, String newName){
        Optional<Task> taskOpt = taskRepository.findById(taskId);
        if(taskOpt.isPresent()){
            Task taskBase = taskOpt.get();
            if(taskBase.getStatus().equals(TaskStatus.ACTIVE)){
                taskBase.setName(newName);
                taskRepository.save(taskBase);
                return true;
            }else {
                return false;
            }
        }
        return false;
    }



    public boolean updateDescription(long taskId, String newDescription){
        Optional<Task> taskOpt = taskRepository.findById(taskId);
        if(taskOpt.isPresent()){
            Task taskBase = taskOpt.get();
            if(taskBase.getStatus().equals(TaskStatus.ACTIVE)){
                taskBase.setDescription(newDescription);
                taskRepository.save(taskBase);
                return true;
            }else{
                return false;
            }
        }
        return false;
    }



    public boolean updateTelephone(long taskId, String newNumber){
        Optional<Task> taskOpt = taskRepository.findById(taskId);
        if(taskOpt.isPresent()){
            Task taskBase = taskOpt.get();
            if(taskBase.getStatus().equals(TaskStatus.ACTIVE)){
                taskBase.setTelephoneTask(TelephoneTask.builder().number(newNumber).build());
                taskRepository.save(taskBase);
                return true;
            }else{
                return false;
            }
        }
        return false;
    }



    public boolean updateAddress(long taskId, AddressTask newAddress){
        Optional<Task> taskOpt = taskRepository.findById(taskId);
        if(taskOpt.isPresent()){
            Task taskBase = taskOpt.get();
            if(taskBase.getStatus().equals(TaskStatus.ACTIVE)){
                AddressTask addressBase = taskBase.getAddressTask();
                addressBase.setCity(newAddress.getCity());
                addressBase.setStreet(newAddress.getStreet());
                addressBase.setHouse(newAddress.getHouse());
                addressBase.setHouse(newAddress.getApartment());
                taskRepository.save(taskBase);
                return true;
            }
        }return false;
    }



    public boolean updatePrice(long taskId, double newPrice){
        Optional<Task> taskOpt = taskRepository.findById(taskId);
        if(taskOpt.isPresent()){
            Task taskBase = taskOpt.get();
            if(taskBase.getStatus().equals(TaskStatus.ACTIVE)){
                taskBase.setPrice(newPrice);
                taskRepository.save(taskBase);
                return true;
            }else{
                return false;
            }
        }
        return false;
    }




    public boolean updateCategory(long taskId, CategoryOfTask newCategory){
        Optional<Task> taskOpt = taskRepository.findById(taskId);
        if(taskOpt.isPresent()){
            Task taskBase = taskOpt.get();
            if(taskBase.getCategory().equals(newCategory) && (!taskBase.getStatus().equals(TaskStatus.ACTIVE))){
                return false;
            }else{
                taskBase.setCategory(newCategory);
                taskRepository.save(taskBase);
                return true;
            }
        }
        return false;
    }



    public boolean updateStatus(long taskId, TaskStatus newStatus){
        Optional<Task> taskOpt = taskRepository.findById(taskId);
        if(taskOpt.isPresent()){
            Task taskBase = taskOpt.get();
            if(taskBase.getStatus().equals(TaskStatus.ACTIVE)){
                taskBase.setStatus(newStatus);
                taskRepository.save(taskBase);
                return true;
            }else{
                return  false;
            }
        }
        return false;
    }








    public boolean changeExpress(long taskId){
        Optional<Task> taskOpt = taskRepository.findById(taskId);
        if(taskOpt.isPresent()){
            Task taskBase = taskOpt.get();
            if(taskBase.getStatus().equals(TaskStatus.ACTIVE)) {
                taskBase.setExpress(!taskBase.isExpress());
                taskRepository.save(taskBase);
                return true;
            }else {
                return false;
            }
        }
        return false;
    }




    public boolean getTaskToWork(long taskId, User executor){
        Optional<Task> taskOpt = taskRepository.findTaskById(taskId);
        if(taskOpt.isPresent()) {
            Task taskBase = taskOpt.get();
            List<User> users = taskBase.getUsers();
            long executorCount = users.stream().filter(user -> user.getRoles().contains(Role.ROLE_EXECUTOR)).count();
            if ((users.size() > 1 && executorCount > 0)  && (!taskBase.getStatus().equals(TaskStatus.ACTIVE)) ) {
                return false;
            } else {
                users.add(executor);
                taskBase.setUsers(users);
                taskBase.setStatus(TaskStatus.WORK);
                taskRepository.save(taskBase);
                return  true;
            }
        }
        return true;
    }


    public boolean deleteUserExecutor(long taskId, User executor){
        Optional<Task> taskOpt = taskRepository.findById(taskId);
        if(taskOpt.isPresent()) {
            Task taskBase = taskOpt.get();
            List<User> users = taskBase.getUsers();
             long count = users.stream().filter(u -> u.getId() == executor.getId()).count();
            if (count > 0) {
                users.remove(executor);
                taskBase.setUsers(users);
                taskBase.setStatus(TaskStatus.ACTIVE);
                taskRepository.save(taskBase);
                return  true;
            } else {
              return false;
            }
        }
        return false;
    }


    public Optional<Task> getTaskById(long taskId){
        return taskRepository.findTaskById(taskId);
    }


    public Optional<List<Task>> getTaskByStatus(TaskStatus status ){
        return  taskRepository.findAllByStatus(status);

    }


    public Optional<List<Task>> getAllByUserId(long userId){
            List<Long> tasksId = taskRepository.findAllIdByUserId(userId);
            if (tasksId.isEmpty()) {
                return Optional.empty();
            } else {
                List<Task> tasks = new ArrayList<>();
                for (Long id : tasksId) {
                    Optional<Task> taskOpt = taskRepository.findById(id);
                    taskOpt.ifPresent(tasks::add);
                }
                return Optional.of(tasks);
            }
    }




    public Optional<List<Task>> getAllActiveAndWorkTask(long executorId){
        Optional<List<Task>> tasksActiveOpt = taskRepository.findAllByStatus(TaskStatus.ACTIVE);
        Optional<List<Task>> taskExecutorOpt = getAllByUserId(executorId);
        List<Task> mainList = new ArrayList<>();
        tasksActiveOpt.ifPresent(mainList::addAll);
        taskExecutorOpt.ifPresent(mainList::addAll);
        return Optional.of(mainList);
    }





    public List<Task> sortedByStatus(List<Task> taskList){
        return taskList.stream().sorted(Comparator.comparing(Task::getStatus)).collect(Collectors.toList());
    }


    public List<Task> sortedByTime(List<Task> taskList){
        return taskList.stream().sorted(Comparator.comparing(Task::getLocalDateTime)).collect(Collectors.toList());

    }




}
