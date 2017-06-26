package com.brilapps.etl.source;

public enum SourceProjectDefinitionColumnHeaders {

	PROJECTNO("PROJECTNO"), TASKNO("TASKNO"), CUSTOMERNO("CUSTOMERNO"), CUSTPONO("CUSTPONO"), DESCRIPTION(
			"DESCRIPTION"), DATE_ADDED("DATE ADDED"), WBS_REVISION("WBS REVISION"), REVISION_DATE("REVISION DATE"),
	WBS_STATUS("WBS STATUS"),FIRM_PROJECTED("FIRM PROJECTED"),START_DATE("START DATE"),STOP_DATE("STOP DATE"),
	AS_SOLD_PRICE("AS SOLD PRICE"),PLANNERNO("PLANNERNO"),PROJECT_TYPE("PROJECT TYPE"),FEE_PERCENT("FEE PERCENT"),
	DEPT_CODE("DEPT CODE"),FORECAST_PRINT("FORECAST PRINT"),FINANCIAL_RESERVE("FINANCIAL RESERVE"),MPM_EXPORT("MPM EXPORT"),
	ENTITY_CODE("ENTITY CODE"),MULTIENTITY_FLAG("MULTIENTITY FLAG"),CATEGORY("CATEGORY"),DD254("DD254"),HON_PRODUCT_TYPE("HON_PRODUCT_TYPE"),
	CONTRACT_NUMBER("CONTRACT NUMBER"),GOVERNMENT_PRIORITY("GOVERNMENT PRIORITY"),PROJECT_FUNDING_LEVEL("PROJECT FUNDING LEVEL"),
	TINA_REQUIREMENTS("TINA REQUIREMENTS"),CASS_REQUIREMENTS("CASS REQUIREMENTS"),QUALITY_REQUIREMENTS("QUALITY REQUIREMENTS"),
	KEY_CODE("KEY CODE"),PLANNER_NAME("PLANNER NAME"),PROJECT_PREFIX("PROJECT PREFIX"),PROJECT_PROFILE("PROJECT PROFILE")
	,COMPANY_CODE("COMPANY CODE"),PROFIT_CENTER("PROFIT CENTER"),
	FUNDING_DETAILS("FUNDING DETAILS");

	String columnHeader;

	SourceProjectDefinitionColumnHeaders(final String columnHeader) {
		this.columnHeader=columnHeader;
	}

	public String getColumnHeader() {
		return this.columnHeader;
	}

}
