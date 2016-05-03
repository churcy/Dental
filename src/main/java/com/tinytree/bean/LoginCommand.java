package com.tinytree.bean;

/**
 * 验证用户名和密码
 */
public class LoginCommand {

    private String username;//用户名

    private String password;//密码

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
