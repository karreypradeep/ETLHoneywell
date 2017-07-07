package com.brilapps.etl.source;

public enum SourceNetworkActivityColumnHeaders {

	PROJECTNO(1, "PROJECTNO"), TASKNO(2, "TASKNO"), ALT_TASKNO(3, "ALT_TASKNO"), SERIAL_NO(4, "SERIAL_NO"), COST_TYPE(5, "COST_TYPE"), COST_TYPE_DESCRIPTION(6,
	"COST_TYPE_DESCRIPTION"),COST_TYPE_ACTUAL_DESCRIPTION(7,"COST_TYPE_ACTUAL_DESCRIPTION"), 
	VORNR(8, "VORNR");

	private String columnHeader;
	private int columnIndex;


	SourceNetworkActivityColumnHeaders(final int index, final String columnHeader) {
		this.columnHeader=columnHeader;
		this.columnIndex = index;
	}

	public String getColumnHeader() {
		return this.columnHeader;
	}

	/**
	 * @return the index
	 */
	public int getColumnIndex() {
		return columnIndex;
	}

	public static SourceNetworkActivityColumnHeaders[] getColumnHeadersByIndex() {
		SourceNetworkActivityColumnHeaders[] columnHeadersByIndex = new SourceNetworkActivityColumnHeaders[SourceNetworkActivityColumnHeaders
		                                                                                                   .values().length];
		int count = 0;
		for (SourceNetworkActivityColumnHeaders scurceNetworkActivityColumnHeaders : SourceNetworkActivityColumnHeaders
				.values()) {
			columnHeadersByIndex[count] = scurceNetworkActivityColumnHeaders;
			count++;
		}
		return columnHeadersByIndex;
	}

}
