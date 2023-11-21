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
}
