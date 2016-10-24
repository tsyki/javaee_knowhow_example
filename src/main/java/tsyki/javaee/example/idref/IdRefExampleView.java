package tsyki.javaee.example.idref;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * id指定のサンプル。以下でアクセスする<BR>
 * http://localhost:8080/javaee_knowhow_example/faces/idref/idRefExampleView.xhtml
 * @author TOSHIYUKI.IMAIZUMI
 * @since 2016/10/24
 */
@Named
@RequestScoped
public class IdRefExampleView {
    @NotEmpty
    private String input1;

    @NotEmpty
    private String input2;

    @NotEmpty
    private String input3;

    @NotEmpty
    private String input4;

    @NotEmpty
    private String input5;

    @NotEmpty
    private String input6;

    public String getInput1() {
        return input1;
    }

    public void setInput1( String input1) {
        this.input1 = input1;
    }

    public String getInput2() {
        return input2;
    }

    public void setInput2( String input2) {
        this.input2 = input2;
    }

    public String getInput3() {
        return input3;
    }

    public void setInput3( String input3) {
        this.input3 = input3;
    }

    public String getInput4() {
        return input4;
    }

    public void setInput4( String input4) {
        this.input4 = input4;
    }

    public String getInput5() {
        return input5;
    }

    public void setInput5( String input5) {
        this.input5 = input5;
    }

    public String getInput6() {
        return input6;
    }

    public void setInput6( String input6) {
        this.input6 = input6;
    }

    public void onProcess() {
        // バリデートするのが目的なのでなにもすることはない
        System.out.println( "onProcess");
    }

}
