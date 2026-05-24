package org.example.javawebservice_session08_lession02.repository;


import org.example.javawebservice_session08_lession02.entity.ErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ErrorLogRepository extends JpaRepository<ErrorLog, Long> {
}
