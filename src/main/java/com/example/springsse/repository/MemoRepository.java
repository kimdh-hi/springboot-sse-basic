package com.example.springsse.repository;

import com.example.springsse.domain.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo, Long>, MemoDao {
}
