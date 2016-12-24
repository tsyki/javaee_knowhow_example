package tsyki.javaee.example.primefaces;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.Assert.*;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.codeborne.selenide.SelenideElement;

/**
 * SelenideでPrimeFacesの部品を操作するためのドライバ
 * @author TOSHIYUKI.IMAIZUMI
 * @since 2016/12/24
 *
 */
public class SelenideWebDriver {
	public static void setDateValue(SelenideElement dateElem, String date){
		setDateValue(dateElem.getAttribute("id"), date);
	}

	public static void setDateValue(String dateId, String date){
		// NOTE 日付部品は中にidに_inputが付いたinput要素がある
		$(By.id(dateId)).$("input").setValue(date);
		// DatePickerを消しておく
		$(By.className("ui-datepicker-current-day")).click();
	}

	public static void setComboValue(SelenideElement comboElem,String value){
		setComboValue(comboElem.getAttribute("id"), value);
	}

	public static void setComboValue(String comboId,String value){
		SelenideElement comboRoot = $(By.id(comboId));
		// まずドロップダウンリストを出す
		comboRoot.click();
		SelenideElement selectNode = comboRoot.$("select");
		List<WebElement> options = new Select(selectNode).getOptions();
		// NOTE PrimeFacesのComboはselectByValueで選択できない。コンボを押すと別の要素がポップアップしてくる
		// ポップアップしてくる方の要素はvalueを持っていないため、まずselect要素の中の何番目を選ぶべきかを取得する
		int selectIndex = -1;
		for (int i = 0; i < options.size(); i++) {
			WebElement option = options.get(i);
			if(value.equals(option.getAttribute("value"))){
				selectIndex = i + 1;
				break;
			}
		}
		if(selectIndex == -1){
			fail("missing value in " + comboId + " value=" + value);
			return;
		}
		// 出てきたドロップダウンリストのルート
		SelenideElement dropdownElem = $(By.id(comboId + "_items"));
		SelenideElement targetItemElem= dropdownElem.$("li:nth-of-type(" + selectIndex + ")");
		targetItemElem.click();
	}

	public static void clickButtonByIcon(String iconClassName){
		$("button ." + iconClassName).click();
	}

	/**
	 * セル編集テーブルにテキストを入力する
	 * @param tableId 操作するテーブル部品のID
	 * @param rowIndex 編集する行番号(0始まり)
	 * @param headerText 編集する列の列名
	 * @param value 設定する値
	 */
	public static void setTableTextValue(String tableId,int rowIndex,String headerText,String value){
		setTableTextValue($(By.id(tableId)), rowIndex, headerText, value);
	}

	/**
	 * セル編集テーブルにテキストを入力する
	 * @param tableElem 操作するテーブルのtable要素
	 * @param rowIndex 編集する行番号(0始まり)
	 * @param headerText 編集する列の列名
	 * @param value 設定する値
	 */
	public static void setTableTextValue(SelenideElement tableElem,int rowIndex,String headerText,String value){
		setTableValue(tableElem, rowIndex, headerText, (targetCell) ->{
			targetCell.$("input").setValue(value);
		});
	}

	public static void setTableDateValue(String tableId,int rowIndex,String headerText,String value){
		setTableDateValue($(By.id(tableId)), rowIndex, headerText, value);
	}

	public static void setTableDateValue(SelenideElement tableElem,int rowIndex,String headerText,String value){
		setTableValue(tableElem, rowIndex, headerText, (targetCell) ->{
			SelenideElement calendarElem = targetCell.$(".ui-calendar");
			setDateValue(calendarElem, value);
		});
	}

	public static void setTableComboValue(String tableId,int rowIndex,String headerText,String value){
		setTableComboValue($(By.id(tableId)), rowIndex, headerText, value);
	}

	public static void setTableComboValue(SelenideElement tableElem,int rowIndex,String headerText,String value){
		setTableValue(tableElem, rowIndex, headerText, (targetCell) ->{
			// コンボのルートとなる要素を取得
			SelenideElement comboElem = targetCell.$(".ui-selectonemenu");
			// 後は通常のコンボ入力と同じ
			setComboValue(comboElem, value);
		});
	}

	public static void setTableValue(SelenideElement tableElem,int rowIndex,String headerText,ValueSetter valueSetter){
		SelenideElement targetCell  =getTableElement(tableElem, rowIndex, headerText);
		// フォーカスを当ててセルエディタを表示させる
		targetCell.click();
		// 部品に値をセット
		valueSetter.setValue(targetCell);
		// フォーカスを外してセルエディタを閉じる
		targetCell.closest("form").click();

	}

	public static void setTableValue(String tableId,int rowIndex,String headerText,ValueSetter valueSetter){
		setTableValue($(By.id(tableId)), rowIndex, headerText, valueSetter);
	}

	@FunctionalInterface
	private interface ValueSetter{
		void setValue(SelenideElement targetCell);
	}

	public static SelenideElement getTableOutputElement(String tableId,int rowIndex,String headerText){
		return getTableOutputElement($(By.id(tableId)), rowIndex, headerText);
	}

	public static SelenideElement getTableOutputElement(SelenideElement tableElem,int rowIndex,String headerText){
		SelenideElement targetCell = getTableElement(tableElem, rowIndex, headerText);
		// セルエディタを使っていない場合はtargetCellのinnerTextが表示されている値
		SelenideElement cellEditorOutputCell =targetCell.$(".ui-cell-editor-output");
		if(cellEditorOutputCell.exists()){
			return cellEditorOutputCell;
		}
		// セルエディタなしならtargetCellのinnerTextが表示されている値
		else{
			return targetCell;
		}
	}

	public static SelenideElement getTableElement(String tableId,int rowIndex,String headerText){
		SelenideElement tableRoot = $(By.id(tableId));
		return getTableElement(tableRoot, rowIndex, headerText);
	}

	public static SelenideElement getTableElement(SelenideElement tableRoot,int rowIndex,String headerText){
		// カラムヘッダを取得
		List<WebElement> headerElems = tableRoot.findElements(By.className("ui-column-title"));
		int targetColIndexForSelector = -1;
		for(int i=0;i<headerElems.size();i++){
			WebElement headerElem = headerElems.get(i);
			if(headerText.equals(headerElem.getText())){
				// nth-of-typeで使うので+1しておく
				targetColIndexForSelector = i+1;
				break;
			}
		}
		// 目標の編集行
		SelenideElement rowElem = tableRoot.$("tr[data-ri=\""+ rowIndex  + "\"]");
		// 目標セル
		SelenideElement targetCell = rowElem.$("td:nth-of-type(" + targetColIndexForSelector + ")");
		return targetCell;
	}
}
