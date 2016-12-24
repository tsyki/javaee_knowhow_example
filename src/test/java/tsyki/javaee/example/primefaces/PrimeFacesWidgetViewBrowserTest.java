package tsyki.javaee.example.primefaces;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.Assert.*;

import java.util.List;

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

	@Test
	public void 日付とコンボの入力(){
		//NOTE -Dselenide.baseUrl=http://localhost:8080/javaee_knowhow_example/faces/ の想定
		open("primefaces/primeFacesWidgetView.xhtml");
		setDateValue("widgetSampleForm:date", "2016/01/01");
		setComboValue("widgetSampleForm:combo1", "value1-2");
		setComboValue("widgetSampleForm:combo2", "value2-2");
		clickButtonByIcon("fa-edit");
		// アサート
		$(By.id("widgetSampleForm:result")).shouldHave(text("2016/01/01 value1-2 value2-2"));
	}

	@Test
	public void ダイアログ入力(){
		open("primefaces/primeFacesWidgetView.xhtml");
		setDateValue("widgetSampleForm:date", "2016/01/01");
		// ダイアログオープン
		clickButtonByIcon("ui-icon-extlink");
		// ダイアログのフレームを選択
		// TODO iframeのsrcを指定して特定したい
		SelenideElement dialogFrame = $("iframe");
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
