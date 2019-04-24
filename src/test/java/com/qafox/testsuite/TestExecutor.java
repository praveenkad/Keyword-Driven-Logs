package com.qafox.testsuite;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.testng.annotations.Test;
import com.qafox.actions.UserActions;

public class TestExecutor extends UserActions {

	public XSSFSheet sheet;

	@Test
	public void test() {
    log.info("&&&&&&&&&&&& TESTNG TEST &&&&&&&&&&&&&");
		try {
			sheet = getSheet(EXCEL_FILE_PATH, SHEET_NAME);
			for (Row row : sheet) {
				try {
					userAction = getCellValue(row.getRowNum(),USER_ACTION_CELL_NUM,sheet);
					testData = getCellValue(row.getRowNum(), TEST_DATA_CELL_NUM,sheet);
				} catch (NullPointerException e) {
					testData = "temp";
				}
				try {
					webElementName = getCellValue(row.getRowNum(),WEBELEMENT_CELL_NUM,sheet);
				} catch (NullPointerException e) {
					webElementName = "temp";
				}
				performUserAction();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
