package tsyki.javaee.example.primefaces;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static tsyki.javaee.example.primefaces.SelenideWebDriver.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;

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
		final String tableId = "widgetSampleForm:cellEditableTable";
		setTableTextValue(tableId, 0, "textValue", "hoge");
		setTableDateValue(tableId, 0, "date", "2016/1/3");
		setTableComboValue(tableId, 0, "combo", "value1-2");

		//アサート
		shouldTableText(tableId, 0, "Id", "1");
		shouldTableText(tableId, 0, "textValue", "hoge");
		shouldTableText(tableId, 0, "date", "2016/01/03");
		// XXX 本当はvalueと表示されている値は違うだろう
		shouldTableText(tableId, 0, "combo", "value1-2");
	}


}
