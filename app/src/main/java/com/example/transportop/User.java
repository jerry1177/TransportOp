package com.example.transportop;

public class User {
    private String m_UserName;
    private String m_FName;
    private String m_LName;
    private String m_Email;
    private String m_Password;

    User() {
        this.m_UserName = "";
        this.m_FName = "";
        this.m_LName = "";
        this.m_Email = "";
        this.m_Password = "";
    }

    User(String username, String fName, String lName, String email, String password) {
        this.m_UserName = username;
        this.m_FName = fName;
        this.m_LName = lName;
        this.m_Email = email;
        this.m_Password = password;
    }

    public void SetUserName(String username) { this.m_UserName = username; }
    public void SetFirstName(String fName) { this.m_FName = fName; }
    public void SetLastName(String lName) { this.m_LName = lName; }
    public void SetEmail(String email) { this.m_Email = email; }
    public void SetPassword(String password) { this.m_Password = password; }

    public String GetUserName() { return this.m_UserName; }
    public String GetFirstName() { return this.m_FName; }
    public String GetLastName() { return this.m_LName; }
    public String GetEmail() { return this.m_Email; }
    public String GetPassword() { return this.m_Password; }

}
