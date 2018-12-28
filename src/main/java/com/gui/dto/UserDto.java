package com.gui.dto;

public final class UserDto {

    private final String login;
    private final String password;
    private final String email;

    public UserDto(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }

}
