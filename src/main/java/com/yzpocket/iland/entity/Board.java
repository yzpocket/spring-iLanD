package com.yzpocket.iland.entity;

import com.yzpocket.iland.dto.BoardUpdateRequestDto;
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
@Table(name = "boards")
public class Board extends TimeStamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    @Enumerated(value = EnumType.STRING)
    private BoardTypeEnum type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "board")
    List<Notice> noticeList = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    List<Video> videoList = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    List<Info> infoList = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    List<Info> gameList = new ArrayList<>();

    public Board(String title, BoardTypeEnum type){
        this.title = title;
        this.type = type;
    }

    public void update(BoardUpdateRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.type = requestDto.getType();
    }
}
