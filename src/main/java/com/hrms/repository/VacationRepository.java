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

	@Query("SELECT v FROM Vacation v JOIN User u ON v.user = u JOIN Department d ON u.department = d WHERE d.id = :departmentId AND v.toDate > :date")
	List<Vacation> getVacationsByDepartment(@Param("departmentId") Integer departmentId, @Param("date") Date date);

}
