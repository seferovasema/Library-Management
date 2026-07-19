package com.sema.librarymanagment.repository;

import com.sema.librarymanagment.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthorId(Long authorId);

    List<Book> findByMemberId(Long memberId);
}
