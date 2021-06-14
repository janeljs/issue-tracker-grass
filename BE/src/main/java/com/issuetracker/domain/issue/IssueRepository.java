package com.issuetracker.domain.issue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {

    List<Issue> findAllByIsOpenTrue();

    List<Issue> findAllByIsOpenFalse();

    long countAllByIsOpenTrue();

    long countAllByIsOpenFalse();

    @Modifying
    @Query("update Issue i set i.isOpen = :isOpen where i.id in (:idList)")
    void updateStatusBy(boolean isOpen, List<Long> idList);
}