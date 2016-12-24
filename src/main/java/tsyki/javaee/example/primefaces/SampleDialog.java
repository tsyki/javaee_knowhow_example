package tsyki.javaee.example.primefaces;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.primefaces.context.RequestContext;

/**
 * PrimeFacesのダイアログのサンプル
 * @author TOSHIYUKI.IMAIZUMI
 * @since 2016/12/24
 */
@Named
@RequestScoped
public class SampleDialog {

	@NotNull
	private Date date;

	@NotNull
	private String comboValue1;

	@NotNull
	private String comboValue2;

	private String result;

	public void submit(){
		DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		String dateString =  format.format(date);
		result = dateString + " " + comboValue1 + " " + comboValue2;
	}

	public void closeDialog(){
		submit();
		RequestContext.getCurrentInstance().closeDialog(result);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getComboValue1() {
		return comboValue1;
	}

	public void setComboValue1(String comboValue1) {
		this.comboValue1 = comboValue1;
	}

	public String getComboValue2() {
		return comboValue2;
	}

	public void setComboValue2(String comboValue2) {
		this.comboValue2 = comboValue2;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
