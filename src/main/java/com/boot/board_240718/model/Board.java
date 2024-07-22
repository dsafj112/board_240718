package com.boot.board_240718.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
//    @Size(min=2, max=30)
    @Size(min=2, max=30, message =  "제목은 2자이상 30자 이하입니다.")
    private String title;
    private String content;
}
