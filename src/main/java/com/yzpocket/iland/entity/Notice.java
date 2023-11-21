package com.yzpocket.iland.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    //@ManyToOne
    //@JoinColumn(name = "board_id")
    //private Board board;

}
