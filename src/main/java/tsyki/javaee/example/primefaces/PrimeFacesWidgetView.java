package tsyki.javaee.example.primefaces;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 * PrimeFacesの部品のサンプル
 * @author TOSHIYUKI.IMAIZUMI
 * @since 2016/12/23
 */
@Named
@RequestScoped
public class PrimeFacesWidgetView {

	@NotNull
	private Date date;

	@NotNull
	private String comboValue1;

	@NotNull
	private String comboValue2;

	private String dialogResult;

	private List<RowModel> cellEditableTableModel = new ArrayList<>();

	private String result;

	@PostConstruct
	public void init(){
		RowModel rowModel1 = new RowModel();
		rowModel1.setId(1L);
		cellEditableTableModel.add(rowModel1);

		RowModel rowModel2 = new RowModel();
		rowModel2.setId(2L);
		cellEditableTableModel.add(rowModel2);

	}

	public void submit(){
		DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		String dateString =  format.format(date);
		result = dateString + " " + comboValue1 + " " + comboValue2;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getComboValue1() {
		return comboValue1;
	}

	public void setComboValue1(String comboValue1) {
		this.comboValue1 = comboValue1;
	}

	public String getComboValue2() {
		return comboValue2;
	}

	public void setComboValue2(String comboValue2) {
		this.comboValue2 = comboValue2;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getDialogResult() {
		return dialogResult;
	}

	public void setDialogResult(String dialogResult) {
		this.dialogResult = dialogResult;
	}

	public List<RowModel> getCellEditableTableModel() {
		return cellEditableTableModel;
	}

	public void setCellEditableTableModel(List<RowModel> cellEditableTableModel) {
		this.cellEditableTableModel = cellEditableTableModel;
	}

	public void openDialog(){
		RequestContext.getCurrentInstance().openDialog("/primefaces/sampleDialog");
	}

	public void onCloseDialog(SelectEvent event){
		dialogResult = (String) event.getObject();
	}
}
