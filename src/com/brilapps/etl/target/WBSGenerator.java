package com.brilapps.etl.target;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.brilapps.etl.ETLUtil;
import com.brilapps.etl.ProjectType;
import com.brilapps.etl.source.SourceWBSColumnHeaders;

public class WBSGenerator {
	static Logger logger = Logger.getLogger(WBSGenerator.class);

	public final static HashMap<TargetWBSColumnHeaders, TargetProjectDefinitionColumnHeaders> destinationSourceCloumnMaps = new HashMap<TargetWBSColumnHeaders, TargetProjectDefinitionColumnHeaders>();
	public final static HashMap<TargetWBSColumnHeaders, Object> destinationConstants = new HashMap<TargetWBSColumnHeaders, Object>();
	//public final static HashMap<String, ReferenceTable> referenceTableMap = new HashMap<String, ReferenceTable>();

	private static List<SourceWBSColumnHeaders> UNIQUE_KEYS = new ArrayList<SourceWBSColumnHeaders>();

	private static List<SourceWBSColumnHeaders> UNIQUE_KEYS_WBS_REFERENCE = new ArrayList<SourceWBSColumnHeaders>();

	static {
		UNIQUE_KEYS.add(SourceWBSColumnHeaders.PROJECTNO);
		UNIQUE_KEYS.add(SourceWBSColumnHeaders.ALT_TASKNO);
		UNIQUE_KEYS.add(SourceWBSColumnHeaders.PARENT_TASKNO);

		UNIQUE_KEYS_WBS_REFERENCE.add(SourceWBSColumnHeaders.PROJECTNO);
		UNIQUE_KEYS_WBS_REFERENCE.add(SourceWBSColumnHeaders.ALT_TASKNO);
		UNIQUE_KEYS_WBS_REFERENCE.add(SourceWBSColumnHeaders.PARENT_TASKNO);
	}
	static {

		// Map Destination Project Definition file columns with Target
		// WBS file. This will be used later to get the values
		// from Destination Project Definition file and insert into destination
		// WBS file.
		destinationSourceCloumnMaps.put(TargetWBSColumnHeaders.PBUKR, TargetProjectDefinitionColumnHeaders.VBUKR);
		destinationSourceCloumnMaps.put(TargetWBSColumnHeaders.PRCTR, TargetProjectDefinitionColumnHeaders.PRCTR);
		destinationSourceCloumnMaps.put(TargetWBSColumnHeaders.SCOPE, TargetProjectDefinitionColumnHeaders.SCOPE);
		destinationSourceCloumnMaps.put(TargetWBSColumnHeaders.WERKS, TargetProjectDefinitionColumnHeaders.WERKS);
		destinationSourceCloumnMaps.put(TargetWBSColumnHeaders.FABKL_ASSIGNMENT,
				TargetProjectDefinitionColumnHeaders.KALID);
		destinationSourceCloumnMaps.put(TargetWBSColumnHeaders.FABKL, TargetProjectDefinitionColumnHeaders.KALID);
		destinationSourceCloumnMaps.put(TargetWBSColumnHeaders.VERNR, TargetProjectDefinitionColumnHeaders.VERNR);
		destinationSourceCloumnMaps.put(TargetWBSColumnHeaders.PSTRT, TargetProjectDefinitionColumnHeaders.PLFAZ);
		destinationSourceCloumnMaps.put(TargetWBSColumnHeaders.PENDE, TargetProjectDefinitionColumnHeaders.PLSEZ);


		// Constants Add all the constants columns here so that they will be
		// directly added to target WBS file.
		destinationConstants.put(TargetWBSColumnHeaders.CLASF, "X");
		// destinationConstants.put(TargetWBSColumnHeaders.WERKS, "");
		destinationConstants.put(TargetWBSColumnHeaders.PLAKZ, "X");
		// destinationConstants.put(TargetWBSColumnHeaders.BELKZ, "X");
		//destinationConstants.put(TargetWBSColumnHeaders.FAKKZ, "X");
		// destinationConstants.put(TargetWBSColumnHeaders.VERNR, 999999);
		// destinationConstants.put(TargetWBSColumnHeaders.FKOKR, "AERO");
		destinationConstants.put(TargetWBSColumnHeaders.FKOKR, "");
		destinationConstants.put(TargetWBSColumnHeaders.PEINH, "D");
		// destinationConstants.put(TargetWBSColumnHeaders.FABKL, "YO");
		destinationConstants.put(TargetWBSColumnHeaders.KALSM, "ZEMO42");
		destinationConstants.put(TargetWBSColumnHeaders.ZSCHL, "Z00001");
		destinationConstants.put(TargetWBSColumnHeaders.SLWID, "Z000003");

		// Blank Space Constants
		// Constants Add all the Blank Spaces columns here so that they will be
		// directly added to target WBS file.
		// Blank Space Constants
		destinationConstants.put(TargetWBSColumnHeaders.POSKI, "");
		destinationConstants.put(TargetWBSColumnHeaders.PSPRI, "");
		destinationConstants.put(TargetWBSColumnHeaders.GRPKZ, "");
		destinationConstants.put(TargetWBSColumnHeaders.ASTNR, "");
		destinationConstants.put(TargetWBSColumnHeaders.FKSTL, "");
		destinationConstants.put(TargetWBSColumnHeaders.AKOKR, "");
		destinationConstants.put(TargetWBSColumnHeaders.AKSTL, "");
		destinationConstants.put(TargetWBSColumnHeaders.PDAUR, "");
		destinationConstants.put(TargetWBSColumnHeaders.ESTRT, "");
		destinationConstants.put(TargetWBSColumnHeaders.EENDE, "");
		destinationConstants.put(TargetWBSColumnHeaders.EDAUR, "");
		destinationConstants.put(TargetWBSColumnHeaders.EEINH, "");
		destinationConstants.put(TargetWBSColumnHeaders.PGSBR, "");
		destinationConstants.put(TargetWBSColumnHeaders.TXJCD, "");
		destinationConstants.put(TargetWBSColumnHeaders.SUBPR, "");
		destinationConstants.put(TargetWBSColumnHeaders.EQUNR, "");
		destinationConstants.put(TargetWBSColumnHeaders.TPLNR, "");
		destinationConstants.put(TargetWBSColumnHeaders.AENNR, "");
		destinationConstants.put(TargetWBSColumnHeaders.ZSCHM_WBS, "");
		destinationConstants.put(TargetWBSColumnHeaders.IMPRF_WBS, "");
		destinationConstants.put(TargetWBSColumnHeaders.ABGSL_WBS, "");
		destinationConstants.put(TargetWBSColumnHeaders.PGPRF, "");
		destinationConstants.put(TargetWBSColumnHeaders.XSTAT_WBS, "");
		destinationConstants.put(TargetWBSColumnHeaders.PLINT_WBS, "");
		destinationConstants.put(TargetWBSColumnHeaders.ISIZE, "");
		destinationConstants.put(TargetWBSColumnHeaders.IZWEK, "");
		destinationConstants.put(TargetWBSColumnHeaders.IUMKZ, "");
		destinationConstants.put(TargetWBSColumnHeaders.USR00, "");
		destinationConstants.put(TargetWBSColumnHeaders.USR01, "");
		destinationConstants.put(TargetWBSColumnHeaders.USR02, "");
		destinationConstants.put(TargetWBSColumnHeaders.USR04, "");
		destinationConstants.put(TargetWBSColumnHeaders.USR05, "");
		destinationConstants.put(TargetWBSColumnHeaders.USR06, "");
		destinationConstants.put(TargetWBSColumnHeaders.USR07, "");
		destinationConstants.put(TargetWBSColumnHeaders.USR08, "");
		destinationConstants.put(TargetWBSColumnHeaders.USR09, "");
		destinationConstants.put(TargetWBSColumnHeaders.USR10, "");
		destinationConstants.put(TargetWBSColumnHeaders.USR11, "");
		destinationConstants.put(TargetWBSColumnHeaders.EVGEW, "");
		destinationConstants.put(TargetWBSColumnHeaders.VERSN_EV, "");
		destinationConstants.put(TargetWBSColumnHeaders.EVMET_TXT_P, "");
		destinationConstants.put(TargetWBSColumnHeaders.EVMET_TXT_A, "");

	}

