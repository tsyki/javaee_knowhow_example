package tsyki.javaee.example.primefaces;

import static tsyki.javaee.example.primefaces.SelenideWebDriver.*;

import org.openqa.selenium.support.FindBy;

import com.codeborne.selenide.SelenideElement;

/**
 * PrimeFacesの部品サンプル画面のページオブジェクト
 * @author TOSHIYUKI.IMAIZUMI
 * @since 2016/12/24
 */
public class PrimeFacesWidgetViewPage {
	@FindBy(id="widgetSampleForm:date")
	public SelenideElement date;

	@FindBy(id="widgetSampleForm:combo1")
	public SelenideElement combo1;

	@FindBy(id="widgetSampleForm:combo2")
	public SelenideElement combo2;

	@FindBy(css="button .ui-icon-extlink")
	public SelenideElement openDialogButton;

	@FindBy(id="widgetSampleForm:dialogResult")
	public SelenideElement dialogResult;

	@FindBy(id="widgetSampleForm:cellEditableTable")
	public SelenideElement cellEditableTable;

	@FindBy(id="widgetSampleForm:result")
	public SelenideElement result;

	@FindBy(css="button .fa-edit")
	public SelenideElement submitButton;

	private static final String ID_COL_HEADER_TEXT = "Id";
	private static final String TEXT_VALUE_COL_HEADER_TEXT = "textValue";
	private static final String DATE_COL_HEADER_TEXT = "date";
	private static final String COMBO_COL_HEADER_TEXT = "combo";

	public void setDateValue(String value){
		SelenideWebDriver.setDateValue(date, value);
	}

	public void setCombo1Value(String value){
		setComboValue(combo1, value);
	}

	public void setCombo2Value(String value){
		setComboValue(combo2, value);
	}

	public void setEditableTableTextValue(int rowIndex,String value){
		setTableTextValue(cellEditableTable, rowIndex, TEXT_VALUE_COL_HEADER_TEXT, value);
	}

	public void setEditableTableDateValue(int rowIndex,String value){
		setTableDateValue(cellEditableTable, rowIndex, DATE_COL_HEADER_TEXT, value);
	}

	public void setEditableTableComboValue(int rowIndex,String value){
		setTableComboValue(cellEditableTable, rowIndex, COMBO_COL_HEADER_TEXT, value);
	}

	public SelenideElement getEditableTableIdElement(int rowIndex){
		return getTableOutputElement(cellEditableTable, rowIndex, ID_COL_HEADER_TEXT);
	}

	public SelenideElement getEditableTableTextElement(int rowIndex){
		return getTableOutputElement(cellEditableTable, rowIndex, TEXT_VALUE_COL_HEADER_TEXT);
	}

	public SelenideElement getEditableTableDateElement(int rowIndex){
		return getTableOutputElement(cellEditableTable, rowIndex, DATE_COL_HEADER_TEXT);
	}

	public SelenideElement getEditableTableComboElement(int rowIndex){
		return getTableOutputElement(cellEditableTable, rowIndex, COMBO_COL_HEADER_TEXT);
	}
}
