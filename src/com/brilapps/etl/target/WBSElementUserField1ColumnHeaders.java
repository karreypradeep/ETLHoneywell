package com.brilapps.etl.target;

public enum WBSElementUserField1ColumnHeaders {


	PSPID(1,"PSPID"), YY_SBU(2,"YY_SBU"),YY_PLAT(3,"YY_PLAT"),YY_PLATC(4,"YY_PLATC"),
	YY_PCUST(5,"YY_PCUST"),YY_CGRP(6,"YY_CGRP"),YY_CGA(7,"YY_CGA"),
	YY_CWBS(8,"YY_CWBS"), YY_FUN_TY(9,"YY_FUN_TY"), YY_PRGT(10,"YY_PRGT"),
	YY_PPC(11,"YY_PPC"), YY_EPT(12,"YY_EPT"), YY_PRIME(13,"YY_PRIME"),
	YY_EVRU(14,"YY_EVRU"), YY_APPR(15,"YY_APPR"), YY_FR_RK(16,"YY_FR_RK"),
	YY_MATNR(17,"YY_MATNR"), YY_MATO(18,"YY_MATO"), YY_REPTYPE(19,"YY_REPTYPE"),
	YY_RPCD(20,"YY_RPCD"), YYAUTHVALUE(21,"YYAUTHVALUE"), YYKEYCODE(22,"YYKEYCODE"),
	YYPROBWIN(23,"YYPROBWIN"), YYFUNDDTL(24,"YYFUNDDTL"), YYDP010_PPC(25,"YYDP010_PPC");


	private String columnHeader;
	private int columnIndex;

	WBSElementUserField1ColumnHeaders(final int index, final String columnHeader) {
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

	public static WBSElementUserField1ColumnHeaders[] getColumnHeadersByIndex() {
		WBSElementUserField1ColumnHeaders[] columnHeadersByIndex = new WBSElementUserField1ColumnHeaders[WBSElementUserField1ColumnHeaders
		                                                                                                 .values().length];
		int count = 0;
		for (WBSElementUserField1ColumnHeaders wbsElementUserField1ColumnHeader : WBSElementUserField1ColumnHeaders
				.values()) {
			columnHeadersByIndex[count] = wbsElementUserField1ColumnHeader;
			count++;
		}
		return columnHeadersByIndex;
	}

}
