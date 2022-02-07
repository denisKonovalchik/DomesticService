package by.konovalchik.domesticservice.repository;

import by.konovalchik.domesticservice.entity.CategoryOfTask;
import by.konovalchik.domesticservice.entity.Task;
import by.konovalchik.domesticservice.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface TaskRepository extends JpaRepository<Task, Long> {



    @Query(value = " SELECT tu.task_id FROM users_tasks tu WHERE tu.user_id = ?1 ", nativeQuery=true)
    Optional<List<Long>> findAllIdByUserId(long userId);


    @Query(value=" FROM Task t WHERE t.status = ?1 ")
    Optional<List<Task>> findAllByStatus(TaskStatus status);


    @Query(value="FROM Task t WHERE t.id IN ?1 ")
    Optional<List<Task>> findAllByListId(List<Long> tasksId);


    @Query(value="FROM Task t WHERE t.id IN ?1 AND t.status = ?2 ")
    Optional<List<Task>> findAllByListIdAndStatus(List<Long> tasksId, TaskStatus status);


    @Query(value="FROM Task t WHERE t.id IN ?1 AND t.category = ?2 ")
    Optional<List<Task>> findAllByListIdAndCategory(List<Long> tasksId, CategoryOfTask category);


    @Query(value="FROM Task t WHERE t.id IN ?1 OR t.status = ?2 ")
    Optional<List<Task>> findAllByListIdOrStatus(List<Long> tasksId, TaskStatus status );


    @Query(value="FROM Task t WHERE (t.id IN ?1 OR t.status = ?2 ) AND t.category = ?3 ")
    Optional<List<Task>> findAllByListIdOrStatusCategory(List<Long> tasksId, TaskStatus status, CategoryOfTask category );


    Optional<Task> findTaskById(long taskId);


}
