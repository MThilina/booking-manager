package com.thilina.booking_manager.repository;

import com.thilina.booking_manager.entity.BookRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRecordRepository extends JpaRepository<BookRecord,String> {
}
