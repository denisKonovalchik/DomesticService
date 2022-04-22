package by.konovalchik.domesticservice.service;

import by.konovalchik.domesticservice.entity.*;
import by.konovalchik.domesticservice.repository.AddressTaskRepository;
import by.konovalchik.domesticservice.repository.TaskRepository;
import by.konovalchik.domesticservice.repository.TelephoneTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Transactional
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



    public boolean delete(User user, long taskId) {
        Optional<Task> taskOpt = taskRepository.findById(taskId);
        if(taskOpt.isPresent()) {
            Task task = taskOpt.get();
            if (task.getStatus().equals(TaskStatus.ACTIVE)) {
                if ((user.getRoles().contains(Role.ROLE_USER) || user.getRoles().contains(Role.ROLE_ADMIN))) {
                    taskRepository.deleteById(task.getId());
                    return true;
                }
            }
        }
        return false;
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
            if(taskBase.getStatus().equals(newStatus)){
               return false;
            }else {
                taskBase.setStatus(newStatus);
                taskRepository.save(taskBase);
                return true;
            }
        }
        return  false;
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



    public Optional<List<Task>> getAllByUserId(long userId){
            Optional<List<Long>> tasksId = taskRepository.findAllIdByUserId(userId);
            if (tasksId.isPresent()) {
                return taskRepository.findAllByListId(tasksId.get());
            } else {
                return Optional.empty();
            }
    }



    public Optional<List<Task>> getAllByUserIdAndStatus(long userId, String status){
        Optional<List<Long>> tasksId = taskRepository.findAllIdByUserId(userId);
        if(tasksId.isPresent()) {
            TaskStatus taskStatus = TaskStatus.valueOf(status);
            return taskRepository.findAllByListIdAndStatus(tasksId.get(), taskStatus);
        }else{
            return Optional.empty();
        }
    }



    public Optional<List<Task>> getAllByUserIdAndCategory(long userId, String category){
        Optional<List<Long>> tasksId = taskRepository.findAllIdByUserId(userId);
        if(tasksId.isPresent()) {
            CategoryOfTask taskCategory = CategoryOfTask.valueOf(category);
            return taskRepository.findAllByListIdAndCategory(tasksId.get(), taskCategory);
        }else{
            return Optional.empty();
        }
    }



    public Optional<List<Task>> getAllByUserIdOrStatus(long userId, String status){
        Optional<List<Long>> tasksId = taskRepository.findAllIdByUserId(userId);
        if(tasksId.isPresent()) {
            TaskStatus  taskStatus = TaskStatus.valueOf(status);
            return taskRepository.findAllByListIdOrStatus(tasksId.get(), taskStatus);
        }else{
            return Optional.empty();
        }
    }



    public Optional<List<Task>> getAllByUserIdOrStatusAndCategory(long userId, String status, String category){
        Optional<List<Long>> tasksId = taskRepository.findAllIdByUserId(userId);
        if(tasksId.isPresent()) {
            TaskStatus  taskStatus = TaskStatus.valueOf(status);
            CategoryOfTask taskCategory = CategoryOfTask.valueOf(category);
            return taskRepository.findAllByListIdOrStatusCategory(tasksId.get(), taskStatus, taskCategory);
        }else{
            return Optional.empty();
        }
    }



    public Optional<List<Task>> getAllByExecutorIdAndStatus(long executorId, String status){
        Optional<List<Long>> tasksId = taskRepository.findAllIdByUserId(executorId);
        if(tasksId.isPresent()) {
            TaskStatus  taskStatus = TaskStatus.valueOf(status);
            if(taskStatus.name().equals("ACTIVE")){
                return taskRepository.findAllByStatus(TaskStatus.ACTIVE);
            }else{
                return taskRepository.findAllByListIdAndStatus(tasksId.get(), taskStatus);
            }
        }else{
            return Optional.empty();
        }
    }



    public boolean closeTask(long taskId, User user){
        Optional<Task> taskOpt = taskRepository.findById(taskId);
        if(taskOpt.isPresent()){
            Task task = taskOpt.get();
            if(task.getStatus().equals(TaskStatus.DONE) && user.getRoles().contains(Role.ROLE_USER) ){
                task.setStatus(TaskStatus.COMPLETED);
                taskRepository.save(task);
                return true;
            }
        }
        return false;
    }



    public Optional<Task> getTaskById(long taskId){
       return taskRepository.findTaskById(taskId);
    }


}
