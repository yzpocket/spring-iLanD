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
    private String videoBody;

    @Column
    @Enumerated(value = EnumType.STRING)
    private VideoTypeEnum videoType;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
}
