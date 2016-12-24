package tsyki.javaee.example.primefaces;

import static com.codeborne.selenide.Selenide.*;
import static tsyki.javaee.example.primefaces.SelenideWebDriver.*;

import org.openqa.selenium.support.FindBy;

import com.codeborne.selenide.SelenideElement;

/**
 * PrimeFacesのサンプルダイアログのページオブジェクト
 * @author TOSHIYUKI.IMAIZUMI
 * @since 2016/12/24
 */
public class SampleDialogPage {

	@FindBy(css="iframe[src*=\"sampleDialog\"]")
	public SelenideElement dialogFrame;

	@FindBy(id="dialogSampleForm:date")
	public SelenideElement date;

	@FindBy(id="dialogSampleForm:combo1")
	public SelenideElement combo1;

	@FindBy(id="dialogSampleForm:combo2")
	public SelenideElement combo2;

	@FindBy(id="dialogSampleForm:result")
	public SelenideElement result;

	@FindBy(css="button .fa-edit")
	public SelenideElement submitButton;

	@FindBy(css="button .fa-close")
	public SelenideElement closeButton;


	public void setDateValue(String value){
		switchToDialogFrame();
		SelenideWebDriver.setDateValue(date, value);
		switchTo().defaultContent();
	}

	public void setCombo1Value(String value){
		switchToDialogFrame();
		setComboValue(combo1, value);
		switchTo().defaultContent();
	}

	public void setCombo2Value(String value){
		switchToDialogFrame();
		setComboValue(combo2, value);
		switchTo().defaultContent();
	}

	public void close(){
		switchToDialogFrame();
		closeButton.click();
		switchTo().defaultContent();
	}

	public void switchToDialogFrame() {
		switchTo().frame(dialogFrame);
	}
}
