package com.brilapps.etl.target;

import java.util.HashMap;
import java.util.Map;

public class ReferenceTable {

	private String referenceColumnHeader;
	private String referenceColumnValue;
	private Map<TargetProjectDefinitionColumnHeaders, String> columnValues = new HashMap<TargetProjectDefinitionColumnHeaders, String>();

	/**
	 * @return the referenceColumnHeader
	 */
	public String getReferenceColumnHeader() {
		return referenceColumnHeader;
	}

	/**
	 * @param referenceColumnHeader
	 *            the referenceColumnHeader to set
	 */
	public void setReferenceColumnHeader(final String referenceColumnHeader) {
		this.referenceColumnHeader = referenceColumnHeader;
	}

	/**
	 * @return the referenceColumnValue
	 */
	public String getReferenceColumnValue() {
		return referenceColumnValue;
	}

	/**
	 * @param referenceColumnValue
	 *            the referenceColumnValue to set
	 */
	public void setReferenceColumnValue(final String referenceColumnValue) {
		this.referenceColumnValue = referenceColumnValue;
	}

	/**
	 * @return the columnValues
	 */
	public Map<TargetProjectDefinitionColumnHeaders, String> getColumnValues() {
		return columnValues;
	}


}
