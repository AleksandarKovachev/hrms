package com.hrms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hrms.entity.Vacation;

@Repository
public interface VacationRepository extends JpaRepository<Vacation, Long> {

}
