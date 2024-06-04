package com.fluent.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicUpdate
@DynamicInsert
@Table
public class Member {
    @Id
    @Column
    private String email;

    @Column
    private String nickName;

    @Column
    private String profilePictureUrl;

    @Column
    private Long tier;

}