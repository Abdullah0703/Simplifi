package com.simplifi.simplifi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.simplifi.simplifi.models.School;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {

}
