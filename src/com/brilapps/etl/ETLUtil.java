package com.brilapps.etl;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

import com.brilapps.etl.target.LegacyProjectDefinitionReferenceTable;
import com.brilapps.etl.target.NetworkHeaderWBSReferenceTable;

public final class ETLUtil {

	private static Map<String, NetworkHeaderWBSReferenceTable> networkHeaderWBSReferenceTable = new HashMap<String, NetworkHeaderWBSReferenceTable>();

	private static Map<String, LegacyProjectDefinitionReferenceTable> legacyProjectDefinitionReferenceTable = new HashMap<String, LegacyProjectDefinitionReferenceTable>();

	private static Map<String, ProjectDefinitionReferenceTable> projectDefinitionReferenceTableByProjectPrefix = new HashMap<String, ProjectDefinitionReferenceTable>();

	/**
	 * @return the networkHeaderWBSReferenceTable
	 */
	public static Map<String, NetworkHeaderWBSReferenceTable> getNetworkHeaderWBSReferenceTable() {
		return networkHeaderWBSReferenceTable;
	}

	/**
	 * @return the legacyProjectDefinitionReferenceTable
	 */
	public static Map<String, LegacyProjectDefinitionReferenceTable> getLegacyProjectDefinitionReferenceTable() {
		return legacyProjectDefinitionReferenceTable;
	}

	/**
	 * @return the projectDefinitionReferenceTableByProjectPrefix
	 */
	public static Map<String, ProjectDefinitionReferenceTable> getProjectDefinitionReferenceTableByProjectPrefix() {
		return projectDefinitionReferenceTableByProjectPrefix;
	}

	public static Object getCellValue(final Cell cell, final Logger logger) {
		if (cell == null) {
			return "";
		}
		Object cellValue = null;
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			cellValue = cell.getStringCellValue();
			logger.debug(" retrieving cell  String value " + cellValue);
			break;

		case Cell.CELL_TYPE_FORMULA:
			cellValue = cell.getCellFormula();
			logger.debug(" retrieving cell  Formula value " + cellValue);
			break;

		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				cellValue = new SimpleDateFormat("MM/dd/yyyy").format(cell.getDateCellValue());
				logger.debug(" retrieving cell  Date value " + cellValue);
			} else {
				cellValue = cell.getNumericCellValue();
				logger.debug(" retrieving cell  Numeriv value " + cellValue);
			}
			break;

		case Cell.CELL_TYPE_BLANK:
			cellValue = "";
			logger.debug(" retrieving cell  Blank value " + cellValue);
			break;

		case Cell.CELL_TYPE_BOOLEAN:
			cellValue = Boolean.toString(cell.getBooleanCellValue());
			logger.debug(" retrieving cell  Boolean value " + cellValue);
			break;

		}
		return cellValue;
	}

	public static void setCellValue(final Cell cell, final Object cellValue, final Logger logger) {
		if (cellValue == null) {
			cell.setCellValue("");
			return;
		}
		if (cellValue instanceof String) {
			cell.setCellValue(cellValue.toString());
			logger.debug(" setting cell  String value " + cellValue);
		} else if (cellValue instanceof Integer) {
			cell.setCellValue((int) cellValue);
			logger.debug(" setting cell  Integer value " + cellValue);
		} else if (cellValue instanceof Double) {
			cell.setCellValue((Double) cellValue);
			logger.debug(" setting cell  Double value " + cellValue);
		} else if (cellValue instanceof Long) {
			cell.setCellValue((Long)cellValue);
			logger.debug(" setting cell  Long value " + cellValue);
		} else if (cellValue instanceof Date || cellValue instanceof java.util.Date) {
			cell.setCellValue(new SimpleDateFormat("MM/dd/yyyy").format(cellValue));
			logger.debug(" setting cell  Date value " + cellValue);
		} else if (isNumeric(cellValue.toString())) {
			cell.setCellValue((int) cellValue);
			logger.debug(" setting cell  Object.toString value " + cellValue);
		} else {
			cell.setCellValue(cellValue.toString());
			logger.debug(" setting cell  Object.toString value " + cellValue);
		}
	}

	public static boolean isNumeric(final String str) {
		return str.matches("[+-]?\\d*(\\.\\d+)?");
	}
}
