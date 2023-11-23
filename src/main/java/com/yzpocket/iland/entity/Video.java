package com.yzpocket.iland.entity;

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
@Table(name = "videos")
public class Video extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long videoId;

    @Column
    private String videoTitle;

    @Column
    private String videoWriter;

    @Column
    private String videoContents;

    @Column
    @Enumerated(value = EnumType.STRING)
    private VideoTypeEnum videoType;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "video")
    List<File> fileList = new ArrayList<>();

    public Video(String title, VideoTypeEnum type, String writer, String contents, Board board) {
        this.videoTitle = title;
        this.videoType = type;
        this.videoWriter = writer;
        this.videoContents = contents;
        this.board = board;
    }
}
