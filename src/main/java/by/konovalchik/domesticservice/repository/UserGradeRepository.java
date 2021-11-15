package by.konovalchik.domesticservice.repository;


import by.konovalchik.domesticservice.entity.UserGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserGradeRepository extends JpaRepository<UserGrade, Long> {


    @Query(value = " FROM UserGrade ug WHERE ug.user.id = ?1 AND ug.userRating.id = ?2 ")
    Optional<UserGrade> findGradeByUserAndRating(long idUser, long idRating);


    @Query(value = " SELECT AVG(grande) FROM grandes g WHERE g.user_reting_id = ?1 ", nativeQuery = true)
    Optional<Double> getScore(long idRating);


    @Modifying
    @Query(value = " INSERT INTO grades  VALUES (null, 3?, 2? , 1?)  ", nativeQuery=true )
    void saveGrade(long idRating, long idUser, int grade);


}
