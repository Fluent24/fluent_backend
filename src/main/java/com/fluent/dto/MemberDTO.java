package com.fluent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    private String email;
    private String nickName;
    private String profilePictureUrl;
    private Long tier;
}
