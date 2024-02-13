package ru.praktikum.model.user.auth;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class UserForAuth {
    private String email;
    private String password;
}