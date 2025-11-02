package com.gooderreads.gooder_reads.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gooderreads.gooder_reads.entity.Book;

@Repository
public interface BookRepository extends JpaRepository <Book, Long>{

    Optional<Book> findById(Long id);
}
