package ru.praktikum.model.user;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserWithoutEmail {
    private String password;
    private String name;
}
