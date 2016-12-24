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
	public static void setDateValue(String dateId, String date){
		// NOTE 日付部品はidに_inputが付いたものが実際のinput要素
		$(By.id(dateId + "_input")).setValue(date);
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
}
