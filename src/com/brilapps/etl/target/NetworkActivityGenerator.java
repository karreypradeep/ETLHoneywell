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
import com.brilapps.etl.ProjectConstants;
import com.brilapps.etl.source.SourceNetworkActivityColumnHeaders;
import com.brilapps.etl.source.SourceNetworkHeaderColumnHeaders;

public class NetworkActivityGenerator {
	static Logger logger = Logger.getLogger(NetworkActivityGenerator.class);


	public final static HashMap<TargetNetworkActivityColumnHeaders, String> destinationConstants = new HashMap<TargetNetworkActivityColumnHeaders, String>();
	private static List<SourceNetworkHeaderColumnHeaders> UNIQUE_KEYS_NETWORK_ACTIVITY = new ArrayList<SourceNetworkHeaderColumnHeaders>();

	static {
		UNIQUE_KEYS_NETWORK_ACTIVITY.add(SourceNetworkHeaderColumnHeaders.PROJECTNO);
		UNIQUE_KEYS_NETWORK_ACTIVITY.add(SourceNetworkHeaderColumnHeaders.ALT_TASKNO);
		UNIQUE_KEYS_NETWORK_ACTIVITY.add(SourceNetworkHeaderColumnHeaders.COST_TYPE);
	}

	static {

		// Constants Add all the constants columns here so that they will be
		// directly added to target WBS file.
		destinationConstants.put(TargetNetworkActivityColumnHeaders.DAUNE, "D");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.SLWID, "Z000003");

