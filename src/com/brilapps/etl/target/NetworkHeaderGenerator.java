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
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.brilapps.etl.ETLUtil;
import com.brilapps.etl.source.SourceNetworkHeaderColumnHeaders;
import com.brilapps.etl.source.SourceWBSColumnHeaders;

public class NetworkHeaderGenerator {
	static Logger logger = Logger.getLogger(NetworkHeaderGenerator.class);
	public final static long START_SERIAL_NUMBER = 1000;

	public final static HashMap<TargetNetworkHeaderColumnHeaders, String> destinationConstants = new HashMap<TargetNetworkHeaderColumnHeaders, String>();

	private static List<SourceNetworkHeaderColumnHeaders> UNIQUE_KEYS_NETWORK_HEADER = new ArrayList<SourceNetworkHeaderColumnHeaders>();


	static {
		UNIQUE_KEYS_NETWORK_HEADER.add(SourceNetworkHeaderColumnHeaders.PROJECTNO);
		UNIQUE_KEYS_NETWORK_HEADER.add(SourceNetworkHeaderColumnHeaders.ALT_TASKNO);
	}
	static {

		// Map Destination Project Definition file columns with Target
		// WBS file. This will be used later to get the values
		// from Destination Project Definition file and insert into destination
		// WBS file.


		// Constants Add all the constants columns here so that they will be
		// directly added to target WBS file.
		destinationConstants.put(TargetNetworkHeaderColumnHeaders.DISPO, "001");
		destinationConstants.put(TargetNetworkHeaderColumnHeaders.TERKZ, "");
		destinationConstants.put(TargetNetworkHeaderColumnHeaders.KLVARP, "");
		destinationConstants.put(TargetNetworkHeaderColumnHeaders.KLVARI, "");
		destinationConstants.put(TargetNetworkHeaderColumnHeaders.NW_PLANCOST, "");
		destinationConstants.put(TargetNetworkHeaderColumnHeaders.AUDISP, "");

		// Blank Space Constants
		// Constants Add all the Blank Spaces columns here so that they will be
		// directly added to target WBS file.
		// Blank Space Constants
		// destinationConstants.put(TargetNetworkHeaderColumnHeaders.GSTRP, "");
		// destinationConstants.put(TargetNetworkHeaderColumnHeaders.GLTRP, "");
		destinationConstants.put(TargetNetworkHeaderColumnHeaders.TXJCD, "");
		destinationConstants.put(TargetNetworkHeaderColumnHeaders.PLGRP, "");
		destinationConstants.put(TargetNetworkHeaderColumnHeaders.KOSTV, "");
		destinationConstants.put(TargetNetworkHeaderColumnHeaders.DISPO_CO, "");
		destinationConstants.put(TargetNetworkHeaderColumnHeaders.APRIO, "");

	}

