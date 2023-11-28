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
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column
    private String fileName;

    @Column
    private Long fileSize;

    @Column
    private String fileUrl;

    @Column
    @Enumerated(value = EnumType.STRING)
    private FileTypeEnum fileType;

    @ManyToOne
    @JoinColumn(name = "Notice_id")
    private Notice notice;

    @ManyToOne
    @JoinColumn(name = "Info_id")
    private Info info;

    @ManyToOne
    @JoinColumn(name = "Video_id")
    private Video video;

    @ManyToOne
    @JoinColumn(name = "Game_id")
    private Game game;

    public File(String fileName, Long fileSize, FileTypeEnum fileType, String fileUrl) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.fileUrl = fileUrl;
    }

    public void setNotice(Notice notice) {
        this.notice = notice;
    }
}
