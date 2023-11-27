package com.yzpocket.iland.repository;

import com.yzpocket.iland.entity.Notice;
import com.yzpocket.iland.entity.NoticeTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Page<Notice> findByNoticeTypeAndBoardNotNull(NoticeTypeEnum noticeTypeEnum, Pageable pageable);

    Page<Notice> findByNoticeTypeAndBoardNotNullOrderByNoticeIdDesc(NoticeTypeEnum noticeTypeEnum, Pageable unpaged);
}
