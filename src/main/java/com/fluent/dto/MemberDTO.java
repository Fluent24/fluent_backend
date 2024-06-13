package com.fluent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    private String email;
    private String nickName;
    private String profilePictureUrl;
    private Long tier;
    private Long exp;
    private List<String> favorites;
}
