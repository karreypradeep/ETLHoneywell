package com.brilapps.etl.source;

public enum SourceWBSColumnHeaders {

	PROJECTNO("PROJECTNO"), TASKNO("TASKNO"), SEQ_NUM("SEQ_NUM"), PARENT_TASKNO("PARENT_TASKNO"), DESCRIPTION(
			"DESCRIPTION"), PLANNERNO("PLANNERNO"), START_DATE("START_DATE"), STOP_DATE("STOP_DATE"),
	TASK_STATUS("TASK_STATUS"), LOW_LEVEL("LOW_LEVEL"), ENTITY_CODE("ENTITY_CODE"), MULTIENTITY_FLAG(
			"MULTIENTITY_FLAG"), COST_CATEGORY("COST_CATEGORY"), COST_CATEGORY_DESCRIPTION("COST_CATEGORY_DESCRIPTION");

	String columnHeader;

	SourceWBSColumnHeaders(final String columnHeader) {
		this.columnHeader=columnHeader;
	}

	public String getColumnHeader() {
		return this.columnHeader;
	}

}
