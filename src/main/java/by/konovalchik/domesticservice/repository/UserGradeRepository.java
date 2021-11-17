package by.konovalchik.domesticservice.repository;


import by.konovalchik.domesticservice.entity.UserGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserGradeRepository extends JpaRepository<UserGrade, Long> {


    @Query(value = " FROM UserGrade ug WHERE ug.user.id = ?1 AND ug.userRating.id = ?2 AND ug.task.id = ?3 ")
    Optional<UserGrade> findGradeByUserIdRatingIdTaskId(long idUser, long idRating, long taskId);


    @Query(value = " SELECT AVG(g.grade) FROM grades g  WHERE user_rating_id = ?1 ", nativeQuery = true)
    Optional<Double> getScore(long idRating);


    @Modifying
    @Query(value = " INSERT INTO grades  VALUES (null, 1?, 2? , 3?, 4?)  ", nativeQuery=true )
    void saveGrade(int grade, long taskId, long userId, long ratingId);


}
