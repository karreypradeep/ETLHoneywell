package com.brilapps.etl.target;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.brilapps.etl.ProjectType;
import com.brilapps.etl.source.ProjectDefinitionExtractor;
import com.brilapps.etl.source.SourceProjectDefinitionColumnHeaders;

public class TargetNetworkHeaderGenerator {
	static Logger logger = Logger.getLogger(TargetNetworkHeaderGenerator.class);

	public final static String EC_PSPID_PREFIX = "EC-";
	public final static long EC_START_SERIAL_NUMBER = 101;
	private File sourceProjectDefinitionFile;
	private File sourceWBSFile;

	public final static HashMap<TargetProjectDefinitionColumnHeaders, SourceProjectDefinitionColumnHeaders> destinationSourceCloumnMaps = new HashMap<TargetProjectDefinitionColumnHeaders, SourceProjectDefinitionColumnHeaders>();
	public final static HashMap<TargetNetworkHeaderColumnHeaders, String> destinationConstants = new HashMap<TargetNetworkHeaderColumnHeaders, String>();
	//public final static HashMap<String, ReferenceTable> referenceTableMap = new HashMap<String, ReferenceTable>();

	static {
		// Map Destination Project Definition file columns headers with Target
		// Project Definition file. This will be used later to get the values
		// from source file and insert into destination file.
		destinationSourceCloumnMaps.put(TargetProjectDefinitionColumnHeaders.PROFL,
				SourceProjectDefinitionColumnHeaders.PROJECT_PROFILE);
		destinationSourceCloumnMaps.put(TargetProjectDefinitionColumnHeaders.POST1,
				SourceProjectDefinitionColumnHeaders.DESCRIPTION);
		destinationSourceCloumnMaps.put(TargetProjectDefinitionColumnHeaders.PLFAZ,
				SourceProjectDefinitionColumnHeaders.START_DATE);
		destinationSourceCloumnMaps.put(TargetProjectDefinitionColumnHeaders.PLSEZ,
				SourceProjectDefinitionColumnHeaders.STOP_DATE);
		destinationSourceCloumnMaps.put(TargetProjectDefinitionColumnHeaders.SPROG,
				SourceProjectDefinitionColumnHeaders.START_DATE);
		destinationSourceCloumnMaps.put(TargetProjectDefinitionColumnHeaders.EPROG,
				SourceProjectDefinitionColumnHeaders.STOP_DATE);
		destinationSourceCloumnMaps.put(TargetProjectDefinitionColumnHeaders.VBUKR,
				SourceProjectDefinitionColumnHeaders.COMPANY_CODE);
		destinationSourceCloumnMaps.put(TargetProjectDefinitionColumnHeaders.WERKS,
				SourceProjectDefinitionColumnHeaders.COMPANY_CODE);
		destinationSourceCloumnMaps.put(TargetProjectDefinitionColumnHeaders.VKORG,
				SourceProjectDefinitionColumnHeaders.COMPANY_CODE);
		destinationSourceCloumnMaps.put(TargetProjectDefinitionColumnHeaders.PRCTR,
				SourceProjectDefinitionColumnHeaders.PROFIT_CENTER);
		destinationSourceCloumnMaps.put(TargetProjectDefinitionColumnHeaders.PROJECTNO,
				SourceProjectDefinitionColumnHeaders.PROJECTNO);

		// Constants Add all the constants columns here so that they will be
		// directly added to target project definition file.
		destinationConstants.put(TargetNetworkHeaderColumnHeaders.DISPO, "001");
		destinationConstants.put(TargetNetworkHeaderColumnHeaders.TERKZ, "1");
		destinationConstants.put(TargetNetworkHeaderColumnHeaders.KLVARP, "PS02");
		destinationConstants.put(TargetNetworkHeaderColumnHeaders.KLVARI, "PS03");
		destinationConstants.put(TargetNetworkHeaderColumnHeaders.NW_PLANCOST, "0");
		destinationConstants.put(TargetNetworkHeaderColumnHeaders.AUDISP, "1");

		// Constants Add all the Blank Spaces columns here so that they will be
		// directly added to target project definition file.
		// Blank Space Constants
		destinationConstants.put(TargetNetworkHeaderColumnHeaders.APRIO, "");
		destinationConstants.put(TargetNetworkHeaderColumnHeaders.DISPO_CO, "");
		destinationConstants.put(TargetNetworkHeaderColumnHeaders.KOSTV, "");
		destinationConstants.put(TargetNetworkHeaderColumnHeaders.PLGRP, "");
		destinationConstants.put(TargetNetworkHeaderColumnHeaders.TXJCD, "");
		destinationConstants.put(TargetNetworkHeaderColumnHeaders.GLTRP, "");
		destinationConstants.put(TargetNetworkHeaderColumnHeaders.GSTRP, "");


		// We can use reference table and insert reference columns in the
		// destination project definition file.
		/*ReferenceTable referenceTable = new ReferenceTable();
		referenceTable
		.setReferenceColumnHeader(ExtractorProjectDefinitionColumnHeaders.PROJECT_PREFIX.getColumnHeader());
		referenceTable.setReferenceColumnValue("EC");
		referenceTable.getColumnValues().put(GeneratorProjectDefinitionColumnHeaders.VPROF, "Z000003");
		referenceTable.getColumnValues().put(GeneratorProjectDefinitionColumnHeaders.SCOPE, "PROFIT");
		referenceTable.getColumnValues().put(GeneratorProjectDefinitionColumnHeaders.VTWEG, "01");
		referenceTableMap.put("EC", referenceTable);*/
		// Validations for start date and end date

	}

