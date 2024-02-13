package ru.praktikum.model.user;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserWithoutName {
    private String email;
    private String password;
}
