package com.brilapps.etl;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

import com.brilapps.etl.target.CostTypeReferenceTable;
import com.brilapps.etl.target.LegacyProjectDefinitionReferenceTable;
import com.brilapps.etl.target.NetworkActivityCostTypeReferenceTable;
import com.brilapps.etl.target.NetworkHeaderActivityReferenceTable;
import com.brilapps.etl.target.NetworkHeaderWBSReferenceTable;

public final class ETLUtil {

	private static Map<String, NetworkHeaderWBSReferenceTable> networkHeaderWBSReferenceTable = new HashMap<String, NetworkHeaderWBSReferenceTable>();

	private static Map<String, LegacyProjectDefinitionReferenceTable> legacyProjectDefinitionReferenceTable = new HashMap<String, LegacyProjectDefinitionReferenceTable>();

	private static Map<String, ProjectDefinitionReferenceTable> projectDefinitionReferenceTableByProjectPrefix = new HashMap<String, ProjectDefinitionReferenceTable>();

	private static Map<String, CostTypeReferenceTable> costTypeReferenceTableByCostType = new HashMap<String, CostTypeReferenceTable>();

	private static Map<String, List<NetworkActivityCostTypeReferenceTable>> networkActivityCostTypeTableListByProjectTaskNo = new HashMap<String, List<NetworkActivityCostTypeReferenceTable>>();

	private static Map<String, NetworkHeaderActivityReferenceTable> networkHeaderActivityReferenceTableByProjectTaskNo = new HashMap<String, NetworkHeaderActivityReferenceTable>();

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

	/**
	 * @return the costTypeReferenceTableByCostType
	 */
	public static Map<String, CostTypeReferenceTable> getCostTypeReferenceTableByCostType() {
		return costTypeReferenceTableByCostType;
	}

	/**
	 * @return the networkActivityReferenceTableListByProjectTaskNo
	 */
	public static Map<String, List<NetworkActivityCostTypeReferenceTable>> getNetworkActivityCostTypeTableListByProjectTaskNo() {
		return networkActivityCostTypeTableListByProjectTaskNo;
	}

	/**
	 * @return the networkHeaderActivityReferenceTableByProjectTaskNo
	 */
	public static Map<String, NetworkHeaderActivityReferenceTable> getNetworkHeaderActivityReferenceTableByProjectTaskNo() {
		return networkHeaderActivityReferenceTableByProjectTaskNo;
	}

	public static Object getCellValue(final Cell cell, final Logger logger) {
		if (cell == null) {
			return "";
		}
		Object cellValue = null;
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			cellValue = cell.getStringCellValue().trim();
			if (logger.isDebugEnabled()) {
				logger.debug(" retrieving cell  String value " + cellValue);
			}
			break;

		case Cell.CELL_TYPE_FORMULA:
			cellValue = cell.getCellFormula();
			if (logger.isDebugEnabled()) {
				logger.debug(" retrieving cell  Formula value " + cellValue);
			}
			break;

		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				cellValue = new SimpleDateFormat("MM/dd/yyyy").format(cell.getDateCellValue());
				if (cellValue.toString().contains("1900")) {
					cellValue = new SimpleDateFormat("MM/dd/yyyy").format(new java.util.Date());
				}
				if (logger.isDebugEnabled()) {
					logger.debug(" retrieving cell  Date value " + cellValue);
				}
			} else {
				cellValue = cell.getNumericCellValue();
				if (logger.isDebugEnabled()) {
					logger.debug(" retrieving cell  Numeriv value " + cellValue);
				}
			}
			break;

		case Cell.CELL_TYPE_BLANK:
			cellValue = "";
			if (logger.isDebugEnabled()) {
				logger.debug(" retrieving cell  Blank value " + cellValue);
			}
			break;

		case Cell.CELL_TYPE_BOOLEAN:
			cellValue = Boolean.toString(cell.getBooleanCellValue());
			if (logger.isDebugEnabled()) {
				logger.debug(" retrieving cell  Boolean value " + cellValue);
			}
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
			if (logger.isDebugEnabled()) {
				logger.debug(" setting cell  String value " + cellValue);
			}
		} else if (cellValue instanceof Integer) {
			cell.setCellValue((int) cellValue);
			if (logger.isDebugEnabled()) {
				logger.debug(" setting cell  Integer value " + cellValue);
			}
		} else if (cellValue instanceof Double) {
			cell.setCellValue((Double) cellValue);
			if (logger.isDebugEnabled()) {
				logger.debug(" setting cell  Double value " + cellValue);
			}
		} else if (cellValue instanceof Long) {
			cell.setCellValue((Long)cellValue);
			if (logger.isDebugEnabled()) {
				logger.debug(" setting cell  Long value " + cellValue);
			}
		} else if (cellValue instanceof Date || cellValue instanceof java.util.Date) {
			cell.setCellValue(new SimpleDateFormat("MM/dd/yyyy").format(cellValue));
			if (logger.isDebugEnabled()) {
				logger.debug(" setting cell  Date value " + cellValue);
			}
		} else if (isNumeric(cellValue.toString())) {
			cell.setCellValue((int) cellValue);
			if (logger.isDebugEnabled()) {
				logger.debug(" setting cell  Object.toString value " + cellValue);
			}
		} else {
			cell.setCellValue(cellValue.toString());
			if (logger.isDebugEnabled()) {
				logger.debug(" setting cell  Object.toString value " + cellValue);
			}
		}
	}

	public static boolean isNumeric(final String str) {
		return str.matches("[+-]?\\d*(\\.\\d+)?");
	}
}
