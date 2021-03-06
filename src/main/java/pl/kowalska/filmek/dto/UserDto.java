package pl.kowalska.filmek.dto;

public class UserDto {
     private String userName;
     private String email;
     private String password;
     private char gender;
     private boolean comfirmed;

    public UserDto() {
    }

    public UserDto(String userName, String email, String password, char gender, boolean comfirmed) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.comfirmed = comfirmed;
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

    public boolean isComfirmed() {
        return comfirmed;
    }

    public void setComfirmed(boolean comfirmed) {
        this.comfirmed = comfirmed;
    }
}
