package com.brilapps.etl.target;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.brilapps.etl.ETLUtil;
import com.brilapps.etl.ProjectDefinitionReferenceTable;
import com.brilapps.etl.ProjectType;
import com.brilapps.etl.source.ProjectDefinitionExtractor;
import com.brilapps.etl.source.SourceProjectDefinitionColumnHeaders;

public class ProjectDefinitionGenerator {
	static Logger logger = Logger.getLogger(ProjectDefinitionGenerator.class);

	private File sourceProjectDefinitionFile;
	private File sourceWBSFile;

	public final static HashMap<TargetProjectDefinitionColumnHeaders, SourceProjectDefinitionColumnHeaders> destinationSourceCloumnMaps = new HashMap<TargetProjectDefinitionColumnHeaders, SourceProjectDefinitionColumnHeaders>();
	public final static HashMap<TargetProjectDefinitionColumnHeaders, Object> destinationConstants = new HashMap<TargetProjectDefinitionColumnHeaders, Object>();
	//public final static HashMap<String, ReferenceTable> referenceTableMap = new HashMap<String, ReferenceTable>();

	static {
		// Map Destination Project Definition file columns headers with Target
		// Project Definition file. This will be used later to get the values
		// from source file and insert into destination file.
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
		// destinationSourceCloumnMaps.put(TargetProjectDefinitionColumnHeaders.VKORG,
		// SourceProjectDefinitionColumnHeaders.COMPANY_CODE);
		destinationSourceCloumnMaps.put(TargetProjectDefinitionColumnHeaders.PRCTR,
				SourceProjectDefinitionColumnHeaders.PROFIT_CENTER);
		destinationSourceCloumnMaps.put(TargetProjectDefinitionColumnHeaders.PROJECTNO,
				SourceProjectDefinitionColumnHeaders.PROJECTNO);
		destinationSourceCloumnMaps.put(TargetProjectDefinitionColumnHeaders.VERNR,
				SourceProjectDefinitionColumnHeaders.SAP_EMPLOYEE_NUMBER);
		destinationSourceCloumnMaps.put(TargetProjectDefinitionColumnHeaders.PD_REVENUE,
				SourceProjectDefinitionColumnHeaders.REVENUE);

		// Constants Add all the constants columns here so that they will be
		// directly added to target project definition file.
		destinationConstants.put(TargetProjectDefinitionColumnHeaders.KIMSK, "");
		// destinationConstants.put(TargetProjectDefinitionColumnHeaders.VERNR,
		// 999999);
		destinationConstants.put(TargetProjectDefinitionColumnHeaders.VKORG, "");
		destinationConstants.put(TargetProjectDefinitionColumnHeaders.VTWEG, "");
		destinationConstants.put(TargetProjectDefinitionColumnHeaders.ZTEHT, "D");
		destinationConstants.put(TargetProjectDefinitionColumnHeaders.VKOKR, "AERO");
		destinationConstants.put(TargetProjectDefinitionColumnHeaders.PPROF, "Z00001");
		destinationConstants.put(TargetProjectDefinitionColumnHeaders.BPROF, "");
		destinationConstants.put(TargetProjectDefinitionColumnHeaders.SMPRF, "0000001");
		destinationConstants.put(TargetProjectDefinitionColumnHeaders.STSPR, "ZPS00001");
		destinationConstants.put(TargetProjectDefinitionColumnHeaders.BESTA, "X");

		// Constants Add all the Blank Spaces columns here so that they will be
		// directly added to target project definition file.
		// Blank Space Constants
		destinationConstants.put(TargetProjectDefinitionColumnHeaders.VGSBR, "");
		destinationConstants.put(TargetProjectDefinitionColumnHeaders.FUNC_AREA, "");
		destinationConstants.put(TargetProjectDefinitionColumnHeaders.ZSCHM, "");
		destinationConstants.put(TargetProjectDefinitionColumnHeaders.IMPRF, "");
		destinationConstants.put(TargetProjectDefinitionColumnHeaders.ABGSL, "");
		destinationConstants.put(TargetProjectDefinitionColumnHeaders.PARGR, "");
		destinationConstants.put(TargetProjectDefinitionColumnHeaders.SCHTYP, "");
		destinationConstants.put(TargetProjectDefinitionColumnHeaders.XSTAT, "");
		destinationConstants.put(TargetProjectDefinitionColumnHeaders.PLINT, "");
		destinationConstants.put(TargetProjectDefinitionColumnHeaders.TXJCD, "");
		destinationConstants.put(TargetProjectDefinitionColumnHeaders.SPART, "");
		destinationConstants.put(TargetProjectDefinitionColumnHeaders.DPPPROF, "");


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
		ProjectDefinitionReferenceTable projectDefinitionReferenceTable = ETLUtil
				.getProjectDefinitionReferenceTableByProjectPrefix().get(projectType.getProjectPrefix());
		for (Row projectTypeRow : projectTypeRows) {
			int colNum = 0;
			long serialNo = 0;
			Row row = targetSheet.createRow(count + 1);
			logger.debug(" in generateProjectDefinitionRows() created row " + (count + 1));
			String pspid = "", projectNo = "";

			String keyCode = ETLUtil.getCellValueAsString(projectTypeRow.getCell(sourceProjectDefinitionKeyCodeIndex),
					logger);
			for (TargetProjectDefinitionColumnHeaders destinationHeader : TargetProjectDefinitionColumnHeaders
					.getColumnHeadersByIndex()) {
				// Serial Column
				if (colNum == 0) {
					Cell cell = row.createCell(colNum);
					serialNo = projectDefinitionReferenceTable.getSerialNoStartIndex() + count;
					ETLUtil.setCellValue(cell, serialNo, logger);
				}
				// PSPID Column
				if (colNum == 1) {
					pspid = projectDefinitionReferenceTable.getPspidPrefix()
							+ (projectDefinitionReferenceTable.getPspidStartIndex() + pspidStartIndex);
					Cell cell = row.createCell(colNum);
					ETLUtil.setCellValue(cell, pspid, logger);
				}

				if (TargetProjectDefinitionColumnHeaders.PROFL == destinationHeader) {
					Cell cell = row.createCell(colNum);
					ETLUtil.setCellValue(cell, projectDefinitionReferenceTable.getProjectProfile(), logger);
				}

				if (TargetProjectDefinitionColumnHeaders.KALID == destinationHeader) {
					Cell companyCodeNoCell = projectTypeRow.getCell(
							sourceColumnHeadersIndexMap.get(SourceProjectDefinitionColumnHeaders.COMPANY_CODE));
					Object cellValue = ETLUtil.getCellValue(companyCodeNoCell, logger);
					Cell desCell = row.createCell(colNum);
					int companyCode = 0;
					if (cellValue instanceof String) {
						companyCode = Integer.valueOf((String) cellValue);
					} else if (cellValue instanceof Double) {
						companyCode = (Integer) cellValue;
					}
					if (cellValue != null && companyCode == 2040) {
						ETLUtil.setCellValue(desCell, "X8", logger);
					} else if (cellValue != null && companyCode == 3040) {
						ETLUtil.setCellValue(desCell, "XB", logger);
					} else {
						ETLUtil.setCellValue(desCell, "", logger);
					}
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
					Object cellValue = "";
					if (TargetProjectDefinitionColumnHeaders.POST1 == destinationHeader) {
						Cell projectNoCell = projectTypeRow.getCell(
								sourceColumnHeadersIndexMap.get(SourceProjectDefinitionColumnHeaders.PROJECTNO));
						projectNo = ETLUtil.getCellValueAsString(projectNoCell, logger);
						cellValue = ETLUtil.getCellValueAsString(projectNoCell, logger) + " - "
								+ ETLUtil.getCellValueAsString(currentCell, logger);
					} else {
						cellValue = ETLUtil.getCellValue(currentCell, logger);
					}
					ETLUtil.setCellValue(desCell, cellValue, logger);
				}

				if (destinationConstants.get(destinationHeader) != null) {
					Cell desCell = row.createCell(colNum);
					ETLUtil.setCellValue(desCell, destinationConstants.get(destinationHeader), logger);
				}

				if (TargetProjectDefinitionColumnHeaders.PROJECT_TYPE == destinationHeader) {
					Cell desCell = row.createCell(colNum);
					ETLUtil.setCellValue(desCell, projectDefinitionReferenceTable.getProjectPrefix(), logger);
				}
				if (TargetProjectDefinitionColumnHeaders.SCOPE == destinationHeader) {
					Cell desCell = row.createCell(colNum);
					ETLUtil.setCellValue(desCell, projectDefinitionReferenceTable.getScope(), logger);
				}
				if (TargetProjectDefinitionColumnHeaders.VPROF == destinationHeader) {
					Cell desCell = row.createCell(colNum);
					ETLUtil.setCellValue(desCell, projectDefinitionReferenceTable.getNetworkProfile(), logger);
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

			// For each record create LegacyProjectDefinitionReferenceTable and
			// store in map which can be used later while generating WBS target
			// file
			LegacyProjectDefinitionReferenceTable legacyProjectDefinitionReferenceTable = new LegacyProjectDefinitionReferenceTable();
			legacyProjectDefinitionReferenceTable.setProjectNo(projectNo);
			legacyProjectDefinitionReferenceTable.setProjectType(projectType.getProjectPrefix());
			legacyProjectDefinitionReferenceTable.setSerialNo(serialNo);
			legacyProjectDefinitionReferenceTable.setPspid(pspid);
			ETLUtil.getLegacyProjectDefinitionReferenceTable().put(projectNo, legacyProjectDefinitionReferenceTable);

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
