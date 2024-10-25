package com.alumniportal.unmsm.repository;

import com.alumniportal.unmsm.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEnrollmentRepository extends JpaRepository<Enrollment, Long> {

    @Query("select e from Enrollment e where e.user.id = ?1")
    List<Enrollment> findEnrollmentsByUserId(Long userId);

    @Query("select e from Enrollment e where e.activity.id = ?1")
    List<Enrollment> findEnrollmentsByActivityId(Long activityId);

    @Query("select (count(e) > 0) from Enrollment e where e.user.id = ?1 and e.activity.id = ?2")
    boolean existsEnrollmentByUserIdAndActivityId(Long userId, Long activityId);

//    TODO: Implementar este método en el servicio y endpoint

    @Query("select e from Enrollment e where e.user.id = ?1 and e.activity.id = ?2")
    Enrollment findEnrollmentByUserIdAndActivityId(Long userId, Long activityId);


}
