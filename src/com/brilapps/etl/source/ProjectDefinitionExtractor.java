package com.brilapps.etl.source;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.brilapps.etl.ProjectConstants;

public class ProjectDefinitionExtractor {

	static Logger logger = Logger.getLogger(ProjectDefinitionExtractor.class);

	private String projectHeaderSourceFile = null;

	/**
	 * @return the projectHeaderSourceFile
	 */
	public String getProjectHeaderSourceFile() {
		return projectHeaderSourceFile;
	}

	/**
	 * @param projectHeaderSourceFile
	 *            the projectHeaderSourceFile to set
	 */
	public void setProjectHeaderSourceFile(final String projectHeaderSourceFile) {
		this.projectHeaderSourceFile = projectHeaderSourceFile;
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
				headers.add(currentCell.getStringCellValue().trim());
			}
			break;
		}
		logger.debug(" exiting getColumnHeaders() ");
		return headers;
	}

	public ArrayList<String> validateColumnHeaders(final Sheet sheet) {
		ArrayList<String> headers = getColumnHeaders(sheet);
		SourceProjectDefinitionColumnHeaders[] wbsColumnHeaders = SourceProjectDefinitionColumnHeaders.values();
		for (SourceProjectDefinitionColumnHeaders wbsColumnHeader : wbsColumnHeaders) {
			if (!headers.contains(wbsColumnHeader.getColumnHeader())) {
				logger.error(ProjectDefinitionExtractor.class,
						new Exception("Column " + wbsColumnHeader.getColumnHeader()
						+ " missing in source Project Definition file."));
			}
		}
		return headers;
	}

	public Row getProjectRow(final String projectType, final Row currentRow, final int headerColumnIndex) {
		Row projectTypeRow = null;
		int cellCount = 0;
		for (int i = 0; i < currentRow.getLastCellNum(); i++) {
			Cell currentCell = currentRow.getCell(i);
			if (headerColumnIndex == cellCount && currentCell != null
					&& currentCell.getStringCellValue().trim().equals(projectType)) {
				projectTypeRow = currentRow;
			}
			cellCount++;
		}
		return projectTypeRow;
	}


	public ArrayList<Row> getAllProjectRows(final Sheet sheet, final String projectType) {
		Iterator<Row> iterator = sheet.iterator();
		ArrayList<Row> projectRows = new ArrayList<Row>();
		ArrayList<String> headers = getColumnHeaders(sheet);
		int headerColumnIndex = 0;
		for (String header : headers) {
			if (ProjectConstants.PROJECT_HEADER_COLUMN_NAME.equals(header)) {
				break;
			}
			headerColumnIndex++;
		}
		while (iterator.hasNext()) {
			Row currentRow = iterator.next();
			Row projectTypeRow = getProjectRow(projectType, currentRow, headerColumnIndex);
			if (projectTypeRow != null) {
				projectRows.add(projectTypeRow);
			}
		}
		return projectRows;
	}


}
