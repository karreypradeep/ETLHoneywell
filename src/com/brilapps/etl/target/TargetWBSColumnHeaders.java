package com.brilapps.etl.target;

public enum TargetWBSColumnHeaders {

	SERIAL(1, "SERIAL"), STUFE(2, "STUFE"), IDENT(3, "IDENT"), POST1(4, "POST1"),
	POSKI(5,"POSKI"),PRART(6,"PRART"),PSPRI(7,"PSPRI"),
	CLASF(8,"CLASF"), PLAKZ(9,"PLAKZ"), BELKZ(10,"BELKZ"),
	FAKKZ(11,"FAKKZ"), GRPKZ(12,"GRPKZ"), VERNR(13,"VERNR"),
	ASTNR(14,"ASTNR"), FKOKR(15,"FKOKR"), FKSTL(16,"FKSTL"),
	AKOKR(17,"AKOKR"), AKSTL(18,"AKSTL"), PSTRT(19,"PSTRT"),
	PENDE(20,"PENDE"), PDAUR(21,"PDAUR"), PEINH(22,"PEINH"),
	FABKL(23,"FABKL"), ESTRT(24,"ESTRT"), EENDE(25,"EENDE"),
	EDAUR(26,"EDAUR"), EEINH(27,"EEINH"), PBUKR(28,"PBUKR"),
	PRCTR(29,"PRCTR"), PGSBR(30,"PGSBR"), SCOPE(31,"SCOPE"),
	TXJCD(32,"TXJCD"), SUBPR(33,"SUBPR"), WERKS(34,"WERKS"),
	FABKL_ASSIGNMENT(35,"FABKL_ASSIGNMENT"), EQUNR(36,"EQUNR"), TPLNR(37,"TPLNR"),
	AENNR(38, "AENNR"), KALSM(39, "KALSM"), ZSCHL(40, "ZSCHL"),
	ZSCHM_WBS(41,"ZSCHM_WBS"), IMPRF_WBS(42,"IMPRF_WBS"), ABGSL_WBS(43,"ABGSL_WBS"),
	PGPRF(44,"PGPRF"), XSTAT_WBS(45,"XSTAT_WBS"), PLINT_WBS(46,"PLINT_WBS"),
	ISIZE(47,"ISIZE"), IZWEK(48,"IZWEK"), IUMKZ(49,"IUMKZ"),
	SLWID(50,"SLWID"), USR00(51,"USR00"), USR01(52,"USR01"),
	USR02(53,"USR02"), USR03(54,"USR03"), USR04(55,"USR04"),
	USR05(56,"USR05"), USR06(57,"USR06"), USR07(58,"USR07"),
	USR08(59,"USR08"), USR09(60,"USR09"), USR10(61,"USR10"),
	USR11(62,"USR11"), EVGEW(63,"EVGEW"), VERSN_EV(64,"VERSN_EV"),
	EVMET_TXT_P(65,"EVMET_TXT_P"), EVMET_TXT_A(66,"EVMET_TXT_A");

	String columnHeader;
	int columnIndex;

	TargetWBSColumnHeaders(final int index, final String columnHeader) {
		this.columnHeader=columnHeader;
		this.columnIndex=index;
	}

	public static TargetWBSColumnHeaders[] getColumnHeadersByIndex() {
		TargetWBSColumnHeaders[] columnHeadersByIndex = new TargetWBSColumnHeaders[TargetWBSColumnHeaders
		                                                                           .values().length];
		int count = 0;
		for (TargetWBSColumnHeaders eCColumnHeader : TargetWBSColumnHeaders
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
