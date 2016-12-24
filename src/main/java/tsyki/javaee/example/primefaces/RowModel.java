package tsyki.javaee.example.primefaces;

import java.util.Date;

/**
 * 行編集用のモデル
 * @author TOSHIYUKI.IMAIZUMI
 *
 */
public class RowModel{
	private Long id;
	private String textValue;
	private Date date;
	private String comboValue;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTextValue() {
		return textValue;
	}
	public void setTextValue(String textValue) {
		this.textValue = textValue;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getComboValue() {
		return comboValue;
	}
	public void setComboValue(String comboValue) {
		this.comboValue = comboValue;
	}


}