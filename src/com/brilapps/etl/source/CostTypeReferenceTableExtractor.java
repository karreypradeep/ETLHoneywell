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
import com.brilapps.etl.target.CostTypeReferenceTable;

public class CostTypeReferenceTableExtractor {
	static Logger logger = Logger.getLogger(CostTypeReferenceTableExtractor.class);

	/**
	 * Generate target Project Definition file from the source file.
	 *
	 * @param sourceProjectDefinitionFile
	 *            source file from which target will be generated.
	 * @param destinationProjectDefinitionFile
	 *            destination file to which target file will be generated.
	 * @throws Exception
	 */
	public void extractCostTypeRefereceTable(final File sourceFileRefereceTable)
			throws Exception {
		logger.debug(" entering extractCostTypeRefereceTable() ");
		try {
			// File input stream to read source project definition file.
			FileInputStream fileInputStream = new FileInputStream(sourceFileRefereceTable);
			// Create Workbook instance holding reference to excel file
			Workbook sourceRefereceTableWorkbook = null;
			if (sourceFileRefereceTable.getName().endsWith(".xls")) {
				sourceRefereceTableWorkbook = new HSSFWorkbook(fileInputStream);
			} else {
				sourceRefereceTableWorkbook = new XSSFWorkbook(fileInputStream);
			}
			// Create sheet in source project definition work book.
			Sheet sourceRefereceTableSheet = sourceRefereceTableWorkbook
					.getSheetAt(1);
			validateColumnHeaders(sourceRefereceTableSheet);

			int colNum = 0;
			Map<String, Integer> pdReferenceTableColumnIndexMap = new HashMap<String, Integer>();
			for (String header : getColumnHeaders(sourceRefereceTableSheet)) {
				pdReferenceTableColumnIndexMap.put(header, colNum);
				colNum++;
			}

			Iterator<Row> iterator = sourceRefereceTableSheet.iterator();
			// iterate header row first and process remaining rows.
			iterator.next();
			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				String costType = "";
				CostTypeReferenceTable costTypeReferenceTable = new CostTypeReferenceTable();
				for (Map.Entry<String, Integer> entry : pdReferenceTableColumnIndexMap.entrySet()) {
					int columnIndex = pdReferenceTableColumnIndexMap.get(entry.getKey());
					if (CostTypeReferenceTableColumnHeaders.COST_TYPE.getColumnHeader()
							.equals(entry.getKey())) {
						costType = currentRow.getCell(columnIndex).getStringCellValue().trim();
						costTypeReferenceTable.setCostType(costType);
					} else if (CostTypeReferenceTableColumnHeaders.COST_TYPE_DESCRIPTION.getColumnHeader()
							.equals(entry.getKey())) {
						costTypeReferenceTable
						.setCostTypeDescription(currentRow.getCell(columnIndex).getStringCellValue().trim());
					} else if (CostTypeReferenceTableColumnHeaders.COST_TYPE_ACTUAL_DESCRIPTION.getColumnHeader()
							.equals(entry.getKey())) {
						costTypeReferenceTable
						.setCostTypeActualDescription(
								currentRow.getCell(columnIndex).getStringCellValue().trim());
					}
				}
				ETLUtil.getCostTypeReferenceTableByCostType().put(costType, costTypeReferenceTable);
			}

			fileInputStream.close();
		} catch (FileNotFoundException e) {
			logger.error(" in extractCostTypeRefereceTable() ", e);
			throw new Exception(e);
		} catch (IOException e) {
			logger.error(" in extractCostTypeRefereceTable() ", e);
			throw new Exception(e);
		}
		logger.debug(" exiting extractCostTypeRefereceTable() ");
	}

	public ArrayList<String> validateColumnHeaders(final Sheet sheet) {
		ArrayList<String> headers = getColumnHeaders(sheet);
		CostTypeReferenceTableColumnHeaders[] costTypeReferenceTableColumnHeaders = CostTypeReferenceTableColumnHeaders
				.values();
		for (CostTypeReferenceTableColumnHeaders costTypeReferenceTableColumnHeader : costTypeReferenceTableColumnHeaders) {
			if (!headers.contains(costTypeReferenceTableColumnHeader.getColumnHeader())) {
				logger.error(CostTypeReferenceTableExtractor.class,
						new Exception("Column " + costTypeReferenceTableColumnHeader.getColumnHeader()
						+ " missing in source Reference Table."));
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
				headers.add(currentCell.getStringCellValue().trim());
			}
			break;
		}
		logger.debug(" exiting getColumnHeaders() ");
		return headers;
	}
}
