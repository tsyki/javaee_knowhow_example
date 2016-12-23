package tsyki.javaee.example.primefaces;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

import org.junit.Test;
import org.openqa.selenium.By;


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
		$(By.id("widgetSampleForm:date_input")).setValue("2016/01/01");
		// TODO コンボの選択
		$("button[type=submit]").click();
		// アサート
		$(By.id("widgetSampleForm:result")).shouldHave(text("2016/01/01 value1-1 value2-1"));
	}
}
