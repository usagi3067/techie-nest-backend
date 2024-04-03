package com.dango.auth.ucenter.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class RegisterDto {
    private String confirmpwd;
    private String password;
    private String username;
}