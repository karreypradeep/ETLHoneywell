package com.brilapps.etl.target;

public enum TargetNetworkActivityColumnHeaders {

	SERIAL(1, "SERIAL"), VORNR(2, "VORNR"), LTXA1(3, "LTXA1"), ACT_TYPE(4, "ACT_TYPE"),
	PROJN_ACTIVITY(5,"PROJN_ACTIVITY"),ARBPL(6,"ARBPL"),WERKS_ACTIVITY(7,"WERKS_ACTIVITY"),
	ARBEI(8,"ARBEI"), ARBEH(9,"ARBEH"), INDET(10,"INDET"),	LARNT(11, "LARNT"), NPRIO(12, "NPRIO"), MLSTN(13, "MLSTN"),
	STEUS(14,"STEUS"), CLASF(15,"CLASF"), VERTL(16,"VERTL"), DAUNO(17,"DAUNO"), DAUNE(18,"DAUNE"), KALID(19,"KALID"),
	PREIS(20,"PREIS"), WAERS(21,"WAERS"), LOSVG(22,"LOSVG"), LOSME(23,"LOSME"), AUDISP_ACTIVITY(24,"AUDISP_ACTIVITY"),
	EKORG(25,"EKORG"), EKGRP(26,"EKGRP"), MATKL(27,"MATKL")	, EINSA(28,"EINSA"), NTANF(29,"NTANF"),
	EINSE(30,"EINSE"), NTEND(31,"NTEND"), FRSP(32,"FRSP"), PRCTR_ACTIVITY(33,"PRCTR_ACTIVITY"),
	AENNR(34,"AENNR"), RFPNT(35,"RFPNT"), KALSM_ACTIVITY(36,"KALSM_ACTIVITY"), ZSCHL_ACTIVITY(37,"ZSCHL_ACTIVITY"),
	TXJCD_ACTIVITY(38,"TXJCD_ACTIVITY"), SCOPE_ACTIVITY(39,"SCOPE_ACTIVITY"), SLWID(40,"SLWID"), USR00(41,"USR00"),
	USR01(42,"USR01"), USR02(43,"USR02"), USR03(44,"USR03"), USR04(45,"USR04"),
	USE04(46,"USE04"), USR05(47,"USR05"), USE05(48,"USE05"), USR06(49,"USR06"),
	USE06(50,"USE06"), USR07(51,"USR07"), USE07(52,"USE07"), VERSN_EV(53,"VERSN_EV"),
	EVMET_TXT_P(54,"EVMET_TXT_P"), EVMET_TXT_A(55,"EVMET_TXT_A"), SWRT10(56,"SWRT10"), SWRT11(57,"SWRT11"),
	PRKST(58,"PRKST"), ANFKO(59,"ANFKO"), SAKTO(60,"SAKTO");

	private String columnHeader;
	private int columnIndex;

	TargetNetworkActivityColumnHeaders(final int index, final String columnHeader) {
		this.columnHeader=columnHeader;
		this.columnIndex=index;
	}

	public static TargetNetworkActivityColumnHeaders[] getColumnHeadersByIndex() {
		TargetNetworkActivityColumnHeaders[] columnHeadersByIndex = new TargetNetworkActivityColumnHeaders[TargetNetworkActivityColumnHeaders
		                                                                                                   .values().length];
		int count = 0;
		for (TargetNetworkActivityColumnHeaders eCColumnHeader : TargetNetworkActivityColumnHeaders
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
