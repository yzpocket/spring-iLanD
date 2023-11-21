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

    //@OneToMany(mappedBy = "notice")
    //List<Notice> noticeList = new ArrayList<>();
}
