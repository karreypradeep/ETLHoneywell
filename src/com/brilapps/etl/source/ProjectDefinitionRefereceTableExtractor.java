package com.brilapps.etl.source;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.brilapps.etl.ETLUtil;
import com.brilapps.etl.ProjectDefinitionReferenceTable;

public class ProjectDefinitionRefereceTableExtractor {
	static Logger logger = Logger.getLogger(ProjectDefinitionRefereceTableExtractor.class);


	/**
	 * Generate target Project Definition file from the source file.
	 *
	 * @param sourceProjectDefinitionFile
	 *            source file from which target will be generated.
	 * @param destinationProjectDefinitionFile
	 *            destination file to which target file will be generated.
	 * @throws Exception
	 */
	public void extractProjectDefinitionRefereceTable(final File sourceProjectDefinitionRefereceTable)
			throws Exception {
		logger.debug(" entering extractProjectDefinitionRefereceTable() ");
		try {
			// File input stream to read source project definition file.
			FileInputStream fileInputStream = new FileInputStream(sourceProjectDefinitionRefereceTable);
			// Create Workbook instance holding reference to excel file
			Workbook sourceProjectDefinitionRefereceTableWorkbook = null;
			if (sourceProjectDefinitionRefereceTable.getName().endsWith(".xls")) {
				sourceProjectDefinitionRefereceTableWorkbook = new HSSFWorkbook(fileInputStream);
			} else {
				sourceProjectDefinitionRefereceTableWorkbook = new XSSFWorkbook(fileInputStream);
			}
			// Create sheet in source project definition work book.
			Sheet sourceProjectDefinitionRefereceTableSheet = sourceProjectDefinitionRefereceTableWorkbook
					.getSheetAt(0);
			validateColumnHeaders(sourceProjectDefinitionRefereceTableSheet);

			int colNum = 0;
			Map<String, Integer> pdReferenceTableColumnIndexMap = new HashMap<String, Integer>();
			for (String header : getColumnHeaders(sourceProjectDefinitionRefereceTableSheet)) {
				pdReferenceTableColumnIndexMap.put(header, colNum);
				colNum++;
			}

			Iterator<Row> iterator = sourceProjectDefinitionRefereceTableSheet.iterator();
			// iterate header row first and process remaining rows.
			iterator.next();
			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				String projectPrefix = "";
				ProjectDefinitionReferenceTable projectDefinitionReferenceTable = new ProjectDefinitionReferenceTable();
				for (Map.Entry<String, Integer> entry : pdReferenceTableColumnIndexMap.entrySet()) {
					int columnIndex = pdReferenceTableColumnIndexMap.get(entry.getKey());
					if (ProjectDefinitionReferenceTableColumnHeaders.PROJECT_PRIFIX.getColumnHeader()
							.equals(entry.getKey())) {
						projectPrefix = currentRow.getCell(columnIndex).getStringCellValue();
						projectDefinitionReferenceTable
						.setProjectPrefix(projectPrefix);
					} else if (ProjectDefinitionReferenceTableColumnHeaders.PROJECT_PROFILE.getColumnHeader()
							.equals(entry.getKey())) {
						projectDefinitionReferenceTable
						.setProjectProfile(currentRow.getCell(columnIndex).getStringCellValue());
					} else if (ProjectDefinitionReferenceTableColumnHeaders.PROJECT_TYPE.getColumnHeader()
							.equals(entry.getKey())) {
						projectDefinitionReferenceTable
						.setProjectType(currentRow.getCell(columnIndex).getStringCellValue());
					} else if (ProjectDefinitionReferenceTableColumnHeaders.TAX_PURPOSE.getColumnHeader()
							.equals(entry.getKey())) {
						projectDefinitionReferenceTable
						.setTaxPurpose((int) currentRow.getCell(columnIndex).getNumericCellValue());
					} else if (ProjectDefinitionReferenceTableColumnHeaders.NETWORK_PROFILE.getColumnHeader()
							.equals(entry.getKey())) {
						projectDefinitionReferenceTable
						.setNetworkProfile(currentRow.getCell(columnIndex).getStringCellValue());
					} else if (ProjectDefinitionReferenceTableColumnHeaders.NETWORK_TYPE.getColumnHeader()
							.equals(entry.getKey())) {
						projectDefinitionReferenceTable
						.setNetworkType(currentRow.getCell(columnIndex).getStringCellValue());
					} else if (ProjectDefinitionReferenceTableColumnHeaders.SCOPE.getColumnHeader()
							.equals(entry.getKey())) {
						projectDefinitionReferenceTable.setScope(currentRow.getCell(columnIndex).getStringCellValue());
					} else if (ProjectDefinitionReferenceTableColumnHeaders.PSPID_PREFIX.getColumnHeader()
							.equals(entry.getKey())) {
						projectDefinitionReferenceTable
						.setPspidPrefix(currentRow.getCell(columnIndex).getStringCellValue());
					} else if (ProjectDefinitionReferenceTableColumnHeaders.SERIAL_NO_START_INDEX.getColumnHeader()
							.equals(entry.getKey())) {
						projectDefinitionReferenceTable
						.setSerialNoStartIndex((int) currentRow.getCell(columnIndex).getNumericCellValue());
					} else if (ProjectDefinitionReferenceTableColumnHeaders.PSPID_START_INDEX.getColumnHeader()
							.equals(entry.getKey())) {
						projectDefinitionReferenceTable
						.setPspidStartIndex((int) currentRow.getCell(columnIndex).getNumericCellValue());
					}
				}
				ETLUtil.getProjectDefinitionReferenceTableByProjectPrefix().put(projectPrefix,
						projectDefinitionReferenceTable);
			}

			fileInputStream.close();
		} catch (FileNotFoundException e) {
			logger.error(" in generateProjectDefinitionTargetWorkbook() ", e);
			throw new Exception(e);
		} catch (IOException e) {
			logger.error(" in generateProjectDefinitionTargetWorkbook() ", e);
			throw new Exception(e);
		}
		logger.debug(" exiting generateProjectDefinitionTargetWorkbook() ");
	}

	public ArrayList<String> validateColumnHeaders(final Sheet sheet) {
		ArrayList<String> headers = getColumnHeaders(sheet);
		ProjectDefinitionReferenceTableColumnHeaders[] projectDefinitionReferenceTableColumnHeaders = ProjectDefinitionReferenceTableColumnHeaders
				.values();
		for (ProjectDefinitionReferenceTableColumnHeaders projectDefinitionReferenceTableColumnHeader : projectDefinitionReferenceTableColumnHeaders) {
			if (!headers.contains(projectDefinitionReferenceTableColumnHeader.getColumnHeader())) {
				logger.error(ProjectDefinitionRefereceTableExtractor.class, new Exception(
						"Column " + projectDefinitionReferenceTableColumnHeader.getColumnHeader()
						+ " missing in source Project Definition file."));
			}
		}
		return headers;
	}

	public ArrayList<String> getColumnHeaders(final Sheet sheet) {
		logger.debug(" entering getColumnHeaders() ");
		ArrayList<String> headers = new ArrayList<String>();
		Iterator<Row> iterator = sheet.iterator();
		while (iterator.hasNext()) {
			Row currentRow = iterator.next();
			Iterator<Cell> cellIterator = currentRow.iterator();
			while (cellIterator.hasNext()) {
				Cell currentCell = cellIterator.next();
				headers.add(currentCell.getStringCellValue());
			}
			break;
		}
		logger.debug(" exiting getColumnHeaders() ");
		return headers;
	}
}
