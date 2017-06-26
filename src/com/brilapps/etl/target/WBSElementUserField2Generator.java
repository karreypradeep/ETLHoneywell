package com.brilapps.etl.target;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class WBSElementUserField2Generator {
	static Logger logger = Logger.getLogger(WBSElementUserField2Generator.class);

	public static void writeHeaderColumns(final Sheet sheet) {
		logger.debug(" entering writeHeaderColumns() ");
		Row row = sheet.createRow(0);
		int colNum = 0;
		for (WBSElementUserField2ColumnHeaders field : WBSElementUserField2ColumnHeaders
				.getColumnHeadersByIndex()) {
			Cell cell = row.createCell(colNum++);
			cell.setCellValue(field.getColumnHeader());
		}
		logger.debug(" exiting writeHeaderColumns() ");
	}

	public static void generateWBSElementUserField2TargetFile(final Sheet wBSElementUserField2TargetSheet,
			final int rowNo, final String pspid, final String projectType) throws Exception {
		logger.debug("entering generateWBSElementUserField1TargetFile ");

		try {
			Row wBSElementUserField1TargetRow = wBSElementUserField2TargetSheet.createRow(rowNo);
			logger.debug("in generateWBSElementUserField2TargetFile before iterating duplicate records");

			for (WBSElementUserField2ColumnHeaders wbsElementUserField2ColumnHeader : WBSElementUserField2ColumnHeaders
					.getColumnHeadersByIndex()) {

				if (WBSElementUserField2ColumnHeaders.YYCONTRTYPE == wbsElementUserField2ColumnHeader) {
					Cell desCell = wBSElementUserField1TargetRow
							.createCell(wbsElementUserField2ColumnHeader.getColumnIndex() - 1);
					String fundingDetail = "";
					if (projectType.equals("EC")) {
						fundingDetail = "Commercial";
					}
					desCell.setCellValue(fundingDetail);
					logger.debug("in generateWBSElementUserField2TargetFile adding YYFUNDDTL column  " + fundingDetail);
				}

				if (WBSElementUserField2ColumnHeaders.PROJ_EXT == wbsElementUserField2ColumnHeader) {
					Cell desCell = wBSElementUserField1TargetRow
							.createCell(wbsElementUserField2ColumnHeader.getColumnIndex() - 1);
					desCell.setCellValue(pspid);
					logger.debug("in generateWBSElementUserField2TargetFile adding PSPID column  " + pspid);
				}

			}
		} catch (Exception e) {
			logger.error(" in generateWBSElementUserField2TargetFile() ", e);
			throw new Exception(e);
		}
		logger.debug("exiting generateWBSElementUserField2TargetFile ");
	}

	/*public static void main(final String[] args) {

		new WBSGenerator().generateWBStargetFile(new File("D:/BavaProject/Files/SAPOttawaProjectWBS.xls"),
				new File("D:/BavaProject/GeneratedFiles/SAPOttawaProjectNoDupWBS.xls"),
				new File("D:/BavaProject/GeneratedFiles/WBSTarget.xls"),
				new File("D:/BavaProject/GeneratedFiles/projectDefinitionTarget.xls"));
	}*/

}
