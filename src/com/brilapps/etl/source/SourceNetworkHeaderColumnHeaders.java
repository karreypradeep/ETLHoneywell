package com.brilapps.etl.source;

public enum SourceNetworkHeaderColumnHeaders {

	PROJECTNO("PROJECTNO"), PROJECT_DESCRIPTION("PROJECT_DESCRIPTION"), CUSTOMERNO("CUSTOMERNO"), COMPANYNAME("COMPANYNAME"), TASKNO(
			"TASKNO"), TASK_DESCRIPTION("TASK_DESCRIPTION"), COST_TYPE("COST_TYPE"), COST_TYPE_DESCRIPTION("COST_TYPE_DESCRIPTION"),
	COST_CATEGORY("COST_CATEGORY"),COST_CATEGORY_DESCRIPTION("COST_CATEGORY_DESCRIPTION"),FISCAL_YEAR("FISCAL_YEAR"),PERIOD("PERIOD"),
	ActualHours("ActualHours"),ActualAmount("ActualAmount");

	String columnHeader;

	SourceNetworkHeaderColumnHeaders(final String columnHeader) {
		this.columnHeader=columnHeader;
	}

	public String getColumnHeader() {
		return this.columnHeader;
	}

}
