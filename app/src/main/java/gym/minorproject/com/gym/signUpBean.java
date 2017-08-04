package gym.minorproject.com.gym;

import java.io.Serializable;

/**
 * Created by tania on 27/7/17.
 */

public class signUpBean implements Serializable
{
    String Name;
    String PhoneNo;
    String EmailId;
    String ULoginId;
    String Password;
    String CPassword;

    public signUpBean(String name, String phoneNo, String emailId, String ULoginId, String password,
                      String CPassword)
    {
        Name = name;
        PhoneNo = phoneNo;
        EmailId = emailId;
        this.ULoginId = ULoginId;
        Password = password;
        this.CPassword = CPassword;
    }

    public signUpBean()
    {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public String getULoginId() {
        return ULoginId;
    }

    public void setULoginId(String ULoginId) {
        this.ULoginId = ULoginId;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getCPassword() {
        return CPassword;
    }

    public void setCPassword(String CPassword) {
        this.CPassword = CPassword;
    }

    @Override
    public String toString() {
        return "RegisterBean{" +
                "Name='" + Name + '\'' +
                ", PhoneNo='" + PhoneNo + '\'' +
                ", EmailId='" + EmailId + '\'' +
                ", ULoginId='" + ULoginId + '\'' +
                ", Password='" + Password + '\'' +
                ", CPassword='" + CPassword + '\'' +
                '}';
    }
}
