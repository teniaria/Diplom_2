package ru.praktikum.generator;

import org.apache.commons.lang3.RandomStringUtils;
import ru.praktikum.model.user.User;
import ru.praktikum.model.user.UserWithoutEmail;
import ru.praktikum.model.user.UserWithoutName;
import ru.praktikum.model.user.UserWithoutPassword;
import ru.praktikum.model.user.auth.UserForAuth;
import ru.praktikum.model.user.auth.UserForAuthWithoutEmail;
import ru.praktikum.model.user.auth.UserForAuthWithoutPassword;

public class UserGenerator {
    private final String email = RandomStringUtils.randomAlphanumeric(10) + "@yandex.ru";
    private final String emailNew = RandomStringUtils.randomAlphanumeric(10) + "@qq.qq";
    private final String password = RandomStringUtils.randomAlphanumeric(10);
    private final String passwordNew = RandomStringUtils.randomAlphanumeric(10);
    private final String name = RandomStringUtils.randomAlphanumeric(10);

    public User createUserFull() {
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .build();
    }

    public User createUserWithEmailNull() {
        return User.builder()
                .password(password)
                .name(name)
                .build();
    }

    public User createUserWithPasswordNull() {
        return User.builder()
                .email(email)
                .name(name)
                .build();
    }

    public User createUserWithNameNull() {
        return User.builder()
                .email(email)
                .password(password)
                .build();
    }

    public UserWithoutEmail createUserWithoutEmail() {
        return UserWithoutEmail.builder()
                .password(password)
                .name(name)
                .build();
    }

    public UserWithoutPassword createUserWithoutPassword() {
        return UserWithoutPassword.builder()
                .email(email)
                .name(name)
                .build();
    }

    public UserWithoutName createUserWithoutName() {
        return UserWithoutName.builder()
                .email(email)
                .password(password)
                .build();
    }
    public UserForAuth createUserForAuth(User user) {
        return UserForAuth.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    public UserForAuth createUserForAuthWithEmailNull(User user) {
        return UserForAuth.builder()
                .password(user.getPassword())
                .build();
    }

    public UserForAuth createUserForAuthWithPasswordNull(User user) {
        return UserForAuth.builder()
                .email(user.getEmail())
                .build();
    }

    public UserForAuthWithoutEmail createUserForAuthWithoutEmail(User user) {
        return new UserForAuthWithoutEmail(user);
    }

    public UserForAuthWithoutPassword createUserForAuthWithoutPassword(User user) {
        return new UserForAuthWithoutPassword(user);
    }

    public UserForAuth createUserForAuthWithIncorrectEmail(User user) {
        return UserForAuth.builder()
                .email(emailNew)
                .password(user.getPassword())
                .build();
    }

    public UserForAuth createUserForAuthWithIncorrectPassword(User user) {
        return UserForAuth.builder()
                .email(user.getEmail())
                .password(passwordNew)
                .build();
    }
}