	/**
	 * Generate target Project Definition file from the source file.
	 *
	 * @param sourceProjectDefinitionFile
	 *            source file from which target will be generated.
	 * @param destinationProjectDefinitionFile
	 *            destination file to which target file will be generated.
	 * @throws Exception
	 */
	public void generateProjectDefinitionTargetWorkbook(final File sourceProjectDefinitionFile,
			final File destinationProjectDefinitionFile, final File destinationWBSElementUserField1File,
			final File destinationWBSElementUserField2File)
					throws Exception {
		logger.debug(" entering generateProjectDefinitionTargetWorkbook() ");
		try {
			// Create target project definition work book to export.
			Workbook targetProjectDefinitionWorkbook = new HSSFWorkbook();
			// Create sheet in target project definition work book to export.
			Sheet targetSheet = targetProjectDefinitionWorkbook.createSheet("LoaderProjectHeader");
			writeHeaderColumns(targetSheet);

			// Create target project definition work book to export.
			Workbook targetWBSElementUserField1Workbook = new HSSFWorkbook();
			// Create sheet in target project definition work book to export.
			Sheet targetWBSElementUserField1Sheet = targetWBSElementUserField1Workbook
					.createSheet("LoaderWBSElementUserField1");
			WBSElementUserField1Generator.writeHeaderColumns(targetWBSElementUserField1Sheet);

			// Create target project definition work book to export.
			Workbook targetWBSElementUserField2Workbook = new HSSFWorkbook();
			// Create sheet in target project definition work book to export.
			Sheet targetWBSElementUserField2Sheet = targetWBSElementUserField2Workbook
					.createSheet("LoaderWBSElementUserField2");
			WBSElementUserField2Generator.writeHeaderColumns(targetWBSElementUserField2Sheet);

			logger.debug(" in generateProjectDefinitionTargetWorkbook() created target project definition sheet");

			// File input stream to read source project definition file.
			FileInputStream fileInputStream = new FileInputStream(sourceProjectDefinitionFile);
			// Create Workbook instance holding reference to excel file
			Workbook extractorWorkbook = null;
			if (sourceProjectDefinitionFile.getName().endsWith(".xls")) {
				extractorWorkbook = new HSSFWorkbook(fileInputStream);
			} else {
				extractorWorkbook = new XSSFWorkbook(fileInputStream);
			}
			// Create sheet in source project definition work book.
			Sheet sourceSheet = extractorWorkbook.getSheetAt(0);
			ProjectDefinitionExtractor eCExtractor = new ProjectDefinitionExtractor();
			// Validate column headers in source project definition file. If any
			// column in missing then exception is thrown.
			eCExtractor.validateColumnHeaders(sourceSheet);

			int rowCount = 0;
			// Get all the project types and generate rows in destination file.
			for (ProjectType projectType : ProjectType.getColumnHeadersByProjectTypeSerialNo()) {
				logger.debug(" in generateProjectDefinitionTargetWorkbook() processing for project type "
						+ projectType.getProjectPrefix());
				rowCount = generateProjectDefinitionRows(projectType, sourceSheet, targetSheet,
						targetWBSElementUserField1Sheet, targetWBSElementUserField2Sheet, rowCount);
			}
			// Write the destination file into excel.
			FileOutputStream outputStream = new FileOutputStream(destinationProjectDefinitionFile);
			targetProjectDefinitionWorkbook.write(outputStream);
			outputStream.close();

			// Write the destination file into excel.
			FileOutputStream outputStreamWBSElementUserField1 = new FileOutputStream(
					destinationWBSElementUserField1File);
			targetWBSElementUserField1Workbook.write(outputStreamWBSElementUserField1);
			outputStreamWBSElementUserField1.close();

			// Write the destination file into excel.
			FileOutputStream outputStreamWBSElementUserField2 = new FileOutputStream(
					destinationWBSElementUserField2File);
			targetWBSElementUserField2Workbook.write(outputStreamWBSElementUserField2);
			outputStreamWBSElementUserField2.close();

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

	public void writeHeaderColumns(final Sheet sheet) {
		logger.debug(" entering writeHeaderColumns() ");
		Row row = sheet.createRow(0);
		int colNum = 0;
		for (TargetProjectDefinitionColumnHeaders field : TargetProjectDefinitionColumnHeaders
				.getColumnHeadersByIndex()) {
			Cell cell = row.createCell(colNum++);
			cell.setCellValue(field.getColumnHeader());
			logger.debug(" in writeHeaderColumns() : writing column header to target project definition file - "
					+ field.getColumnHeader());
		}
		logger.debug(" exiting writeHeaderColumns() ");
	}

	/**
	 * @return the sourceProjectDefinitionFile
	 */
	public File getSourceProjectDefinitionFile() {
		return sourceProjectDefinitionFile;
	}

	/**
	 * @param sourceProjectDefinitionFile
	 *            the sourceProjectDefinitionFile to set
	 */
	public void setSourceProjectDefinitionFile(final File sourceProjectDefinitionFile) {
		this.sourceProjectDefinitionFile = sourceProjectDefinitionFile;
	}

	/**
	 * @return the sourceWBSFile
	 */
	public File getSourceWBSFile() {
		return sourceWBSFile;
	}

	/**
	 * @param sourceWBSFile
	 *            the sourceWBSFile to set
	 */
	public void setSourceWBSFile(final File sourceWBSFile) {
		this.sourceWBSFile = sourceWBSFile;
	}

	private int generateProjectDefinitionRows(final ProjectType projectType, final Sheet sourceSheet,
			final Sheet targetSheet, final Sheet targetWBSElementUserField1Sheet,
			final Sheet targetWBSElementUserField2Sheet, final int rowCount) throws Exception {
		logger.debug(" entering generateProjectDefinitionRows()");
		int count = rowCount;
		ProjectDefinitionExtractor eCExtractor = new ProjectDefinitionExtractor();

		logger.debug(" in generateProjectDefinitionRows() before extracting all project rows");
		ArrayList<Row> projectTypeRows = eCExtractor.getAllProjectRows(sourceSheet, projectType.getProjectPrefix());
		logger.debug(" in generateProjectDefinitionRows() after extracting all project rows");
		ArrayList<String> extractorHeaders = eCExtractor.getColumnHeaders(sourceSheet);
		logger.debug(" in generateProjectDefinitionRows() after extracting all column headers");

		// Get the source column index from source file and store
		HashMap<SourceProjectDefinitionColumnHeaders, Integer> sourceColumnHeadersIndexMap = new HashMap<SourceProjectDefinitionColumnHeaders, Integer>();

		for (SourceProjectDefinitionColumnHeaders value : destinationSourceCloumnMaps.values()) {
			int headerColumnIndex = 0;
			for (String extractorHeader : extractorHeaders) {
				if (extractorHeader.equals(value.getColumnHeader())) {
					sourceColumnHeadersIndexMap.put(value, headerColumnIndex);
					break;
				}
				headerColumnIndex++;
			}
		}

		int sourceProjectDefinitionKeyCodeIndex = 0;
		for (String extractorHeader : extractorHeaders) {
			if (extractorHeader.equals("KEY CODE")) {
				break;
			}
			sourceProjectDefinitionKeyCodeIndex++;
		}
		logger.debug(" in generateProjectDefinitionRows() after generating sourceColumnHeadersIndexMap");

		int pspidStartIndex = 0;
		for (Row projectTypeRow : projectTypeRows) {
			int colNum = 0;
			Row row = targetSheet.createRow(count + 1);
			logger.debug(" in generateProjectDefinitionRows() created row " + (count + 1));
			String pspid = "";
			String keyCode = projectTypeRow.getCell(sourceProjectDefinitionKeyCodeIndex).getStringCellValue();
			for (TargetProjectDefinitionColumnHeaders destinationHeader : TargetProjectDefinitionColumnHeaders
					.getColumnHeadersByIndex()) {
				// Serial Column
				if (colNum == 0) {
					Cell cell = row.createCell(colNum);
					cell.setCellValue(EC_START_SERIAL_NUMBER + count);
					logger.debug(" in generateProjectDefinitionRows() for row " + (count + 1)
							+ " serial no generated is " + (EC_START_SERIAL_NUMBER + count));
				}
				// PSPID Column
				if (colNum == 1) {
					pspid = projectType.getPSPID_PREFIX() + (projectType.getPspidStartIndex() + pspidStartIndex);
					Cell cell = row.createCell(colNum);
					cell.setCellValue(pspid);
					logger.debug(" in generateProjectDefinitionRows() for row " + (count + 1) + " PSPID generated is "
							+ pspid);
				}
				// IF the cell has to be populated from source file then get
				// the column header and get the value from the extractor
				// row
				if (destinationSourceCloumnMaps.get(destinationHeader) != null) {
					logger.debug(" in generateProjectDefinitionRows() for row " + (count + 1)
							+ " processing for column " + destinationHeader.getColumnHeader());

					Cell currentCell = projectTypeRow.getCell(
							sourceColumnHeadersIndexMap.get(destinationSourceCloumnMaps.get(destinationHeader)));
					Cell desCell = row.createCell(colNum);
					if (currentCell.getCellType() == Cell.CELL_TYPE_BLANK) {
						desCell.setCellValue("");
						logger.debug(" in generateProjectDefinitionRows() blank cell");
					} else if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {
						desCell.setCellValue(currentCell.getStringCellValue());
						logger.debug(" in generateProjectDefinitionRows()  string value "
								+ currentCell.getStringCellValue());
					} else if (currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
						if (DateUtil.isCellDateFormatted(currentCell)) {
							if (currentCell.getDateCellValue() != null) {
								String dateValue = new SimpleDateFormat("MM/dd/yyyy")
										.format(currentCell.getDateCellValue());
								desCell.setCellValue(dateValue);
								logger.debug(" in generateProjectDefinitionRows()  date value " + dateValue);
							}
						} else {
							desCell.setCellValue(currentCell.getNumericCellValue());
							logger.debug(" in generateProjectDefinitionRows()  number value "
									+ currentCell.getNumericCellValue());
						}

					} else if (currentCell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
						desCell.setCellValue(currentCell.getBooleanCellValue());
						logger.debug(" in generateProjectDefinitionRows()  boolean value "
								+ currentCell.getBooleanCellValue());
					}

					if (TargetProjectDefinitionColumnHeaders.POST1 == destinationHeader) {
						Cell projectNoCell = projectTypeRow.getCell(
								sourceColumnHeadersIndexMap.get(SourceProjectDefinitionColumnHeaders.PROJECTNO));
						desCell.setCellValue(
								currentCell.getStringCellValue().trim() + " - " + projectNoCell.getStringCellValue());
						logger.debug(" in generateProjectDefinitionRows()  string value for description field POST1 "
								+ currentCell.getStringCellValue().trim() + " - " + projectNoCell.getStringCellValue());
					}
				}

				if (destinationConstants.get(destinationHeader) != null) {
					Cell desCell = row.createCell(colNum);
					desCell.setCellValue(destinationConstants.get(destinationHeader));
					logger.debug(" in generateProjectDefinitionRows()  constant value "
							+ destinationConstants.get(destinationHeader));
				}

				if (projectType.getReferenceColumnValue(destinationHeader) != null) {
					Cell desCell = row.createCell(colNum);
					desCell.setCellValue(projectType.getReferenceColumnValue(destinationHeader));
					logger.debug(" in generateProjectDefinitionRows()  reference column value "
							+ projectType.getReferenceColumnValue(destinationHeader));
				}

				if (TargetProjectDefinitionColumnHeaders.PROJECT_TYPE == destinationHeader) {
					Cell desCell = row.createCell(colNum);
					desCell.setCellValue(projectType.getProjectPrefix());
					logger.debug(" in generateProjectDefinitionRows()  PROJECT_TYPE "
							+ projectType.getProjectPrefix());
				}
				/*if (referenceTableMap.get("EC") != null) {
					ReferenceTable referenceTable = referenceTableMap.get(projectType);
					for (Map.Entry<GeneratorProjectDefinitionColumnHeaders, String> entry : referenceTable
							.getColumnValues().entrySet()) {
						if (entry.getKey().equals(destinationHeader)) {
							Cell desCell = row.createCell(colNum);
							desCell.setCellValue(entry.getValue());
							break;
						}
					}
				}*/
				colNum++;
			}
			WBSElementUserField1Generator.generateWBSElementUserField1TargetFile(targetWBSElementUserField1Sheet,
					count + 1, pspid, projectType.getProjectPrefix(), keyCode);
			WBSElementUserField2Generator.generateWBSElementUserField2TargetFile(targetWBSElementUserField2Sheet,
					count + 1, pspid, projectType.getProjectPrefix());
			count++;
			pspidStartIndex++;
		}
		logger.debug(" exiting generateProjectDefinitionRows() ");
		return count;
	}

}
