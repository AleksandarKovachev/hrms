package com.hrms.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hrms.entity.Vacation;

@Repository
public interface VacationRepository extends JpaRepository<Vacation, Long> {

	@Query("SELECT v FROM Vacation v JOIN User u ON v.user = u JOIN Department d ON u.department = d WHERE u.id != :userId AND d.id = :departmentId AND v.toDate > :date AND v.isApproved = 1")
	List<Vacation> getVacationsByDepartment(@Param("userId") Long userId, @Param("departmentId") Integer departmentId,
			@Param("date") Date date);

	@Query("SELECT v FROM Vacation v JOIN User u on v.user = u WHERE u.id = :userId")
	List<Vacation> getMyVacations(@Param("userId") Long userId);

	@Query("SELECT v FROM Vacation v JOIN User u on v.user = u WHERE u.directManager.username = :manager AND v.isApproved = 0")
	List<Vacation> getVacationsThatNeedsApproval(@Param("manager") String manager);

}