		// Blank Space Constants
		// Constants Add all the Blank Spaces columns here so that they will be
		// directly added to target WBS file.
		// Blank Space Constants
		destinationConstants.put(TargetNetworkActivityColumnHeaders.ARBEI, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.ARBEH, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.INDET, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.LARNT, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.NPRIO, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.MLSTN, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.CLASF, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.VERTL, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.DAUNO, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.PREIS, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.WAERS, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.LOSVG, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.LOSME, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.AUDISP_ACTIVITY, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.EKORG, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.EKGRP, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.MATKL, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.EINSA, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.NTANF, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.EINSE, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.NTEND, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.FRSP, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.AENNR, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.RFPNT, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.TXJCD_ACTIVITY, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.USR00, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.USR01, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.USR02, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.USR04, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.USE04, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.USR05, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.USE05, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.USR06, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.USE06, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.USR07, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.USE07, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.VERSN_EV, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.EVMET_TXT_P, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.EVMET_TXT_A, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.SWRT10, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.PRKST, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.ANFKO, "");
		destinationConstants.put(TargetNetworkActivityColumnHeaders.SWRT11, "");
	}

	public void writeHeaderColumns(final Sheet sheet) {
		logger.debug(" entering writeHeaderColumns() ");
		Row row = sheet.createRow(0);
		int colNum = 0;
		for (TargetNetworkActivityColumnHeaders field : TargetNetworkActivityColumnHeaders.getColumnHeadersByIndex()) {
			Cell cell = row.createCell(colNum++);
			cell.setCellValue(field.getColumnHeader());
		}
		logger.debug(" exiting writeHeaderColumns() ");
	}

	public ArrayList<String> getColumnHeaders(final Sheet sheet) {
		if (logger.isDebugEnabled()) {
			logger.debug(" entering getColumnHeaders() ");
		}
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

	public void deleteDuplicateRowsAndGenerateTargetForNetworkActivity(final Sheet sourceNetworkHeaderSheet,
			final Sheet destinationNetworkActivitySheet,
			final Map<String, Integer> networkHeaderSourceHeaderColumnIndexMap) {
		logger.debug(" entering deleteDuplicateRowsAndGenerateTargetForNetworkActivity ");
		ArrayList<String> headers = getColumnHeaders(sourceNetworkHeaderSheet);
		ArrayList<Integer> uniqueKeyIndexes = new ArrayList<Integer>();
		int indexCount = 0;
		int costTypeIndex = -1;
		for (String headerName : headers) {
			for (SourceNetworkHeaderColumnHeaders sourceNetworkHeaderColumnHeader : UNIQUE_KEYS_NETWORK_ACTIVITY) {
				if (headerName.equals(sourceNetworkHeaderColumnHeader.getColumnHeader())) {
					uniqueKeyIndexes.add(indexCount);
				}
				if (costTypeIndex != -1 && SourceNetworkHeaderColumnHeaders.COST_TYPE.toString()
						.equals(sourceNetworkHeaderColumnHeader.getColumnHeader())) {
					costTypeIndex = indexCount;
				}
			}
			indexCount++;
		}
		logger.debug(" in deleteDuplicateRowsAndGenerateTargetForNetworkActivity uniqueKeyIndexes " + uniqueKeyIndexes);
		Set<String> uniqueKeys = new HashSet<String>();
		Set<Integer> uniqueRows = new TreeSet<Integer>();

		// writeHeaderColumns
		Row networkActivityRow = destinationNetworkActivitySheet.createRow(0);
		int networkActivityColNum = 0;
		for (SourceNetworkActivityColumnHeaders header : SourceNetworkActivityColumnHeaders.getColumnHeadersByIndex()) {
			Cell cell = networkActivityRow.createCell(networkActivityColNum++);
			cell.setCellValue(header.getColumnHeader());
		}

		Iterator<Row> iterator = sourceNetworkHeaderSheet.iterator();
		iterator.next();
		while (iterator.hasNext()) {
			Row currentRow = iterator.next();
			StringBuffer uniqueKey = new StringBuffer("");
			for (Integer uniqueKeyIndex : uniqueKeyIndexes) {
				if (costTypeIndex == uniqueKeyIndex) {
					uniqueKey.append(ETLUtil.getCellValueAsString(currentRow.getCell(uniqueKeyIndex), logger));
				} else {
					uniqueKey.append(ETLUtil.getCellValueAsString(currentRow.getCell(uniqueKeyIndex), logger));
				}
			}
			if (!uniqueKeys.contains(uniqueKey.toString())) {
				logger.debug(" in deleteDuplicateRowsAndGenerateTargetForNetworkActivity adding unique key record "
						+ uniqueKey);
				uniqueKeys.add(uniqueKey.toString());
				uniqueRows.add(currentRow.getRowNum());
			}
		}

		logger.debug(" in deleteDuplicateRowsAndGenerateTargetForNetworkActivity entering uniqueRowsloop");

		List<String> projectTaskNos = new ArrayList<String>();
		List<String> projectAltTaskNosAndActDesc = new ArrayList<String>();

		for (Integer uniqueRow : uniqueRows) {
			logger.debug(" in deleteDuplicateRowsAndGenerateTargetForNetworkActivity processing uniqueRow with number "
					+ uniqueRow);
			Row currentRow = sourceNetworkHeaderSheet.getRow(uniqueRow);
			Cell costTypeCell = currentRow.getCell(
					networkHeaderSourceHeaderColumnIndexMap.get(SourceNetworkHeaderColumnHeaders.COST_TYPE.toString()));

			Object costType = ETLUtil.getCellValue(costTypeCell, logger);
			CostTypeReferenceTable costTypeReference = null;
			String projectNo = ETLUtil.getCellValueAsString(currentRow.getCell(
					networkHeaderSourceHeaderColumnIndexMap.get(SourceNetworkHeaderColumnHeaders.PROJECTNO.toString())),
					logger);
			String taskNo = ETLUtil.getCellValueAsString(currentRow.getCell(
					networkHeaderSourceHeaderColumnIndexMap.get(SourceNetworkHeaderColumnHeaders.TASKNO.toString())),
					logger);
			String altTaskNo = ETLUtil.getCellValueAsString(currentRow.getCell(networkHeaderSourceHeaderColumnIndexMap
					.get(SourceNetworkHeaderColumnHeaders.ALT_TASKNO.toString())), logger);

			if (costType != null) {
				costTypeReference = ETLUtil.getCostTypeReferenceTableByCostType().get(costType.toString());
				if (costTypeReference == null
						|| costTypeReference.getCostTypeActualDescription().toUpperCase()
						.equals(ProjectConstants.COST_TYPE_NA.toUpperCase())) {
					continue;
				}

				if (!projectAltTaskNosAndActDesc
						.contains(projectNo + altTaskNo + costTypeReference.getCostTypeActualDescription())) {
					projectAltTaskNosAndActDesc
					.add(projectNo + altTaskNo + costTypeReference.getCostTypeActualDescription());
				} else {
					continue;
				}
				// Check if for same project no and alt task no the
				// ActualDescription is added earlier if yes then do not add
				if (costTypeReference.getCostTypeActualDescription().toUpperCase()
						.equals(ProjectConstants.COST_TYPE_NA.toUpperCase())) {
					continue;
				}
			}

			// if there is no record in network header for the project no and
			// alt task no then skip the record in network activity.
			NetworkHeaderActivityReferenceTable networkHeaderActivityReferenceTable = ETLUtil
					.getNetworkHeaderActivityReferenceTableByProjectTaskNo().get(projectNo + altTaskNo);
			if (networkHeaderActivityReferenceTable == null) {
				continue;
			}
			NetworkActivityCostTypeReferenceTable networkActivityCostTypeReferenceTable = new NetworkActivityCostTypeReferenceTable();
			networkActivityCostTypeReferenceTable.setProjectNo(projectNo);
			networkActivityCostTypeReferenceTable.setTaskNo(taskNo);
			networkActivityCostTypeReferenceTable.setAltTaskNo(altTaskNo);
			networkActivityCostTypeReferenceTable
			.setCostType(ETLUtil.getCellValueAsString(currentRow
					.getCell(networkHeaderSourceHeaderColumnIndexMap
							.get(SourceNetworkHeaderColumnHeaders.COST_TYPE.toString())),
					logger));

			networkActivityCostTypeReferenceTable.setCostTypeDescription(costTypeReference.getCostTypeDescription());
			networkActivityCostTypeReferenceTable
			.setCostTypeActualDescription(costTypeReference.getCostTypeActualDescription());
			if (!projectTaskNos.contains(projectNo + altTaskNo)) {
				projectTaskNos.add(projectNo + altTaskNo);
			}
			if (ETLUtil.getNetworkActivityCostTypeTableListByProjectTaskNo().get(projectNo + altTaskNo) != null) {
				ETLUtil.getNetworkActivityCostTypeTableListByProjectTaskNo().get(projectNo + altTaskNo)
				.add(networkActivityCostTypeReferenceTable);
			} else {
				List<NetworkActivityCostTypeReferenceTable> networkHeaderActivityReferences = new ArrayList<NetworkActivityCostTypeReferenceTable>();
				networkHeaderActivityReferences.add(networkActivityCostTypeReferenceTable);
				ETLUtil.getNetworkActivityCostTypeTableListByProjectTaskNo().put(projectNo + altTaskNo,
						networkHeaderActivityReferences);
			}
		}
		for (String projectTaskNo : projectTaskNos) {
			List<NetworkActivityCostTypeReferenceTable> networkHeaderActivityReferences = ETLUtil
					.getNetworkActivityCostTypeTableListByProjectTaskNo().get(projectTaskNo);
			NetworkActivityCostTypeReferenceTable[] sortedNetworkHeaderActivityReferenceArray = new NetworkActivityCostTypeReferenceTable[6];
			for (NetworkActivityCostTypeReferenceTable networkHeaderActivityReferenceTable : networkHeaderActivityReferences) {
				if (networkHeaderActivityReferenceTable.getCostTypeActualDescription().toUpperCase()
						.equals(ProjectConstants.COST_TYPE_MATERIAL.toUpperCase())) {
					sortedNetworkHeaderActivityReferenceArray[0] = networkHeaderActivityReferenceTable;
				} else if (networkHeaderActivityReferenceTable.getCostTypeActualDescription().toUpperCase()
						.equals(ProjectConstants.COST_TYPE_LABOUR_ENG.toUpperCase())) {
					sortedNetworkHeaderActivityReferenceArray[1] = networkHeaderActivityReferenceTable;
				} else if (networkHeaderActivityReferenceTable.getCostTypeActualDescription().toUpperCase()
						.equals(ProjectConstants.COST_TYPE_LABOUR_MFG.toUpperCase())) {
					sortedNetworkHeaderActivityReferenceArray[2] = networkHeaderActivityReferenceTable;
				} else if (networkHeaderActivityReferenceTable.getCostTypeActualDescription().toUpperCase()
						.equals(ProjectConstants.COST_TYPE_ODC.toUpperCase())) {
					sortedNetworkHeaderActivityReferenceArray[3] = networkHeaderActivityReferenceTable;
				} else if (networkHeaderActivityReferenceTable.getCostTypeActualDescription().toUpperCase()
						.equals(ProjectConstants.COST_TYPE_TRAVEL.toUpperCase())) {
					sortedNetworkHeaderActivityReferenceArray[4] = networkHeaderActivityReferenceTable;
				} else if (networkHeaderActivityReferenceTable.getCostTypeActualDescription().toUpperCase()
						.equals(ProjectConstants.COST_TYPE_MGMT_RES.toUpperCase())) {
					sortedNetworkHeaderActivityReferenceArray[5] = networkHeaderActivityReferenceTable;
				}
			}
			List<NetworkActivityCostTypeReferenceTable> sortedNetworkHeaderActivityReferences = new ArrayList<NetworkActivityCostTypeReferenceTable>();
			for (NetworkActivityCostTypeReferenceTable networkHeaderActivityReferenceTable : sortedNetworkHeaderActivityReferenceArray) {
				if (networkHeaderActivityReferenceTable != null) {
					sortedNetworkHeaderActivityReferences.add(networkHeaderActivityReferenceTable);
				}
			}
			ETLUtil.getNetworkActivityCostTypeTableListByProjectTaskNo().put(projectTaskNo,
					sortedNetworkHeaderActivityReferences);
		}

		int rowCount = 1;

		for (String projectTaskNo : projectTaskNos) {

			List<NetworkActivityCostTypeReferenceTable> networkHeaderCostTypeReferences = ETLUtil
					.getNetworkActivityCostTypeTableListByProjectTaskNo().get(projectTaskNo);
			long vornr = 0;
			for (NetworkActivityCostTypeReferenceTable networkHeaderCostTypeReferenceTable : networkHeaderCostTypeReferences) {
				NetworkHeaderActivityReferenceTable networkHeaderActivityReferenceTable = ETLUtil
						.getNetworkHeaderActivityReferenceTableByProjectTaskNo()
						.get(networkHeaderCostTypeReferenceTable.getProjectNo()
								+ networkHeaderCostTypeReferenceTable.getAltTaskNo());
				if (networkHeaderActivityReferenceTable != null) {
					Row row = destinationNetworkActivitySheet.createRow(rowCount);

					Cell desCell = row.createCell(0);
					ETLUtil.setCellValue(desCell, networkHeaderCostTypeReferenceTable.getProjectNo(), logger);

					desCell = row.createCell(1);
					ETLUtil.setCellValue(desCell, networkHeaderCostTypeReferenceTable.getTaskNo(), logger);

					desCell = row.createCell(2);
					ETLUtil.setCellValue(desCell, networkHeaderCostTypeReferenceTable.getAltTaskNo(), logger);

					desCell = row.createCell(3);
					ETLUtil.setCellValue(desCell, networkHeaderActivityReferenceTable.getSerialNo(), logger);

					desCell = row.createCell(4);
					ETLUtil.setCellValue(desCell, networkHeaderCostTypeReferenceTable.getCostType(), logger);

					desCell = row.createCell(5);
					ETLUtil.setCellValue(desCell, networkHeaderCostTypeReferenceTable.getCostTypeDescription(), logger);

					desCell = row.createCell(6);
					ETLUtil.setCellValue(desCell, networkHeaderCostTypeReferenceTable.getCostTypeActualDescription(),
							logger);
					vornr = vornr + 10;
					desCell = row.createCell(7);
					ETLUtil.setCellValue(desCell, "00" + vornr, logger);

					rowCount++;
				}
			}
		}
		logger.debug(" exiting deleteDuplicateRowsAndGenerateTargetForNetworkActivity ");
	}

	public void generateNetworkActivityTargetFile(final File projectDefinitionDestinationFile,
			final File networkHeaderSourceFile,
			final File networkActivityNonDuplicateFile, final File destinationNetworkActivityFile) throws Exception {
		logger.debug("entering generateNetworkActivityTargetFile ");

		try {
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
			logger.debug("in generateNetworkActivityTargetFile before validateColumnHeaders");
			validateColumnHeaders(networkHeaderSourceSheet);
			logger.debug("in generateNetworkActivityTargetFile after validateColumnHeaders");

			// writeHeaderColumns
			int colNum = 0;
			Map<String, Integer> networkHeaderSourceHeaderColumnIndexMap = new HashMap<String, Integer>();
			for (String header : getColumnHeaders(networkHeaderSourceSheet)) {
				networkHeaderSourceHeaderColumnIndexMap.put(header, colNum);
				colNum++;
			}

			Workbook networkActivityNonDuplicateWorkbook = new HSSFWorkbook();
			Sheet networkActivityNonDuplicateTargetSheet = networkActivityNonDuplicateWorkbook
					.createSheet(networkHeaderSourceSheet.getSheetName());

			logger.debug("in generateNetworkActivityTargetFile before deleting duplicate records for network activity");
			deleteDuplicateRowsAndGenerateTargetForNetworkActivity(networkHeaderSourceSheet,
					networkActivityNonDuplicateTargetSheet,
					networkHeaderSourceHeaderColumnIndexMap);
			logger.debug("in generateNetworkActivityTargetFile after deleting duplicate records for network activity");
			FileOutputStream netWorkActivityOutputStream = new FileOutputStream(networkActivityNonDuplicateFile);
			networkActivityNonDuplicateWorkbook.write(netWorkActivityOutputStream);
			netWorkActivityOutputStream.close();
			networkHeaderSourceFileInputStream.close();

			// Generate Destination file
			Workbook targetNetworkTargetWorkbook = new HSSFWorkbook();
			Sheet targetNetworkTargetSheet = targetNetworkTargetWorkbook.createSheet("NetworkTargetLoader");
			writeHeaderColumns(targetNetworkTargetSheet);

			Sheet networkActivityNonDuplicateSheet = networkActivityNonDuplicateWorkbook.getSheetAt(0);

			Map<String, Integer> networkActivityNoDuplicateHeaderColumnIndexMap = new HashMap<String, Integer>();
			colNum = 0;
			for (String header : getColumnHeaders(networkActivityNonDuplicateSheet)) {
				networkActivityNoDuplicateHeaderColumnIndexMap.put(header, colNum);
				colNum++;
			}

			Iterator<Row> networkTargetNonDuplicateSheetIterator = networkActivityNonDuplicateSheet.iterator();
			// iterate header row firstand process remaining rows.
			networkTargetNonDuplicateSheetIterator.next();


			int targetRowCount = 1;
			while (networkTargetNonDuplicateSheetIterator.hasNext()) {
				Row networkTargetNonDuplicateCurrentRow = networkTargetNonDuplicateSheetIterator.next();

				String projectNo = ETLUtil.getCellValueAsString(networkTargetNonDuplicateCurrentRow
						.getCell(networkActivityNoDuplicateHeaderColumnIndexMap
								.get(SourceNetworkActivityColumnHeaders.PROJECTNO.toString())),
						logger);

				/*String taskNo = networkTargetNonDuplicateCurrentRow
						.getCell(networkActivityNoDuplicateHeaderColumnIndexMap
								.get(SourceNetworkActivityColumnHeaders.TASKNO.toString())).getStringCellValue();*/
				String altTaskNo = ETLUtil.getCellValueAsString(networkTargetNonDuplicateCurrentRow
						.getCell(networkActivityNoDuplicateHeaderColumnIndexMap
								.get(SourceNetworkActivityColumnHeaders.ALT_TASKNO.toString())),
						logger);

				NetworkHeaderActivityReferenceTable networkHeaderActivityReferenceTable = ETLUtil
						.getNetworkHeaderActivityReferenceTableByProjectTaskNo().get(projectNo + altTaskNo);

				Row targetNetworkActivityRow = targetNetworkTargetSheet.createRow(targetRowCount);
				for (TargetNetworkActivityColumnHeaders targetNetworkActivityColumnHeader : TargetNetworkActivityColumnHeaders
						.getColumnHeadersByIndex()) {
					String actualCostType = ETLUtil.getCellValueAsString(networkTargetNonDuplicateCurrentRow
							.getCell(networkActivityNoDuplicateHeaderColumnIndexMap
									.get(SourceNetworkActivityColumnHeaders.COST_TYPE_ACTUAL_DESCRIPTION.toString())),
							logger);

					if (TargetNetworkActivityColumnHeaders.SERIAL == targetNetworkActivityColumnHeader) {
						Cell cell = targetNetworkActivityRow
								.createCell(targetNetworkActivityColumnHeader.getColumnIndex() - 1);
						ETLUtil.setCellValue(cell, networkTargetNonDuplicateCurrentRow
								.getCell(networkActivityNoDuplicateHeaderColumnIndexMap
										.get(SourceNetworkActivityColumnHeaders.SERIAL_NO.toString()))
								.getNumericCellValue(), logger);
					} else if (TargetNetworkActivityColumnHeaders.VORNR == targetNetworkActivityColumnHeader) {
						Cell cell = targetNetworkActivityRow
								.createCell(targetNetworkActivityColumnHeader.getColumnIndex() - 1);
						ETLUtil.setCellValue(cell, ETLUtil.getCellValueAsString(
								networkTargetNonDuplicateCurrentRow
								.getCell(networkActivityNoDuplicateHeaderColumnIndexMap
										.get(SourceNetworkActivityColumnHeaders.VORNR.toString())),
								logger),
								logger);
					} else if (TargetNetworkActivityColumnHeaders.LTXA1 == targetNetworkActivityColumnHeader) {
						Cell cell = targetNetworkActivityRow
								.createCell(targetNetworkActivityColumnHeader.getColumnIndex() - 1);
						ETLUtil.setCellValue(cell, actualCostType, logger);
					} else if (TargetNetworkActivityColumnHeaders.PROJN_ACTIVITY == targetNetworkActivityColumnHeader) {
						Cell cell = targetNetworkActivityRow
								.createCell(targetNetworkActivityColumnHeader.getColumnIndex() - 1);
						ETLUtil.setCellValue(cell, networkHeaderActivityReferenceTable.getIdent(),
								logger);
					} else if (TargetNetworkActivityColumnHeaders.ACT_TYPE == targetNetworkActivityColumnHeader) {
						Cell cell = targetNetworkActivityRow
								.createCell(targetNetworkActivityColumnHeader.getColumnIndex() - 1);
						String actType = "C";
						if (actualCostType.toUpperCase().equals(ProjectConstants.COST_TYPE_LABOUR_ENG.toUpperCase())
								|| actualCostType.toUpperCase()
								.equals(ProjectConstants.COST_TYPE_LABOUR_MFG.toUpperCase())) {
							actType = "I";
						}
						ETLUtil.setCellValue(cell, actType, logger);
					} else if (TargetNetworkActivityColumnHeaders.STEUS == targetNetworkActivityColumnHeader) {
						Cell cell = targetNetworkActivityRow
								.createCell(targetNetworkActivityColumnHeader.getColumnIndex() - 1);
						String steus = "PS03";
						if (actualCostType.toUpperCase().equals(ProjectConstants.COST_TYPE_LABOUR_ENG.toUpperCase())
								|| actualCostType
								.toUpperCase().equals(ProjectConstants.COST_TYPE_LABOUR_MFG.toUpperCase())) {
							steus = "PS01";
						}
						ETLUtil.setCellValue(cell, steus, logger);
					} else if (TargetNetworkActivityColumnHeaders.ARBPL == targetNetworkActivityColumnHeader) {
						Cell cell = targetNetworkActivityRow
								.createCell(targetNetworkActivityColumnHeader.getColumnIndex() - 1);
						String arbpl = "";
						if (actualCostType.toUpperCase().equals(ProjectConstants.COST_TYPE_LABOUR_ENG.toUpperCase())) {
							arbpl = "TIMECHRG";
						} else if (actualCostType.toUpperCase()
								.equals(ProjectConstants.COST_TYPE_LABOUR_MFG.toUpperCase())) {
							arbpl = "ETPRZZXX";
						}
						ETLUtil.setCellValue(cell, arbpl, logger);
					} else if (TargetNetworkActivityColumnHeaders.WERKS_ACTIVITY == targetNetworkActivityColumnHeader) {
						Cell cell = targetNetworkActivityRow
								.createCell(targetNetworkActivityColumnHeader.getColumnIndex() - 1);
						ETLUtil.setCellValue(cell, networkHeaderActivityReferenceTable.getWerks(), logger);
					} else if (TargetNetworkActivityColumnHeaders.KALID == targetNetworkActivityColumnHeader) {
						Cell cell = targetNetworkActivityRow
								.createCell(targetNetworkActivityColumnHeader.getColumnIndex() - 1);
						ETLUtil.setCellValue(cell, networkHeaderActivityReferenceTable.getKalid(), logger);
					} else if (TargetNetworkActivityColumnHeaders.PRCTR_ACTIVITY == targetNetworkActivityColumnHeader) {
						Cell cell = targetNetworkActivityRow
								.createCell(targetNetworkActivityColumnHeader.getColumnIndex() - 1);
						ETLUtil.setCellValue(cell, networkHeaderActivityReferenceTable.getPrctr(), logger);
					} else if (TargetNetworkActivityColumnHeaders.KALSM_ACTIVITY == targetNetworkActivityColumnHeader) {
						Cell cell = targetNetworkActivityRow
								.createCell(targetNetworkActivityColumnHeader.getColumnIndex() - 1);
						ETLUtil.setCellValue(cell, networkHeaderActivityReferenceTable.getKalsm(), logger);
					} else if (TargetNetworkActivityColumnHeaders.ZSCHL_ACTIVITY == targetNetworkActivityColumnHeader) {
						Cell cell = targetNetworkActivityRow
								.createCell(targetNetworkActivityColumnHeader.getColumnIndex() - 1);
						ETLUtil.setCellValue(cell, networkHeaderActivityReferenceTable.getZschl(), logger);
					} else if (TargetNetworkActivityColumnHeaders.SCOPE_ACTIVITY == targetNetworkActivityColumnHeader) {
						Cell cell = targetNetworkActivityRow
								.createCell(targetNetworkActivityColumnHeader.getColumnIndex() - 1);
						ETLUtil.setCellValue(cell, networkHeaderActivityReferenceTable.getScope(), logger);
					} else if (TargetNetworkActivityColumnHeaders.USR03 == targetNetworkActivityColumnHeader) {
						Cell cell = targetNetworkActivityRow
								.createCell(targetNetworkActivityColumnHeader.getColumnIndex() - 1);
						ETLUtil.setCellValue(cell, networkHeaderActivityReferenceTable.getUser03(), logger);
					} else if (TargetNetworkActivityColumnHeaders.SAKTO == targetNetworkActivityColumnHeader) {
						Cell cell = targetNetworkActivityRow
								.createCell(targetNetworkActivityColumnHeader.getColumnIndex() - 1);
						String sakto = "";
						if (actualCostType.toUpperCase().equals(ProjectConstants.COST_TYPE_MATERIAL.toUpperCase())) {
							sakto = "9000000000";
						} else if (actualCostType.toUpperCase().equals(ProjectConstants.COST_TYPE_ODC.toUpperCase())) {
							sakto = "7780250000";
						} else if (actualCostType.toUpperCase()
								.equals(ProjectConstants.COST_TYPE_TRAVEL.toUpperCase())) {
							sakto = "7350000000";
						} else if (actualCostType.toUpperCase()
								.equals(ProjectConstants.COST_TYPE_MGMT_RES.toUpperCase())) {
							sakto = "9000000001";
						}
						ETLUtil.setCellValue(cell, sakto, logger);
					} else if (destinationConstants.get(targetNetworkActivityColumnHeader) != null) {
						Cell desCell = targetNetworkActivityRow
								.createCell(targetNetworkActivityColumnHeader.getColumnIndex() - 1);
						ETLUtil.setCellValue(desCell, destinationConstants.get(targetNetworkActivityColumnHeader),
								logger);
						logger.debug("in generateWBStargetFile adding constant column  "
								+ targetNetworkActivityColumnHeader.getColumnHeader()
								+ ETLUtil.getCellValueAsString(desCell, logger));
					}
				}
				targetRowCount++;
			}
			FileOutputStream targetOutputStream = new FileOutputStream(destinationNetworkActivityFile);
			targetNetworkTargetWorkbook.write(targetOutputStream);
			targetOutputStream.close();

		} catch (Exception e) {
			logger.error(" in generateNetworkActivityTargetFile() ", e);
			throw new Exception(e);
		}
		logger.debug("exiting generateNetworkActivityTargetFile ");
	}

	public ArrayList<String> validateColumnHeaders(final Sheet sheet) throws Exception {
		ArrayList<String> headers = getColumnHeaders(sheet);
		SourceNetworkHeaderColumnHeaders[] sourceNetworkHeaderColumnHeaders = SourceNetworkHeaderColumnHeaders.values();
		for (SourceNetworkHeaderColumnHeaders sourceNetworkHeaderColumnHeader : sourceNetworkHeaderColumnHeaders) {
			if (!headers.contains(sourceNetworkHeaderColumnHeader.getColumnHeader())) {
				logger.error(" in validateColumnHeaders() ",
						new Exception("Column " + sourceNetworkHeaderColumnHeader.getColumnHeader()
						+ " missing in source Network Header file."));
				throw new Exception("Column " + sourceNetworkHeaderColumnHeader.getColumnHeader()
				+ " missing in source Network Header file.");
			}
		}
		return headers;
	}

}