	public void writeHeaderColumns(final Sheet sheet) {
		logger.debug(" entering writeHeaderColumns() ");
		Row row = sheet.createRow(0);
		int colNum = 0;
		for (TargetNetworkHeaderColumnHeaders field : TargetNetworkHeaderColumnHeaders
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
				headers.add(currentCell.getStringCellValue().trim());
			}
			break;
		}
		logger.debug(" exiting getColumnHeaders() ");
		return headers;
	}

	public void deleteDuplicateRowsForNetworkHeader(final Sheet sourceNetworkHeaderSheet,
			final Sheet destinationNetworkHeaderSheet, final Set<String> convertedProjectNos) {
		logger.debug(" entering deleteDuplicateRowsForNetworkHeader ");
		ArrayList<String> headers = getColumnHeaders(sourceNetworkHeaderSheet);
		ArrayList<Integer> uniqueKeyIndexes = new ArrayList<Integer>();
		int projectNoIndex = -1;
		int indexCount = 0;
		for (String headerName : headers) {
			for (SourceNetworkHeaderColumnHeaders sourceNetworkHeaderColumnHeader : UNIQUE_KEYS_NETWORK_HEADER) {
				if (headerName.equals(sourceNetworkHeaderColumnHeader.getColumnHeader())) {
					uniqueKeyIndexes.add(indexCount);
				}
			}
			if (headerName.equals(SourceWBSColumnHeaders.PROJECTNO.toString())) {
				projectNoIndex = indexCount;
			}
			indexCount++;
		}
		logger.debug(" in deleteDuplicateRowsForNetworkHeader uniqueKeyIndexes " + uniqueKeyIndexes);
		Set<String> uniqueKeys = new HashSet<String>();
		Set<Integer> uniqueRows = new TreeSet<Integer>();
		String projectNo = null;
		Iterator<Row> iterator = sourceNetworkHeaderSheet.iterator();
		iterator.next();
		while (iterator.hasNext()) {
			Row currentRow = iterator.next();
			StringBuffer uniqueKey = new StringBuffer("");
			for (Integer uniqueKeyIndex : uniqueKeyIndexes) {
				uniqueKey.append(currentRow.getCell(uniqueKeyIndex).getStringCellValue().trim());
			}
			if (projectNoIndex > -1) {
				Cell currentCell = currentRow.getCell(projectNoIndex);
				projectNo = currentCell.getStringCellValue().trim();
			}
			if (convertedProjectNos.contains(projectNo) && !uniqueKeys.contains(uniqueKey.toString())) {
				logger.debug(" in deleteDuplicateRowsForNetworkHeader adding unique key record " + uniqueKey);
				uniqueKeys.add(uniqueKey.toString());
				uniqueRows.add(currentRow.getRowNum());
			}
		}

		int rowCount = 1;
		logger.debug(" in deleteDuplicateRowsForNetworkHeader entering uniqueRowsloop");
		for (Integer uniqueRow : uniqueRows) {
			logger.debug(" in deleteDuplicateRowsForNetworkHeader processing uniqueRow with number " + uniqueRow);
			Row currentRow = sourceNetworkHeaderSheet.getRow(uniqueRow);
			Row row = destinationNetworkHeaderSheet.createRow(rowCount);
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
		logger.debug(" exiting deleteDuplicateRowsForNetworkHeader ");
	}

	public void generateNetworkHeaderTargetFile(final File projectDefinitionDestinationFile,
			final File networkHeaderSourceFile, final File networkHeaderNonDuplicateSourceFile,
			final File destinationNetworkHeaderFile)
					throws Exception {
		logger.debug("entering generateNetworkHeaderTargetFile ");

		try {

			FileInputStream pdDestinationFileInputStream = new FileInputStream(projectDefinitionDestinationFile);

			// Create Workbook instance holding reference to .xlsx file
			Workbook pdDestinationWorkbook = null;
			if (projectDefinitionDestinationFile.getName().endsWith(".xls")) {
				pdDestinationWorkbook = new HSSFWorkbook(pdDestinationFileInputStream);
			} else {
				pdDestinationWorkbook = new XSSFWorkbook(pdDestinationFileInputStream);
			}
			Set<String> convertedProjectNos = new HashSet<String>();
			Sheet pdDestinationSheet = pdDestinationWorkbook.getSheetAt(0);
			Iterator<Row> iterator = pdDestinationSheet.iterator();
			// iterate header row first and process remaining rows.
			iterator.next();
			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				String projectNo = currentRow
						.getCell(TargetProjectDefinitionColumnHeaders.PROJECTNO.getColumnIndex() - 1)
						.getStringCellValue().trim();
				convertedProjectNos.add(projectNo.trim());
			}
			pdDestinationFileInputStream.close();

			// WBS source file with duplicates
			FileInputStream networkHeaderSourceFileInputStream = new FileInputStream(networkHeaderSourceFile);

			// Create Workbook instance holding reference to .xlsx file
			Workbook networkHeaderSourceWorkbook = null;
			if (networkHeaderSourceFile.getName().endsWith(".xls")) {
				networkHeaderSourceWorkbook = new HSSFWorkbook(networkHeaderSourceFileInputStream);
			} else {
				networkHeaderSourceWorkbook = new XSSFWorkbook(networkHeaderSourceFileInputStream);
			}
			Sheet networkHeaderSourceSheet = networkHeaderSourceWorkbook.getSheetAt(0);
			logger.debug("in generateNetworkHeaderTargetFile before validateColumnHeaders");
			validateColumnHeaders(networkHeaderSourceSheet);
			logger.debug("in generateNetworkHeaderTargetFile after validateColumnHeaders");

			Workbook networkHeaderNonDuplicateWorkbook = new HSSFWorkbook();
			Sheet networkHeaderNonDuplicateTargetSheet = networkHeaderNonDuplicateWorkbook.createSheet(networkHeaderSourceSheet.getSheetName());
			// writeHeaderColumns
			Row row = networkHeaderNonDuplicateTargetSheet.createRow(0);
			int colNum = 0;
			Map<String,Integer> networkHeaderSourceHeaderColumnIndexMap = new HashMap<String,Integer>();
			for (String header : getColumnHeaders(networkHeaderSourceSheet)) {
				networkHeaderSourceHeaderColumnIndexMap.put(header, colNum);
				Cell cell = row.createCell(colNum++);
				cell.setCellValue(header);
			}
			logger.debug("in generateNetworkHeaderTargetFile before deleting duplicate records");
			deleteDuplicateRowsForNetworkHeader(networkHeaderSourceSheet, networkHeaderNonDuplicateTargetSheet,
					convertedProjectNos);
			logger.debug("in generateNetworkHeaderTargetFile after deleting duplicate records");
			FileOutputStream outputStream = new FileOutputStream(networkHeaderNonDuplicateSourceFile);
			networkHeaderNonDuplicateWorkbook.write(outputStream);
			outputStream.close();
			networkHeaderSourceFileInputStream.close();

			// Iterate through each row in non duplicate WBS file
			Workbook targetNetworkHeaderWorkbook = new HSSFWorkbook();
			Sheet targetNetworkHeaderSheet = targetNetworkHeaderWorkbook.createSheet("NetworkHeaderLoader");
			writeHeaderColumns(targetNetworkHeaderSheet);

			Sheet wbsNonDuplicateSheet = networkHeaderNonDuplicateWorkbook.getSheetAt(0);
			Iterator<Row> networkHeaderNonDuplicateSheetIterator = wbsNonDuplicateSheet.iterator();
			// iterate header row firstand process remaining rows.
			networkHeaderNonDuplicateSheetIterator.next();
			int targetRowCount = 1;
			logger.debug("in generateNetworkHeaderTargetFile before iterating duplicate records");
			int projectNoColumnIndex = networkHeaderSourceHeaderColumnIndexMap
					.get(SourceNetworkHeaderColumnHeaders.PROJECTNO.toString());
			/*int taskNoColumnIndex = networkHeaderSourceHeaderColumnIndexMap
					.get(SourceNetworkHeaderColumnHeaders.TASKNO.toString());*/
			int altTaskNoColumnIndex = networkHeaderSourceHeaderColumnIndexMap
					.get(SourceNetworkHeaderColumnHeaders.ALT_TASKNO.toString());
			int taskDescriptionColumnIndex = networkHeaderSourceHeaderColumnIndexMap
					.get(SourceNetworkHeaderColumnHeaders.TASK_DESCRIPTION.toString());
			while (networkHeaderNonDuplicateSheetIterator.hasNext()) {
				Row networkHeaderNonDuplicateCurrentRow = networkHeaderNonDuplicateSheetIterator.next();
				String uniqueKey = networkHeaderNonDuplicateCurrentRow.getCell(projectNoColumnIndex)
						.getStringCellValue().trim()
						+ networkHeaderNonDuplicateCurrentRow.getCell(altTaskNoColumnIndex).getStringCellValue().trim();
				NetworkHeaderWBSReferenceTable networkHeaderWBSReferenceTable = ETLUtil
						.getNetworkHeaderWBSReferenceTable().get(uniqueKey);
				if (networkHeaderWBSReferenceTable != null) {
					Row targetNetworkHeaderRow = targetNetworkHeaderSheet.createRow(targetRowCount);
					NetworkHeaderActivityReferenceTable networkHeaderActivityReferenceTable = new NetworkHeaderActivityReferenceTable();
					for (TargetNetworkHeaderColumnHeaders targetNetworkHeaderColumnHeader : TargetNetworkHeaderColumnHeaders
							.getColumnHeadersByIndex()) {

						if (TargetNetworkHeaderColumnHeaders.SERIAL == targetNetworkHeaderColumnHeader) {
							Cell cell = targetNetworkHeaderRow
									.createCell(targetNetworkHeaderColumnHeader.getColumnIndex() - 1);
							ETLUtil.setCellValue(cell, START_SERIAL_NUMBER + targetRowCount, logger);
							networkHeaderActivityReferenceTable
							.setSerialNo(START_SERIAL_NUMBER + targetRowCount);
							networkHeaderActivityReferenceTable.setKalid(networkHeaderWBSReferenceTable.getFabkl());
							networkHeaderActivityReferenceTable
							.setUser03(networkHeaderWBSReferenceTable.getUsr03());
							logger.debug(" in generateProjectDefinitionRows() for row " + targetRowCount
									+ " serial no generated is " + (START_SERIAL_NUMBER + targetRowCount));
						} else if (TargetNetworkHeaderColumnHeaders.KTEXT == targetNetworkHeaderColumnHeader) {
							Cell cell = targetNetworkHeaderRow
									.createCell(targetNetworkHeaderColumnHeader.getColumnIndex() - 1);
							ETLUtil.setCellValue(cell, networkHeaderNonDuplicateCurrentRow
									.getCell(taskDescriptionColumnIndex).getStringCellValue().trim(), logger);
							logger.debug(" in generateProjectDefinitionRows() for row " + targetRowCount
									+ " KTEXT is " + networkHeaderNonDuplicateCurrentRow.getCell(taskDescriptionColumnIndex)
									.getStringCellValue().trim());
						} else if (TargetNetworkHeaderColumnHeaders.PROFID == targetNetworkHeaderColumnHeader) {
							Cell cell = targetNetworkHeaderRow
									.createCell(targetNetworkHeaderColumnHeader.getColumnIndex() - 1);
							ETLUtil.setCellValue(cell, networkHeaderWBSReferenceTable.getProfid(), logger);
							logger.debug(" in generateProjectDefinitionRows() for row " + targetRowCount
									+ " PROFID is " + networkHeaderWBSReferenceTable.getProfid());
						} else if (TargetNetworkHeaderColumnHeaders.PS_AUFART == targetNetworkHeaderColumnHeader) {
							Cell cell = targetNetworkHeaderRow
									.createCell(targetNetworkHeaderColumnHeader.getColumnIndex() - 1);
							ETLUtil.setCellValue(cell, networkHeaderWBSReferenceTable.getPsAufart(), logger);
							logger.debug(" in generateProjectDefinitionRows() for row " + targetRowCount + " PS_AUFART is "
									+ networkHeaderWBSReferenceTable.getProfid());
						} else if (TargetNetworkHeaderColumnHeaders.WERKS == targetNetworkHeaderColumnHeader) {
							Cell cell = targetNetworkHeaderRow
									.createCell(targetNetworkHeaderColumnHeader.getColumnIndex() - 1);
							ETLUtil.setCellValue(cell, networkHeaderWBSReferenceTable.getWerks(), logger);
							networkHeaderActivityReferenceTable.setWerks(networkHeaderWBSReferenceTable.getWerks());
							logger.debug(" in generateProjectDefinitionRows() for row " + targetRowCount + " WERKS is "
									+ networkHeaderWBSReferenceTable.getProfid());
						} else if (TargetNetworkHeaderColumnHeaders.PRONR == targetNetworkHeaderColumnHeader) {
							Cell cell = targetNetworkHeaderRow
									.createCell(targetNetworkHeaderColumnHeader.getColumnIndex() - 1);
							ETLUtil.setCellValue(cell, networkHeaderWBSReferenceTable.getIdent().substring(0, 9),
									logger);
							logger.debug(" in generateProjectDefinitionRows() for row " + targetRowCount + " PRONR is "
									+ networkHeaderWBSReferenceTable.getProfid());
						} else if (TargetNetworkHeaderColumnHeaders.PROJN == targetNetworkHeaderColumnHeader) {
							Cell cell = targetNetworkHeaderRow
									.createCell(targetNetworkHeaderColumnHeader.getColumnIndex() - 1);
							ETLUtil.setCellValue(cell, networkHeaderWBSReferenceTable.getIdent(), logger);
							networkHeaderActivityReferenceTable.setIdent(networkHeaderWBSReferenceTable.getIdent());
							logger.debug(" in generateProjectDefinitionRows() for row " + targetRowCount + " PROJN is "
									+ networkHeaderWBSReferenceTable.getProfid());
						} else if (TargetNetworkHeaderColumnHeaders.SCOPE == targetNetworkHeaderColumnHeader) {
							Cell cell = targetNetworkHeaderRow
									.createCell(targetNetworkHeaderColumnHeader.getColumnIndex() - 1);
							ETLUtil.setCellValue(cell, networkHeaderWBSReferenceTable.getScope(), logger);
							networkHeaderActivityReferenceTable.setScope(networkHeaderWBSReferenceTable.getScope());
							logger.debug(" in generateProjectDefinitionRows() for row " + targetRowCount + " SCOPE is "
									+ networkHeaderWBSReferenceTable.getProfid());
						} else if (TargetNetworkHeaderColumnHeaders.KALSM == targetNetworkHeaderColumnHeader) {
							Cell cell = targetNetworkHeaderRow
									.createCell(targetNetworkHeaderColumnHeader.getColumnIndex() - 1);
							ETLUtil.setCellValue(cell, networkHeaderWBSReferenceTable.getKalsm(), logger);
							networkHeaderActivityReferenceTable.setKalsm(networkHeaderWBSReferenceTable.getKalsm());
							logger.debug(" in generateProjectDefinitionRows() for row " + targetRowCount + " KALSM is "
									+ networkHeaderWBSReferenceTable.getProfid());
						} else if (TargetNetworkHeaderColumnHeaders.PRCTR == targetNetworkHeaderColumnHeader) {
							Cell cell = targetNetworkHeaderRow
									.createCell(targetNetworkHeaderColumnHeader.getColumnIndex() - 1);
							ETLUtil.setCellValue(cell, networkHeaderWBSReferenceTable.getPrctr(), logger);
							networkHeaderActivityReferenceTable.setPrctr(networkHeaderWBSReferenceTable.getPrctr());
							logger.debug(" in generateProjectDefinitionRows() for row " + targetRowCount + " PRCTR is "
									+ networkHeaderWBSReferenceTable.getPrctr());
						} else if (TargetNetworkHeaderColumnHeaders.ZSCHL == targetNetworkHeaderColumnHeader) {
							Cell cell = targetNetworkHeaderRow
									.createCell(targetNetworkHeaderColumnHeader.getColumnIndex() - 1);
							ETLUtil.setCellValue(cell, networkHeaderWBSReferenceTable.getZschl(), logger);
							networkHeaderActivityReferenceTable.setZschl(networkHeaderWBSReferenceTable.getZschl());
							logger.debug(" in generateProjectDefinitionRows() for row " + targetRowCount + " ZSCHL is "
									+ networkHeaderWBSReferenceTable.getProfid());
						} else if (TargetNetworkHeaderColumnHeaders.GSTRP == targetNetworkHeaderColumnHeader) {
							Cell cell = targetNetworkHeaderRow
									.createCell(targetNetworkHeaderColumnHeader.getColumnIndex() - 1);
							ETLUtil.setCellValue(cell, networkHeaderWBSReferenceTable.getGstrp(), logger);
							logger.debug(" in generateProjectDefinitionRows() for row " + targetRowCount + " ZSCHL is "
									+ networkHeaderWBSReferenceTable.getProfid());
						} else if (TargetNetworkHeaderColumnHeaders.GLTRP == targetNetworkHeaderColumnHeader) {
							Cell cell = targetNetworkHeaderRow
									.createCell(targetNetworkHeaderColumnHeader.getColumnIndex() - 1);
							ETLUtil.setCellValue(cell, networkHeaderWBSReferenceTable.getGltrp(), logger);
							logger.debug(" in generateProjectDefinitionRows() for row " + targetRowCount + " ZSCHL is "
									+ networkHeaderWBSReferenceTable.getProfid());
						} else if (destinationConstants.get(targetNetworkHeaderColumnHeader) != null) {
							Cell desCell = targetNetworkHeaderRow
									.createCell(targetNetworkHeaderColumnHeader.getColumnIndex() - 1);
							ETLUtil.setCellValue(desCell, destinationConstants.get(targetNetworkHeaderColumnHeader),
									logger);
							logger.debug("in generateWBStargetFile adding constant column  "
									+ targetNetworkHeaderColumnHeader.getColumnHeader()
									+ desCell.getStringCellValue().trim());
						}

					}
					targetRowCount++;
					ETLUtil.getNetworkHeaderActivityReferenceTableByProjectTaskNo().put(
							networkHeaderWBSReferenceTable.getProjectNo()
							+ networkHeaderWBSReferenceTable.getAltTaskNo(),
							networkHeaderActivityReferenceTable);
				}
			}
			FileOutputStream targetOutputStream = new FileOutputStream(destinationNetworkHeaderFile);
			targetNetworkHeaderWorkbook.write(targetOutputStream);
			targetOutputStream.close();


		} catch (Exception e) {
			logger.error(" in generateWBStargetFile() ", e);
			throw new Exception(e);
		}
		logger.debug("exiting generateWBStargetFile ");
	}

	public ArrayList<String> validateColumnHeaders(final Sheet sheet) throws Exception {
		ArrayList<String> headers = getColumnHeaders(sheet);
		SourceNetworkHeaderColumnHeaders[] sourceNetworkHeaderColumnHeaders = SourceNetworkHeaderColumnHeaders.values();
		for (SourceNetworkHeaderColumnHeaders sourceNetworkHeaderColumnHeader : sourceNetworkHeaderColumnHeaders) {
			if (!headers.contains(sourceNetworkHeaderColumnHeader.getColumnHeader())) {
				logger.error(" in validateColumnHeaders() ", new Exception(
						"Column " + sourceNetworkHeaderColumnHeader.getColumnHeader()
						+ " missing in source Network Header file."));
				throw new Exception(
						"Column " + sourceNetworkHeaderColumnHeader.getColumnHeader()
						+ " missing in source Network Header file.");
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
