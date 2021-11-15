package by.konovalchik.domesticservice.repository;

import by.konovalchik.domesticservice.entity.TelephoneTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TelephoneTaskRepository extends JpaRepository<TelephoneTask, Long> {


}
