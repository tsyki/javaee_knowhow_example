package tsyki.javaee.example.primefaces;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.codeborne.selenide.SelenideElement;


/**
 * `PrimeFacesの部品を使った画面のブラウザテストサンプル
 * @author TOSHIYUKI.IMAIZUMI
 * @since 2016/12/23
 */
public class PrimeFacesWidgetViewBrowserTest {

	@Before
	public void before(){
		//NOTE -Dselenide.baseUrl=http://localhost:8080/javaee_knowhow_example/faces/ の想定
		open("primefaces/primeFacesWidgetView.xhtml");
	}

	@Test
	public void 日付とコンボの入力(){
		setDateValue("widgetSampleForm:date", "2016/01/01");
		setComboValue("widgetSampleForm:combo1", "value1-2");
		setComboValue("widgetSampleForm:combo2", "value2-2");
		clickButtonByIcon("fa-edit");
		// アサート
		$(By.id("widgetSampleForm:result")).shouldHave(text("2016/01/01 value1-2 value2-2"));
	}

	@Test
	public void ダイアログ入力(){
		setDateValue("widgetSampleForm:date", "2016/01/01");
		// ダイアログオープン
		clickButtonByIcon("ui-icon-extlink");
		// ダイアログのフレームを選択
		SelenideElement dialogFrame = $("iframe[src*=\"sampleDialog\"]");
		switchTo().frame(dialogFrame);
		setDateValue("dialogSampleForm:date", "2016/01/02");
		setComboValue("dialogSampleForm:combo1", "value1-2");
		setComboValue("dialogSampleForm:combo2", "value2-2");
		// ダイアログクローズ
		clickButtonByIcon("fa-close");
		// フレームを元に戻す
		switchTo().defaultContent();
		// アサート
		$(By.id("widgetSampleForm:dialogResult")).shouldHave(text("2016/01/02 value1-2 value2-2"));
	}

	@Test
	public void テーブルセル入力(){
		String headerText = "textValue";
		String value = "hoge";
		int rowIndex = 0;
		SelenideElement tableRoot = $(By.id("widgetSampleForm:cellEditableTable"));
		List<WebElement> headerElems = tableRoot.findElements(By.className("ui-column-title"));
		int targetColIndexForSelector = -1;
		for(int i=0;i<headerElems.size();i++){
			WebElement headerElem = headerElems.get(i);
			if(headerText.equals(headerElem.getText())){
				// nth-of-typeで使うので+1
				targetColIndexForSelector = i+1;
				break;
			}
		}
		// 目標の編集行
		SelenideElement rowElem = tableRoot.$("tr[data-ri=\""+ rowIndex  + "\"]");
		// ヘッダと同じカラムのセルをクリックしてセルエディタを起動
		SelenideElement targetCell = rowElem.$("td:nth-of-type(" + targetColIndexForSelector + ")");
		targetCell.click();
		// この後は入力部品ごとに異なる処理
		targetCell.$("input").setValue(value);
		// フォーカスを外してセルエディタを閉じる
		// 適当なところをクリックする
		headerElems.iterator().next().click();
		// targetCell.sendKeys("\t");
		// アサート
		targetCell.$(".ui-cell-editor-output").shouldHave(text(value));
	}

	private void clickButtonByIcon(String icon){
		$("button ." + icon).click();
	}

	private void setDateValue(String dateId, String date){
		// NOTE 日付部品はidに_inputが付いたものが実際のinput要素
		$(By.id(dateId + "_input")).setValue(date);
		// DatePickerを消しておく
		$(By.className("ui-datepicker-current-day")).click();
	}

	private void setComboValue(String comboId,String value){
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
}
