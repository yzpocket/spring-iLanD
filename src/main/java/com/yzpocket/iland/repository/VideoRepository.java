package com.yzpocket.iland.repository;

import com.yzpocket.iland.entity.Video;
import com.yzpocket.iland.entity.VideoTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    Page<Video> findByVideoTypeAndBoardNotNullOrderByVideoIdDesc(VideoTypeEnum videoTypeEnum, Pageable unpaged);
}
