package tsyki.javaee.example.primefaces;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static tsyki.javaee.example.primefaces.SelenideWebDriver.*;

import org.junit.Before;
import org.junit.Test;

import com.codeborne.selenide.SelenideElement;

/**
 * `PrimeFacesの部品を使った画面のブラウザテストサンプル
 * @author TOSHIYUKI.IMAIZUMI
 * @since 2016/12/23
 */
public class PrimeFacesWidgetViewBrowserTest {

	private PrimeFacesWidgetViewPage widgetPage;

	@Before
	public void before(){
		//NOTE -Dselenide.baseUrl=http://localhost:8080/javaee_knowhow_example/faces/ の想定
		widgetPage =  open("primefaces/primeFacesWidgetView.xhtml", PrimeFacesWidgetViewPage.class);
	}

	@Test
	public void 日付とコンボの入力(){
		widgetPage.setDateValue("2016/01/01");
		widgetPage.setCombo1Value("value1-2");
		widgetPage.setCombo2Value("value2-2");
		widgetPage.submitButton.click();
		// アサート
		widgetPage.result.shouldHave(text("2016/01/01 value1-2 value2-2"));
	}

	@Test
	public void ダイアログ入力(){
		widgetPage.setDateValue("2016/01/01");
		// ダイアログオープン
		widgetPage.openDialogButton.click();
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
		widgetPage.dialogResult.shouldHave(text("2016/01/02 value1-2 value2-2"));
	}

	@Test
	public void テーブルセル入力(){
		widgetPage.setEditableTableTextValue(0, "hoge");
		widgetPage.setEditableTableDateValue(0, "2016/01/03");
		widgetPage.setEditableTableComboValue(0, "value1-2");

		//アサート
		widgetPage.getEditableTableIdElement(0).shouldHave(text("1"));
		widgetPage.getEditableTableTextElement(0).shouldHave(text("hoge"));
		widgetPage.getEditableTableDateElement(0).shouldHave(text("2016/01/03"));
		// XXX 本当はvalueと表示されている値は違うだろう
		widgetPage.getEditableTableComboElement(0).shouldHave(text("value1-2"));
	}


}
