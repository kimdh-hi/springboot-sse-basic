package com.example.springsse.repository;

import com.example.springsse.domain.Memo;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class MemoDaoImpl implements MemoDao{

    private final EntityManager em;

    @Override
    public Memo getMemoDetail(Long id) {
        Memo memo = em.createQuery(
                        "select distinct m from Memo m " +
                                "left join fetch m.user u " +
                                "left join fetch m.comments c " +
                                "where m.id = :id", Memo.class
                )
                .setParameter("id", id)
                .getSingleResult();

        return memo;
    }
}
