package com.brilapps.etl.target;

public enum TargetNetworkHeaderColumnHeaders {

	SERIAL(1,"SERIAL"), PROFID(2,"PROFID"),PS_AUFART(3,"PS_AUFART"),WERKS(4,"WERKS"),
	DISPO(5,"DISPO"),KTEXT(6,"KTEXT"),GSTRP(7,"GSTRP"),
	GLTRP(8,"GLTRP"), TERKZ(9,"TERKZ"), PRONR(10,"PRONR"),
	PROJN(11, "PROJN"), PRCTR(12, "PRCTR"), SCOPE(13, "SCOPE"),
	TXJCD(14,"TXJCD"), PLGRP(15,"PLGRP"), KOSTV(16,"KOSTV"),
	DISPO_CO(17,"DISPO_CO"), APRIO(18,"APRIO"), KALSM(19,"KALSM"),
	KLVARP(20,"KLVARP"), KLVARI(21,"KLVARI"), ZSCHL(22,"ZSCHL"),
	NW_PLANCOST(23,"NW_PLANCOST"), AUDISP(24,"AUDISP");

	private String columnHeader;
	private int columnIndex;

	TargetNetworkHeaderColumnHeaders(final int index, final String columnHeader) {
		this.columnHeader=columnHeader;
		this.columnIndex=index;
	}

	public static TargetNetworkHeaderColumnHeaders[] getColumnHeadersByIndex() {
		TargetNetworkHeaderColumnHeaders[] columnHeadersByIndex = new TargetNetworkHeaderColumnHeaders[TargetNetworkHeaderColumnHeaders
		                                                                                               .values().length];
		int count = 0;
		for (TargetNetworkHeaderColumnHeaders eCColumnHeader : TargetNetworkHeaderColumnHeaders
				.values()) {
			columnHeadersByIndex[count] = eCColumnHeader;
			count++;
		}
		return columnHeadersByIndex;
	}

	/**
	 * @return the columnHeader
	 */
	public String getColumnHeader() {
		return columnHeader;
	}

	/**
	 * @return the columnIndex
	 */
	public int getColumnIndex() {
		return columnIndex;
	}

}