	public void writeHeaderColumns(final Sheet sheet) {
		logger.debug(" entering writeHeaderColumns() ");
		Row row = sheet.createRow(0);
		int colNum = 0;
		for (TargetWBSColumnHeaders field : TargetWBSColumnHeaders
				.getColumnHeadersByIndex()) {
			Cell cell = row.createCell(colNum++);
			cell.setCellValue(field.getColumnHeader());
		}
		logger.debug(" exiting writeHeaderColumns() ");
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
				headers.add(ETLUtil.getCellValueAsString(currentCell, logger));
			}
			break;
		}
		logger.debug(" exiting getColumnHeaders() ");
		return headers;
	}

	public void deleteDuplicateRows(final Sheet sourceSheet, final Sheet destinationSheet,
			final Set<String> convertedProjectNos) {
		logger.debug(" entering deleteDuplicateRows ");
		ArrayList<String> headers = getColumnHeaders(sourceSheet);
		ArrayList<Integer> uniqueKeyIndexes = new ArrayList<Integer>();
		ArrayList<Integer> uniqueKeyIndexesWBSReference = new ArrayList<Integer>();
		int lowLevelColumnIndex = -1, taskNoIndex = -1, parentTaskNoIndex = -1, projectNoIndex = -1;
		int indexCount = 0;
		for (String headerName : headers) {
			for (SourceWBSColumnHeaders sourceWBSColumnHeaders : UNIQUE_KEYS) {
				if (headerName.equals(sourceWBSColumnHeaders.getColumnHeader())) {
					uniqueKeyIndexes.add(indexCount);
				}
			}
			for (SourceWBSColumnHeaders sourceWBSColumnHeaders : UNIQUE_KEYS_WBS_REFERENCE) {
				if (headerName.equals(sourceWBSColumnHeaders.getColumnHeader())) {
					uniqueKeyIndexesWBSReference.add(indexCount);
				}
			}
			if (headerName.equals(SourceWBSColumnHeaders.LOW_LEVEL.toString())) {
				lowLevelColumnIndex = indexCount;
			} else if (headerName.equals(SourceWBSColumnHeaders.TASKNO.toString())) {
				taskNoIndex = indexCount;
			} else if (headerName.equals(SourceWBSColumnHeaders.PARENT_TASKNO.toString())) {
				parentTaskNoIndex = indexCount;
			} else if (headerName.equals(SourceWBSColumnHeaders.PROJECTNO.toString())) {
				projectNoIndex = indexCount;
			}
			indexCount++;
		}
		logger.debug(" in deleteDuplicateRows uniqueKeyIndexes " + uniqueKeyIndexes);
		logger.debug(" in deleteDuplicateRows lowLevelColumnIndex " + lowLevelColumnIndex);
		Set<String> uniqueKeys = new HashSet<String>();
		//Set<Integer> uniqueRows = new TreeSet<Integer>();
		Map<String, List<String>> level2TaskNosPerProjectNo = new HashMap<String, List<String>>();
		Map<String, List<Row>> projectRows = new HashMap<String, List<Row>>();

		Iterator<Row> iterator = sourceSheet.iterator();
		iterator.next();
		while (iterator.hasNext()) {
			Row currentRow = iterator.next();
			StringBuffer uniqueKey = new StringBuffer("");
			for (Integer uniqueKeyIndex : uniqueKeyIndexes) {
				uniqueKey.append(ETLUtil.getCellValueAsString(currentRow.getCell(uniqueKeyIndex), logger));
			}
			String lowLevelValue = null, projectNo = null;
			if (lowLevelColumnIndex > -1) {
				Cell currentCell = currentRow.getCell(lowLevelColumnIndex);
				if (currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					lowLevelValue = currentCell.getNumericCellValue() + "";
				} else if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {
					lowLevelValue = ETLUtil.getCellValueAsString(currentCell, logger);
				}
			}
			if (projectNoIndex > -1) {
				Cell currentCell = currentRow.getCell(projectNoIndex);
				projectNo = ETLUtil.getCellValueAsString(currentCell, logger);
			}

			if (convertedProjectNos.contains(projectNo) && !uniqueKeys.contains(uniqueKey.toString())
					&& lowLevelValue != null && !lowLevelValue.equals("01")) {
				logger.debug(" in deleteDuplicateRows adding unique key record " + uniqueKey);
				uniqueKeys.add(uniqueKey.toString());
				//uniqueRows.add(currentRow.getRowNum());
				if (lowLevelValue.equals("02")) {
					if (level2TaskNosPerProjectNo.get(projectNo) != null && !level2TaskNosPerProjectNo.get(projectNo)
							.contains(ETLUtil.getCellValueAsString(currentRow.getCell(taskNoIndex), logger))) {
						level2TaskNosPerProjectNo.get(projectNo)
						.add(ETLUtil.getCellValueAsString(currentRow.getCell(taskNoIndex), logger));
					} else {
						List<String> taskNos = new ArrayList<String>();
						taskNos.add(ETLUtil.getCellValueAsString(currentRow.getCell(taskNoIndex), logger));
						level2TaskNosPerProjectNo.put(projectNo, taskNos);
					}
					// level2ParentTaskNos.add(currentRow.getCell(taskNoIndex).getStringCellValue().trim());
				}
				if (projectRows.get(projectNo) != null) {
					projectRows.get(projectNo).add(currentRow);
				} else {
					List<Row> rows = new ArrayList<Row>();
					rows.add(currentRow);
					projectRows.put(projectNo, rows);
				}
			}
		}

		int rowCount = 1;
		logger.debug(" in deleteDuplicateRows entering uniqueRowsloop");

		List<Row> sortedRows = new ArrayList<Row>();
		for (Map.Entry<String, List<String>> entry : level2TaskNosPerProjectNo.entrySet()) {
			for (String taskNo : entry.getValue()) {
				sortedRows = getRowsSortedForTaskNoOnLowLevel(projectRows.get(entry.getKey()), sortedRows,
						taskNo,
						"00000", taskNoIndex,
						parentTaskNoIndex);
				logger.debug(" in deleteDuplicateRows entering uniqueRowsloop");
			}
		}

		for (Row  currentRow : sortedRows) {
			Row row = destinationSheet.createRow(rowCount);
			int colNum = 0;
			currentRow.getLastCellNum();
			//Iterator<Cell> cellIterator = currentRow.iterator();
			for (int i = 0; i < currentRow.getLastCellNum(); i++) {
				Cell currentCell = currentRow.getCell(i);
				Object cellValue = ETLUtil.getCellValue(currentCell, logger);
				Cell desCell = row.createCell(colNum);
				ETLUtil.setCellValue(desCell, cellValue, logger);
				colNum++;
			}
			rowCount++;
		}


		/*for (Map.Entry<String, List<Row>> entry : projectRows.entrySet()) {
			for (Row  currentRow : entry.getValue()) {
				Row row = destinationSheet.createRow(rowCount);
				int colNum = 0;
				currentRow.getLastCellNum();
				//Iterator<Cell> cellIterator = currentRow.iterator();
				for (int i = 0; i < currentRow.getLastCellNum(); i++) {
					Cell currentCell = currentRow.getCell(i);
					Object cellValue = ETLUtil.getCellValue(currentCell, logger);
					Cell desCell = row.createCell(colNum);
					ETLUtil.setCellValue(desCell, cellValue, logger);
					colNum++;
				}
				rowCount++;
			}
		}*/

		/*for (Integer uniqueRow : uniqueRows) {
			logger.debug(" in deleteDuplicateRows processing uniqueRow with number " + uniqueRow);
			Row currentRow = sourceSheet.getRow(uniqueRow);
			Row row = destinationSheet.createRow(rowCount);
			int colNum = 0;
			currentRow.getLastCellNum();
			//Iterator<Cell> cellIterator = currentRow.iterator();
			for (int i = 0; i < currentRow.getLastCellNum(); i++) {
				Cell currentCell = currentRow.getCell(i);
				Object cellValue = ETLUtil.getCellValue(currentCell, logger);
				Cell desCell = row.createCell(colNum);
				ETLUtil.setCellValue(desCell, cellValue, logger);
				colNum++;
			}
			rowCount++;
		}*/

		logger.debug(" exiting deleteDuplicateRows ");
	}

	private List<Row> getRowsSortedForTaskNoOnLowLevel(final List<Row> allRows, final List<Row> sortedRows,
			final String taskNo, final String parentTaskNo, final int taskNoIndex, final int parentTaskNoIndex) {

		for (Row row : allRows) {
			if ((taskNo == null || taskNo.equals(ETLUtil.getCellValueAsString(row.getCell(taskNoIndex), logger)))
					&& parentTaskNo.equals(ETLUtil.getCellValueAsString(row.getCell(parentTaskNoIndex), logger))) {
				sortedRows.add(row);
				getRowsSortedForTaskNoOnLowLevel(allRows, sortedRows, null,
						ETLUtil.getCellValueAsString(row.getCell(taskNoIndex), logger), taskNoIndex, parentTaskNoIndex);
			}
		}
		return sortedRows;
	}

	public void generateWBStargetFile(final File wbsSourceFile, final File wbsNonDuplicateSourceFile,
			final File destinationWBSFile, final File projectDefinitionDesFile) throws Exception {
		logger.debug("entering generateWBStargetFile ");

		try {
			WBSGenerator wBSExtractor = new WBSGenerator();
			int colNum = 0;

			FileInputStream pdDestinationFileInputStream = new FileInputStream(projectDefinitionDesFile);

			// Create Workbook instance holding reference to .xlsx file
			Workbook pdDestinationWorkbook = null;
			if (projectDefinitionDesFile.getName().endsWith(".xls")) {
				pdDestinationWorkbook = new HSSFWorkbook(pdDestinationFileInputStream);
			} else {
				pdDestinationWorkbook = new XSSFWorkbook(pdDestinationFileInputStream);
			}
			Map<String, Row> projectDefinitionRowsMap = new HashMap<String, Row>();
			Sheet pdDestinationSheet = pdDestinationWorkbook.getSheetAt(0);
			Map<String,Integer> pdTargetHeaderColumnIndexMap = new HashMap<String,Integer>();
			colNum = 0;
			for (String header : wBSExtractor.getColumnHeaders(pdDestinationSheet)) {
				pdTargetHeaderColumnIndexMap.put(header, colNum);
				colNum++;
			}
			Iterator<Row> iterator = pdDestinationSheet.iterator();
			// iterate header row first and process remaining rows.
			iterator.next();
			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				String projectNo = ETLUtil.getCellValueAsString(
						currentRow.getCell(TargetProjectDefinitionColumnHeaders.PROJECTNO.getColumnIndex() - 1),
						logger);
				projectDefinitionRowsMap.put(projectNo.trim(), currentRow);
			}
			pdDestinationFileInputStream.close();

			// WBS source file with duplicates
			FileInputStream wbsSourceFileInputStream = new FileInputStream(wbsSourceFile);

			// Create Workbook instance holding reference to .xlsx file
			Workbook wbsSourceWorkbook = null;
			if (wbsSourceFile.getName().endsWith(".xls")) {
				wbsSourceWorkbook = new HSSFWorkbook(wbsSourceFileInputStream);
			} else {
				wbsSourceWorkbook = new XSSFWorkbook(wbsSourceFileInputStream);
			}
			Sheet wbsSourceSheet = wbsSourceWorkbook.getSheetAt(0);
			logger.debug("in generateWBStargetFile before validateColumnHeaders");
			validateColumnHeaders(wbsSourceSheet);
			logger.debug("in generateWBStargetFile after validateColumnHeaders");

			Workbook wbsNonDuplicateWorkbook = new HSSFWorkbook();
			Sheet wbsNonDuplicateTargetSheet = wbsNonDuplicateWorkbook.createSheet(wbsSourceSheet.getSheetName());
			// writeHeaderColumns
			Row row = wbsNonDuplicateTargetSheet.createRow(0);
			Map<String,Integer> wbsSourceHeaderColumnIndexMap = new HashMap<String,Integer>();
			colNum = 0;
			for (String header : wBSExtractor.getColumnHeaders(wbsSourceSheet)) {
				wbsSourceHeaderColumnIndexMap.put(header, colNum);
				Cell cell = row.createCell(colNum++);
				cell.setCellValue(header);
			}

			// *********************************************deleteDuplicateRows
			logger.debug("in generateWBStargetFile before deleting duplicate records");
			wBSExtractor.deleteDuplicateRows(wbsSourceSheet, wbsNonDuplicateTargetSheet,
					projectDefinitionRowsMap.keySet());
			// *********************************************deleteDuplicateRows

			Sheet wbsNonDuplicateReferenceSheet = wbsNonDuplicateWorkbook.createSheet("ReferenceSheet");
			// writeHeaderColumns
			Row referenceHeaderRow = wbsNonDuplicateReferenceSheet.createRow(0);
			Cell cell = referenceHeaderRow.createCell(0);
			cell.setCellValue("Legacy Project No");

			cell = referenceHeaderRow.createCell(1);
			cell.setCellValue("New Project No");

			cell = referenceHeaderRow.createCell(2);
			cell.setCellValue("Task No");

			cell = referenceHeaderRow.createCell(3);
			cell.setCellValue("Alt Task No");

			cell = referenceHeaderRow.createCell(4);
			cell.setCellValue("Parent Task No");

			cell = referenceHeaderRow.createCell(5);
			cell.setCellValue("Ident");



			// Iterate through each row in non duplicate WBS file
			Workbook targetWBSWorkbook = new HSSFWorkbook();
			Sheet targetWBSSheet = targetWBSWorkbook.createSheet("WBSLoader");
			writeHeaderColumns(targetWBSSheet);

			Set<String> convertedProjectNos = new HashSet<String>();
			Sheet wbsNonDuplicateSheet = wbsNonDuplicateWorkbook.getSheetAt(0);
			Iterator<Row> wbsNonDuplicateSheetIterator = wbsNonDuplicateSheet.iterator();
			// iterate header row firstand process remaining rows.
			wbsNonDuplicateSheetIterator.next();
			int targetRowCount = 1;
			logger.debug("in generateWBStargetFile before iterating duplicate records");
			int wbsReferenceRowCount = 1;
			while (wbsNonDuplicateSheetIterator.hasNext()) {
				Row wbsNonDuplicateCurrentRow = wbsNonDuplicateSheetIterator.next();
				// create a row for each of non duplicate row of WBS source file
				// Project Number Cell in index 0
				int projectNoIndex = wbsSourceHeaderColumnIndexMap
						.get(SourceWBSColumnHeaders.PROJECTNO.getColumnHeader());
				String projectNo = ETLUtil.getCellValueAsString(wbsNonDuplicateCurrentRow.getCell(projectNoIndex),logger);
				logger.debug("in generateWBStargetFile processing " + projectNo);
				// Get the corresponding project definition record of the
				// generated file
				Row correspondingProjectDefinitionRow = projectDefinitionRowsMap.get(projectNo);
				if (correspondingProjectDefinitionRow != null) {

					// Create a new NetworkHeaderWBSReferenceTable which will be
					// used to generate network header target file.
					NetworkHeaderWBSReferenceTable networkHeaderWBSReferenceTable = new NetworkHeaderWBSReferenceTable();
					networkHeaderWBSReferenceTable.setProjectNo(projectNo);
					networkHeaderWBSReferenceTable
					.setAltTaskNo(ETLUtil.getCellValueAsString(wbsNonDuplicateCurrentRow
							.getCell(wbsSourceHeaderColumnIndexMap
									.get(SourceWBSColumnHeaders.ALT_TASKNO.getColumnHeader())),
							logger));

					networkHeaderWBSReferenceTable.setTaskNo(ETLUtil.getCellValueAsString(
							wbsNonDuplicateCurrentRow.getCell(
									wbsSourceHeaderColumnIndexMap.get(SourceWBSColumnHeaders.TASKNO.getColumnHeader())),
							logger));

					List<Row> targetWBSRows = new ArrayList<Row>();
					int pdTargetProjectTypeIndex = pdTargetHeaderColumnIndexMap
							.get(TargetProjectDefinitionColumnHeaders.PROJECT_TYPE.getColumnHeader());
					Cell projectTypeCell = correspondingProjectDefinitionRow.getCell(pdTargetProjectTypeIndex);
					ProjectType projectType = null;
					if (projectTypeCell != null && ETLUtil.getCellValueAsString(projectTypeCell,logger) != null) {
						projectType = ProjectType
								.getProjectTypeByProjectPrefix(ETLUtil.getCellValueAsString(projectTypeCell,logger));
					}

					int revenueColumnIndex = pdTargetHeaderColumnIndexMap
							.get(TargetProjectDefinitionColumnHeaders.PD_REVENUE.getColumnHeader());
					Cell revenueCell = correspondingProjectDefinitionRow.getCell(revenueColumnIndex);
					String pdRevenue = null;
					if (revenueCell != null && ETLUtil.getCellValueAsString(revenueCell,logger) != null) {
						pdRevenue = ETLUtil.getCellValueAsString(revenueCell,logger);
					}
					boolean addNetworkHeaderRows = true;
					// if record is not already added then create 2 or three
					// records one each for stuff 1 and 2
					// for stuff 3 create a row only if revenue is Y in
					// project definition file.
					if (!convertedProjectNos.contains(projectNo)) {
						logger.debug("in generateWBStargetFile adding project definition and stufe row ");
						targetWBSRows.add(targetWBSSheet.createRow(targetRowCount));
						// create level 2 row
						targetRowCount++;
						targetWBSRows.add(targetWBSSheet.createRow(targetRowCount));
						targetRowCount++;
						// add WBS records only if revenue is "Y" in project
						// definition file
						if (pdRevenue != null && pdRevenue.equals("Y")) {
							// create level 3 row
							targetWBSRows.add(targetWBSSheet.createRow(targetRowCount));
							targetRowCount++;
						} else {
							addNetworkHeaderRows = false;
						}
						convertedProjectNos.add(projectNo);
					} else {
						// create a row in WBS file only if revenue is Y in
						// project definition file.
						if (pdRevenue != null && pdRevenue.equals("Y")) {
							targetWBSRows.add(targetWBSSheet.createRow(targetRowCount));
							targetRowCount++;
							addNetworkHeaderRows = true;
						} else {
							addNetworkHeaderRows = false;
						}
					}
					int projectWiseRowCount = 1;
					for (Row targetWBSRow : targetWBSRows) {
						Row referenceRow = wbsNonDuplicateReferenceSheet.createRow(wbsReferenceRowCount);

						// Reference WBS legacy project number
						Cell wbsReferenceCell = referenceRow.createCell(0);
						ETLUtil.setCellValue(wbsReferenceCell, networkHeaderWBSReferenceTable.getProjectNo(),
								logger);

						for (TargetWBSColumnHeaders targetWBSColumnHeader : TargetWBSColumnHeaders
								.getColumnHeadersByIndex()) {
							if (TargetWBSColumnHeaders.SERIAL == targetWBSColumnHeader) {
								Cell desCell = targetWBSRow.createCell(targetWBSColumnHeader.getColumnIndex() - 1);
								try {
									double serialNo = Double.parseDouble(ETLUtil.getCellValue(
											correspondingProjectDefinitionRow.getCell(pdTargetHeaderColumnIndexMap.get(
													TargetProjectDefinitionColumnHeaders.SERIAL.getColumnHeader())),
											logger).toString());
									ETLUtil.setCellValue(desCell, (int) serialNo, logger);
									networkHeaderWBSReferenceTable.setSerialNo((long) serialNo);

									// Reference WBS new project number
									wbsReferenceCell = referenceRow.createCell(1);
									ETLUtil.setCellValue(wbsReferenceCell, (long) serialNo, logger);

									logger.debug("in generateWBStargetFile adding serial no " + serialNo);
								} catch (NumberFormatException nfe) {
									logger.error("in generateWBStargetFile adding serial no ", nfe);
								}

							} else if (TargetWBSColumnHeaders.STUFE == targetWBSColumnHeader) {
								Cell desCell = targetWBSRow.createCell(targetWBSColumnHeader.getColumnIndex() - 1);
								if ((projectWiseRowCount == 1 || projectWiseRowCount == 2)
										&& targetWBSRows.size() > 1) {
									ETLUtil.setCellValue(desCell, projectWiseRowCount, logger);
								} else {
									// Get LOWER_LEVEL cell and add 1
									ETLUtil.setCellValue(desCell, Integer.valueOf(ETLUtil.getCellValueAsString(wbsNonDuplicateCurrentRow
											.getCell(wbsSourceHeaderColumnIndexMap
													.get(SourceWBSColumnHeaders.LOW_LEVEL.getColumnHeader())),logger)) + 1, logger);
								}
							} else if (TargetWBSColumnHeaders.BELKZ == targetWBSColumnHeader) {
								Cell desCell = targetWBSRow.createCell(targetWBSColumnHeader.getColumnIndex() - 1);
								if (projectWiseRowCount == 1 && targetWBSRows.size() > 1) {
									ETLUtil.setCellValue(desCell, "", logger);
								} else {
									// Get LOWER_LEVEL cell and add 1
									ETLUtil.setCellValue(desCell, "X", logger);
								}
							} else if (TargetWBSColumnHeaders.IDENT == targetWBSColumnHeader) {
								String ident = "";
								Cell desCell = targetWBSRow.createCell(targetWBSColumnHeader.getColumnIndex() - 1);
								if (projectWiseRowCount == 1 && targetWBSRows.size() > 1) {
									ident =
											ETLUtil.getCellValueAsString(correspondingProjectDefinitionRow
													.getCell(pdTargetHeaderColumnIndexMap
															.get(TargetProjectDefinitionColumnHeaders.PSPID
																	.getColumnHeader())),
													logger);
								} else if (projectWiseRowCount == 2 && targetWBSRows.size() > 1) {
									ident =
											ETLUtil.getCellValueAsString(correspondingProjectDefinitionRow
													.getCell(pdTargetHeaderColumnIndexMap
															.get(TargetProjectDefinitionColumnHeaders.PSPID
																	.getColumnHeader())),
													logger) + "-BILL1";
								} else {
									ident = ETLUtil.getCellValueAsString(correspondingProjectDefinitionRow.getCell(
											pdTargetHeaderColumnIndexMap.get(TargetProjectDefinitionColumnHeaders.PSPID
													.getColumnHeader())),
											logger)
											+ "-"
											+ ETLUtil.getCellValueAsString(
													wbsNonDuplicateCurrentRow.getCell(wbsSourceHeaderColumnIndexMap
															.get(SourceWBSColumnHeaders.TASKNO.getColumnHeader())),
													logger);
									ETLUtil.setCellValue(desCell, ident, logger);

								}
								ETLUtil.setCellValue(desCell, ident, logger);
								networkHeaderWBSReferenceTable.setIdent(ident);
								logger.debug("in generateWBStargetFile adding IDENT " + ident);

								wbsReferenceCell = referenceRow.createCell(2);
								ETLUtil.setCellValue(wbsReferenceCell,
										networkHeaderWBSReferenceTable.getTaskNo(), logger);

								wbsReferenceCell = referenceRow.createCell(3);
								ETLUtil.setCellValue(wbsReferenceCell,
										networkHeaderWBSReferenceTable.getAltTaskNo(), logger);

								wbsReferenceCell = referenceRow.createCell(4);
								ETLUtil.setCellValue(wbsReferenceCell,
										ETLUtil.getCellValueAsString(
												wbsNonDuplicateCurrentRow.getCell(wbsSourceHeaderColumnIndexMap
														.get(SourceWBSColumnHeaders.PARENT_TASKNO.getColumnHeader())),
												logger),
										logger);
								wbsReferenceCell = referenceRow.createCell(5);
								ETLUtil.setCellValue(wbsReferenceCell, ident, logger);

							} else if (TargetWBSColumnHeaders.POST1 == targetWBSColumnHeader) {
								Cell desCell = targetWBSRow.createCell(targetWBSColumnHeader.getColumnIndex() - 1);
								if (projectWiseRowCount == 1 && targetWBSRows.size() > 1) {
									int pdTargetHeaderColumnIndex = pdTargetHeaderColumnIndexMap
											.get(TargetProjectDefinitionColumnHeaders.POST1.getColumnHeader());
									Object post1 = ETLUtil.getCellValue(
											correspondingProjectDefinitionRow.getCell(pdTargetHeaderColumnIndex),
											logger);
									ETLUtil.setCellValue(desCell, post1, logger);
								} else if (projectWiseRowCount == 2 && targetWBSRows.size() > 1) {
									ETLUtil.setCellValue(desCell, "BILLING", logger);
									logger.debug(
											"in generateWBStargetFile adding POST1 "
													+ ETLUtil.getCellValueAsString(desCell, logger));
								} else {
									Object post1 = ETLUtil.getCellValue(
											wbsNonDuplicateCurrentRow.getCell(wbsSourceHeaderColumnIndexMap
													.get(SourceWBSColumnHeaders.DESCRIPTION.getColumnHeader())),
											logger);
									ETLUtil.setCellValue(desCell, post1, logger);
								}
							} else if (TargetWBSColumnHeaders.PRART == targetWBSColumnHeader) {
								Cell desCell = targetWBSRow.createCell(targetWBSColumnHeader.getColumnIndex() - 1);
								ETLUtil.setCellValue(desCell, projectType.getProjectType(), logger);
								networkHeaderWBSReferenceTable.setProjectType(projectType);
								networkHeaderWBSReferenceTable.setProfid(projectType.getNetworkProfile());
								networkHeaderWBSReferenceTable.setPsAufart(projectType.getNetworkType());
							} else if (TargetWBSColumnHeaders.USR03 == targetWBSColumnHeader) {
								Cell desCell = targetWBSRow.createCell(targetWBSColumnHeader.getColumnIndex() - 1);
								networkHeaderWBSReferenceTable.setUsr03(projectType.getTaxPurpose());
								ETLUtil.setCellValue(desCell, projectType.getTaxPurpose(), logger);
							} else if (destinationSourceCloumnMaps.get(targetWBSColumnHeader) != null) {
								Cell desCell = targetWBSRow.createCell(targetWBSColumnHeader.getColumnIndex() - 1);
								TargetProjectDefinitionColumnHeaders targetProjectDefinitionColumnHeader = destinationSourceCloumnMaps
										.get(targetWBSColumnHeader);
								int pdTargetHeaderColumnIndex = pdTargetHeaderColumnIndexMap
										.get(targetProjectDefinitionColumnHeader.getColumnHeader());
								Cell currentCell = correspondingProjectDefinitionRow.getCell(pdTargetHeaderColumnIndex);
								logger.debug("in generateWBStargetFile adding destination column  "
										+ targetWBSColumnHeader.getColumnHeader());
								String cellValue = "";

								if (currentCell != null) {
									cellValue = ETLUtil.getCellValueAsString(currentCell, logger);
									ETLUtil.setCellValue(desCell, cellValue, logger);
								}

								if (TargetWBSColumnHeaders.WERKS == targetWBSColumnHeader) {
									networkHeaderWBSReferenceTable.setWerks(Integer.valueOf(cellValue));
									ETLUtil.setCellValue(desCell, "", logger);
								} else if (TargetWBSColumnHeaders.PRCTR == targetWBSColumnHeader) {
									networkHeaderWBSReferenceTable.setPrctr(Integer.valueOf(cellValue));
								} else if (TargetWBSColumnHeaders.SCOPE == targetWBSColumnHeader) {
									networkHeaderWBSReferenceTable.setScope(cellValue.toString());
								} else if (TargetWBSColumnHeaders.PSTRT == targetWBSColumnHeader) {
									networkHeaderWBSReferenceTable.setGstrp(cellValue.toString());
								} else if (TargetWBSColumnHeaders.PENDE == targetWBSColumnHeader) {
									networkHeaderWBSReferenceTable.setGltrp(cellValue.toString());
								} else if (TargetWBSColumnHeaders.FABKL == targetWBSColumnHeader) {
									networkHeaderWBSReferenceTable.setFabkl(cellValue.toString());
								}
							} else if (TargetWBSColumnHeaders.FAKKZ == targetWBSColumnHeader) {
								if (projectWiseRowCount == 2) {
									Cell desCell = targetWBSRow.createCell(targetWBSColumnHeader.getColumnIndex() - 1);
									ETLUtil.setCellValue(desCell, "X", logger);
									logger.debug(
											"in generateWBStargetFile adding destination column FAKKZ with value "
													+ "X");
								}
							} else if (destinationConstants.get(targetWBSColumnHeader) != null) {
								Cell desCell = targetWBSRow.createCell(targetWBSColumnHeader.getColumnIndex() - 1);
								ETLUtil.setCellValue(desCell, destinationConstants.get(targetWBSColumnHeader), logger);
								if (TargetWBSColumnHeaders.KALSM == targetWBSColumnHeader) {
									networkHeaderWBSReferenceTable.setKalsm(ETLUtil.getCellValueAsString(desCell,logger));
								} else if (TargetWBSColumnHeaders.ZSCHL == targetWBSColumnHeader) {
									networkHeaderWBSReferenceTable.setZschl(ETLUtil.getCellValueAsString(desCell,logger));
								}
							}
						}
						projectWiseRowCount++;
						wbsReferenceRowCount++;
					}
					// targetRowCount++;
					if (addNetworkHeaderRows) {
						ETLUtil.getNetworkHeaderWBSReferenceTable().put(
								networkHeaderWBSReferenceTable.getProjectNo()
								+ networkHeaderWBSReferenceTable.getAltTaskNo(),
								networkHeaderWBSReferenceTable);
					}
				}
			}

			logger.debug("in generateWBStargetFile after deleting duplicate records");
			FileOutputStream outputStream = new FileOutputStream(wbsNonDuplicateSourceFile);
			wbsNonDuplicateWorkbook.write(outputStream);
			outputStream.close();
			wbsSourceFileInputStream.close();

			FileOutputStream targetOutputStream = new FileOutputStream(destinationWBSFile);
			targetWBSWorkbook.write(targetOutputStream);
			targetOutputStream.close();

		} catch (Exception e) {
			logger.error(" in generateWBStargetFile() ", e);
			throw new Exception(e);
		}
		logger.debug("exiting generateWBStargetFile ");
	}

	public ArrayList<String> validateColumnHeaders(final Sheet sheet) throws Exception {
		ArrayList<String> headers = getColumnHeaders(sheet);
		SourceWBSColumnHeaders[] sourceWBSColumnHeaders = SourceWBSColumnHeaders.values();
		for (SourceWBSColumnHeaders sourceWBSColumnHeader : sourceWBSColumnHeaders) {
			if (!headers.contains(sourceWBSColumnHeader.getColumnHeader())) {
				logger.error(" in validateColumnHeaders() ", new Exception(
						"Column " + sourceWBSColumnHeader.getColumnHeader()
						+ " missing in source WBS file."));
				throw new Exception(
						"Column " + sourceWBSColumnHeader.getColumnHeader() + " missing in source WBS file.");
			}
		}
		return headers;
	}

	/*public static void main(final String[] args) {

		new WBSGenerator().generateWBStargetFile(new File("D:/BavaProject/Files/SAPOttawaProjectWBS.xls"),
				new File("D:/BavaProject/GeneratedFiles/SAPOttawaProjectNoDupWBS.xls"),
				new File("D:/BavaProject/GeneratedFiles/WBSTarget.xls"),
				new File("D:/BavaProject/GeneratedFiles/projectDefinitionTarget.xls"));
	}*/

}
