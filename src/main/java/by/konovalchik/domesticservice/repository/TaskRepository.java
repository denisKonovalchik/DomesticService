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
    List<Long> findAllIdByUserId(long userId);


    @Query(value=" FROM Task t WHERE t.status = ?1 ")
    Optional<List<Task>> findAllByStatus(TaskStatus status);


    Optional<List<Task>> findAllByCategory(CategoryOfTask category);

    Optional<List<Task>> findAllByExpress(boolean express);

    Optional<Task> findTaskById(long taskId);


}
