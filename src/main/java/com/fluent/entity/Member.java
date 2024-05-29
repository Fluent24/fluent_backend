package com.fluent.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicUpdate
@Table
public class Member {
    @Id
    @Column
    private String email;

    @Column
    private String nickName;

    @Column
    private String profilePictureUrl;

}