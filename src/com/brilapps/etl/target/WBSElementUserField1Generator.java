package com.brilapps.etl.target;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.brilapps.etl.ETLUtil;

public class WBSElementUserField1Generator {
	static Logger logger = Logger.getLogger(WBSElementUserField1Generator.class);

	public final static HashMap<WBSElementUserField1ColumnHeaders, Object> destinationConstants = new HashMap<WBSElementUserField1ColumnHeaders, Object>();
	//public final static HashMap<String, ReferenceTable> referenceTableMap = new HashMap<String, ReferenceTable>();
	static {

		// Constants Add all the constants columns here so that they will be
		// directly added to target WBS file.
		destinationConstants.put(WBSElementUserField1ColumnHeaders.YY_EVRU, "X");
		destinationConstants.put(WBSElementUserField1ColumnHeaders.YYAUTHVALUE, "NORESTRICT");
		destinationConstants.put(WBSElementUserField1ColumnHeaders.YYDP010_PPC, "X");

		// Blank Space Constants
		// Constants Add all the Blank Spaces columns here so that they will be
		// directly added to target WBS file.
		// Blank Space Constants
		destinationConstants.put(WBSElementUserField1ColumnHeaders.YY_SBU, "");
		destinationConstants.put(WBSElementUserField1ColumnHeaders.YY_PLAT, "");
		destinationConstants.put(WBSElementUserField1ColumnHeaders.YY_PLATC, "");
		destinationConstants.put(WBSElementUserField1ColumnHeaders.YY_PCUST, "");
		destinationConstants.put(WBSElementUserField1ColumnHeaders.YY_CGRP, "");
		destinationConstants.put(WBSElementUserField1ColumnHeaders.YY_CGA, "");
		destinationConstants.put(WBSElementUserField1ColumnHeaders.YY_CWBS, "");
		destinationConstants.put(WBSElementUserField1ColumnHeaders.YY_FUN_TY, "");
		destinationConstants.put(WBSElementUserField1ColumnHeaders.YY_PRGT, "");
		destinationConstants.put(WBSElementUserField1ColumnHeaders.YY_PPC, "");
		destinationConstants.put(WBSElementUserField1ColumnHeaders.YY_EPT, "");
		destinationConstants.put(WBSElementUserField1ColumnHeaders.YY_PRIME, "");
		destinationConstants.put(WBSElementUserField1ColumnHeaders.YY_APPR, "");
		destinationConstants.put(WBSElementUserField1ColumnHeaders.YY_FR_RK, "");
		destinationConstants.put(WBSElementUserField1ColumnHeaders.YY_MATNR, "");
		destinationConstants.put(WBSElementUserField1ColumnHeaders.YY_MATO, "");
		destinationConstants.put(WBSElementUserField1ColumnHeaders.YY_REPTYPE, "");
		destinationConstants.put(WBSElementUserField1ColumnHeaders.YY_RPCD, "");
		destinationConstants.put(WBSElementUserField1ColumnHeaders.YYPROBWIN, "");

	}

	public static void writeHeaderColumns(final Sheet sheet) {
		logger.debug(" entering writeHeaderColumns() ");
		Row row = sheet.createRow(0);
		int colNum = 0;
		for (WBSElementUserField1ColumnHeaders field : WBSElementUserField1ColumnHeaders
				.getColumnHeadersByIndex()) {
			Cell cell = row.createCell(colNum++);
			cell.setCellValue(field.getColumnHeader());
		}
		logger.debug(" exiting writeHeaderColumns() ");
	}

	public static void generateWBSElementUserField1TargetFile(final Sheet wBSElementUserField1TargetSheet,
			final int rowNo, final String pspid, final String projectType, final String keyCode) throws Exception {
		logger.debug("entering generateWBSElementUserField1TargetFile ");

		try {
			Row wBSElementUserField1TargetRow = wBSElementUserField1TargetSheet.createRow(rowNo);
			logger.debug("in generateWBStargetFile before iterating duplicate records");

			for (WBSElementUserField1ColumnHeaders wbsElementUserField1ColumnHeader : WBSElementUserField1ColumnHeaders
					.getColumnHeadersByIndex()) {

				if (WBSElementUserField1ColumnHeaders.PSPID == wbsElementUserField1ColumnHeader) {
					Cell desCell = wBSElementUserField1TargetRow
							.createCell(wbsElementUserField1ColumnHeader.getColumnIndex() - 1);
					ETLUtil.setCellValue(desCell, pspid, logger);
					logger.debug("in generateWBSElementUserField1TargetFile adding PSPID column  " + pspid);
				}

				if (WBSElementUserField1ColumnHeaders.YYKEYCODE == wbsElementUserField1ColumnHeader) {
					Cell desCell = wBSElementUserField1TargetRow
							.createCell(wbsElementUserField1ColumnHeader.getColumnIndex() - 1);
					ETLUtil.setCellValue(desCell, keyCode, logger);
					logger.debug("in generateWBSElementUserField1TargetFile adding PSPID column  " + pspid);
				}

				if (WBSElementUserField1ColumnHeaders.YYFUNDDTL == wbsElementUserField1ColumnHeader) {
					Cell desCell = wBSElementUserField1TargetRow
							.createCell(wbsElementUserField1ColumnHeader.getColumnIndex() - 1);
					String fundingDetail = "";
					if (projectType.equals("EC")) {
						fundingDetail = "FIXED-PRICE";
					} else if (projectType.equals("EI")) {
						fundingDetail = "PROCESS-IMPROVED";
					} else if (projectType.equals("EB")) {
						fundingDetail = "SG&A";
					} else if (projectType.equals("ED")) {
						fundingDetail = "IRD";
					}
					ETLUtil.setCellValue(desCell, fundingDetail, logger);
					logger.debug("in generateWBSElementUserField1TargetFile adding YYFUNDDTL column  " + fundingDetail);
				}

				if (destinationConstants.get(wbsElementUserField1ColumnHeader) != null) {
					Cell desCell = wBSElementUserField1TargetRow
							.createCell(wbsElementUserField1ColumnHeader.getColumnIndex() - 1);
					ETLUtil.setCellValue(desCell, destinationConstants.get(wbsElementUserField1ColumnHeader), logger);
					logger.debug("in generateWBSElementUserField1TargetFile adding constant column  "
							+ wbsElementUserField1ColumnHeader.getColumnHeader() + desCell.getStringCellValue());
				}
			}
		} catch (Exception e) {
			logger.error(" in generateWBSElementUserField1TargetFile() ", e);
			throw new Exception(e);
		}
		logger.debug("exiting generateWBSElementUserField1TargetFile ");
	}

	/*public static void main(final String[] args) {

		new WBSGenerator().generateWBStargetFile(new File("D:/BavaProject/Files/SAPOttawaProjectWBS.xls"),
				new File("D:/BavaProject/GeneratedFiles/SAPOttawaProjectNoDupWBS.xls"),
				new File("D:/BavaProject/GeneratedFiles/WBSTarget.xls"),
				new File("D:/BavaProject/GeneratedFiles/projectDefinitionTarget.xls"));
	}*/

}
