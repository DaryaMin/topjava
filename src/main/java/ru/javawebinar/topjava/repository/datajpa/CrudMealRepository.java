package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Transactional
    @Modifying
    @Query("UPDATE Meal m SET m.description=:#{#meal.description}, m.calories=:#{#meal.calories}, m.dateTime=:#{#meal.dateTime} WHERE id=:#{#meal.id} AND m.user.id=:userId")
    int save(@Param("meal") Meal meal, @Param("userId") int userId);

    @Transactional
    @Query("SELECT m FROM Meal m WHERE m.user.id=:userId AND m.dateTime >= :startDateTime AND m.dateTime < :endDateTime ORDER BY m.dateTime DESC")
    List<Meal> getBetween(@Param("userId")int userId,
                   @Param("startDateTime") LocalDateTime startDateTime,
                   @Param("endDateTime") LocalDateTime endDateTime);


    List<Meal> getByUser_id(Integer userId);

    List<Meal> getByUserIdOrderByDateTimeDesc(Integer userId);

    //List<Meal> getByDate_timeAfter(LocalDateTime startTime);
}
