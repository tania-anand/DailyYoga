package gym.minorproject.com.gym.bean;

import java.io.Serializable;

/**
 * Created by tania on 27/7/17.
 */

public class SignUpBean implements Serializable {
    private String Name;
    private String EmailId;
    private String Password;

    public SignUpBean() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }


    @Override
    public String toString() {
        return "RegisterBean{" +
                "Name='" + Name + '\'' +
                ", EmailId='" + EmailId + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }
}
