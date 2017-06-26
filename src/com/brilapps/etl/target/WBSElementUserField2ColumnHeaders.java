package com.brilapps.etl.target;

public enum WBSElementUserField2ColumnHeaders {

	PROJ_EXT(1, "PROJ_EXT"), YYCONTRTYPE(2, "YYCONTRTYPE ");

	private String columnHeader;
	private int columnIndex;

	WBSElementUserField2ColumnHeaders(final int index, final String columnHeader) {
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

	public static WBSElementUserField2ColumnHeaders[] getColumnHeadersByIndex() {
		WBSElementUserField2ColumnHeaders[] columnHeadersByIndex = new WBSElementUserField2ColumnHeaders[WBSElementUserField2ColumnHeaders
		                                                                                                 .values().length];
		int count = 0;
		for (WBSElementUserField2ColumnHeaders wbsElementUserField2ColumnHeader : WBSElementUserField2ColumnHeaders
				.values()) {
			columnHeadersByIndex[count] = wbsElementUserField2ColumnHeader;
			count++;
		}
		return columnHeadersByIndex;
	}

}
