package com.example.springsse.repository;

import com.example.springsse.domain.Memo;

import java.util.List;

public interface MemoDao {

    Memo getMemoDetail(Long id);
}
