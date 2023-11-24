package com.yzpocket.iland.entity;

import com.yzpocket.iland.dto.InfoUpdateRequestDto;
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
@Table(name = "infos")
public class Info extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long infoId;

    @Column
    private String infoTitle;

    @Column
    private String infoWriter;

    @Column
    private String infoContents;

    @Column
    @Enumerated(value = EnumType.STRING)
    private InfoTypeEnum infoType;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "info")
    List<File> fileList = new ArrayList<>();

    public Info(String title, InfoTypeEnum type, String writer, String contents, Board board) {
        this.infoTitle = title;
        this.infoType = type;
        this.infoWriter = writer;
        this.infoContents = contents;
        this.board = board;
    }

    public void update(InfoUpdateRequestDto requestDto) {
        this.infoTitle = requestDto.getInfoTitle();
        this.infoType = requestDto.getInfoType();
        this.infoWriter = requestDto.getInfoWriter();
        this.infoContents = requestDto.getInfoContents();
    }
}
