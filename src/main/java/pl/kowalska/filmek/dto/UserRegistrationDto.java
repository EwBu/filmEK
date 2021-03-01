package pl.kowalska.filmek.dto;

public class UserRegistrationDto {
     private String userName;
     private String email;
     private String password;
     private char gender;

    public UserRegistrationDto() {
    }

    public UserRegistrationDto(String userName, String email, String password, char gender) {
        super();
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.gender = gender;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }
}
