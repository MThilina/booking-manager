package com.thilina.booking_manager.repository;

import com.thilina.booking_manager.entity.Book;
import com.thilina.booking_manager.entity.BookRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRecordRepository extends JpaRepository<BookRecord,String> {
    Optional<BookRecord> findByBookIdAndBookBorrowDateIsNotNullAndBookReturnDateIsNull(Book book);
}
