package tsyki.javaee.example.simplebean;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * シンプルなバッキングビーンのサンプル
 * @author TOSHIYUKI.IMAIZUMI
 * @since 2016/10/24
 */
@Named
@RequestScoped
public class LoginView {
    @Size( min = 4, max = 8)
    private String userCode;

    @NotEmpty
    private String password;

    private String result;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode( String userCode) {
        this.userCode = userCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword( String password) {
        this.password = password;
    }

    public String getResult() {
        return result;
    }

    public String login() {
        if ( userCode.equals( "admin") && password.equals( "admin")) {
            System.out.println( "login success");
            result = "success";
        }
        else {
            System.out.println( "login failed");
            result = "fail";
        }
        return null;
    }

}
