package tsyki.javaee.example.primefaces;

import static com.codeborne.selenide.Condition.*;
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
	public static void setDateValue(String dateId, String date){
		// NOTE 日付部品は中にidに_inputが付いたinput要素がある
		$(By.id(dateId)).$("input").setValue(date);
		// DatePickerを消しておく
		$(By.className("ui-datepicker-current-day")).click();
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
		SelenideElement targetCell = getTableElement(tableId, rowIndex, headerText);
		// フォーカスを当ててセルエディタを表示させる
		targetCell.click();
		targetCell.$("input").setValue(value);
		// フォーカスを外してセルエディタを閉じる
		targetCell.closest("form").click();
	}

	public static void setTableDateValue(String tableId,int rowIndex,String headerText,String value){
		SelenideElement targetCell = getTableElement(tableId, rowIndex, headerText);
		// フォーカスを当ててセルエディタを表示させる
		targetCell.click();
		SelenideElement calendarElem = targetCell.$(".ui-calendar");
		setDateValue(calendarElem.getAttribute("id"), value);
		// フォーカスを外してセルエディタを閉じる
		targetCell.closest("form").click();
	}

	public static void setTableComboValue(String tableId,int rowIndex,String headerText,String value){
		SelenideElement targetCell  =getTableElement(tableId, rowIndex, headerText);
		// フォーカスを当ててセルエディタを表示させる
		targetCell.click();
		// コンボのルートとなる要素を取得
		SelenideElement comboElem = targetCell.$(".ui-selectonemenu");
		// 後は通常のコンボ入力と同じ
		setComboValue(comboElem.getAttribute("id"), value);
		// フォーカスを外してセルエディタを閉じる
		targetCell.closest("form").click();
	}

	public static void shouldTableText(String tableId,int rowIndex,String headerText,String value){
		SelenideElement targetCell = getTableElement(tableId, rowIndex, headerText);
		// セルエディタを使っていない場合はtargetCellのinnerTextが表示されている値
		SelenideElement cellEditorOutputCell =targetCell.$(".ui-cell-editor-output");
		if(cellEditorOutputCell.exists()){
			cellEditorOutputCell.shouldHave(text(value));
		}
		// セルエディタなしならtargetCellのinnerTextが表示されている値
		else{
			targetCell.shouldHave(text(value));
		}
	}

	public static SelenideElement getTableElement(String tableId,int rowIndex,String headerText){
		SelenideElement tableRoot = $(By.id(tableId));
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
