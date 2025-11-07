package ro.ase.model;

import java.util.Objects;

/**
 * Holds simple username/password credentials.
 */
public class Credentials {
    private String userName;
    private String password;

    public Credentials() {
    }

    public Credentials(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if( this == obj )
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Credentials credentials = (Credentials) obj;
        return Objects.equals(userName, credentials.getUserName()) && Objects.equals(password, credentials.getPassword());
    }

    @Override
    public String toString() {
        return "Credentials{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
