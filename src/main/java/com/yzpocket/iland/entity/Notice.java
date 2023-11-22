package com.yzpocket.iland.entity;

import com.yzpocket.iland.dto.NoticeUpdateRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "notices")
public class Notice extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;

    @Column
    private String noticeTitle;

    @Column
    private String noticeWriter;

    @Column
    private String noticeContents;

    @Column
    @Enumerated(value = EnumType.STRING)
    private NoticeTypeEnum noticeType;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "notice")
    List<File> fileList = new ArrayList<>();

    public Notice(String title, NoticeTypeEnum type, String writer, String contents, Board board) {
        this.noticeTitle = title;
        this.noticeType = type;
        this.noticeWriter = writer;
        this.noticeContents = contents;
        this.board = board;
    }

    public void update(NoticeUpdateRequestDto requestDto) {
        this.noticeTitle = requestDto.getNoticeTitle();
        this.noticeType = requestDto.getNoticeType();
        this.noticeWriter = requestDto.getNoticeWriter();
        this.noticeContents = requestDto.getNoticeContents();
    }
}
