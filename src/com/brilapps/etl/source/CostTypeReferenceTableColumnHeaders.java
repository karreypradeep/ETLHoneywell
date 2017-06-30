package com.brilapps.etl.source;

public enum CostTypeReferenceTableColumnHeaders {

	COST_TYPE("COST_TYPE"), COST_TYPE_DESCRIPTION("COST_TYPE_DESCRIPTION"), COST_TYPE_ACTUAL_DESCRIPTION(
			"COST_TYPE_ACTUAL_DESCRIPTION");

	String columnHeader;

	CostTypeReferenceTableColumnHeaders(final String columnHeader) {
		this.columnHeader=columnHeader;
	}

	public String getColumnHeader() {
		return this.columnHeader;
	}

}
