package com.brilapps.etl.source;

public enum ProjectDefinitionReferenceTableColumnHeaders {

	PROJECT_PRIFIX("PROJECT_PRIFIX"), PROJECT_PROFILE("PROJECT_PROFILE"), PROJECT_TYPE("PROJECT_TYPE"), TAX_PURPOSE("TAX_PURPOSE"), NETWORK_PROFILE(
			"NETWORK_PROFILE"), NETWORK_TYPE("NETWORK_TYPE"), SCOPE("SCOPE"), SERIAL_NO_START_INDEX("SERIAL_NO_START_INDEX"),
	PSPID_PREFIX("PSPID_PREFIX"),PSPID_START_INDEX("PSPID_START_INDEX");

	String columnHeader;

	ProjectDefinitionReferenceTableColumnHeaders(final String columnHeader) {
		this.columnHeader=columnHeader;
	}

	public String getColumnHeader() {
		return this.columnHeader;
	}

}
