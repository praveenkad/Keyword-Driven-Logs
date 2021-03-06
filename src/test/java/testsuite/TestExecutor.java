package testsuite;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import actions.UserActions;

public class TestExecutor extends UserActions {

	public XSSFSheet sheet;
    
	@BeforeSuite
	public void loadProperty(){
		loadLogs();
	}
	@Test
	public void test() {
		try {
			sheet = getSheet(EXCEL_FILE_PATH, "Reddit Cases");
			for (Row row : sheet) {
				try {
					currentRow = row;
					userAction = getCellValue(row.getRowNum(), 4, sheet);
					testData = getCellValue(row.getRowNum(), 5, sheet);
				} catch (NullPointerException e) {
					testData = "temp";
				}
				try {
					webElementName = getCellValue(row.getRowNum(), 3, sheet);
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
