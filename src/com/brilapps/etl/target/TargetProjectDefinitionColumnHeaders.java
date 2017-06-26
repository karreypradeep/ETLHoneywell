package com.brilapps.etl.target;

public enum TargetProjectDefinitionColumnHeaders {

	SERIAL(1,"SERIAL"), PSPID(2,"PSPID"),PROFL(3,"PROFL"),POST1(4,"POST1"),
	KIMSK(5,"KIMSK"),VERNR(6,"VERNR"),PLFAZ(7,"PLFAZ"),
	PLSEZ(8,"PLSEZ"), KALID(9,"KALID"), ZTEHT(10,"ZTEHT"),
	SPROG(11,"SPROG"), EPROG(12,"EPROG"), VKOKR(13,"VKOKR"),
	VBUKR(14,"VBUKR"), VGSBR(15,"VGSBR"), WERKS(16,"WERKS"),
	FUNC_AREA(17,"FUNC_AREA"), PRCTR(18,"PRCTR"), BPROF(19,"BPROF"),
	PPROF(20,"PPROF"), ZSCHM(21,"ZSCHM"), IMPRF(22,"IMPRF"),
	ABGSL(23,"ABGSL"), SMPRF(24,"SMPRF"), PARGR(25,"PARGR"),
	VPROF(26,"VPROF"), SCHTYP(27,"SCHTYP"), SCOPE(28,"SCOPE"),
	XSTAT(29,"XSTAT"), PLINT(30,"PLINT"), TXJCD(31,"TXJCD"),
	STSPR(32,"STSPR"), BESTA(33,"BESTA"), VKORG(34,"VKORG"),
	VTWEG(35,"VTWEG"), SPART(36,"SPART"), DPPPROF(37,"DPPPROF")
	, PROJECTNO(38,"PROJECTNO"), PROJECT_TYPE(39,"PROJECT_TYPE");

	private String columnHeader;
	private int columnIndex;

	TargetProjectDefinitionColumnHeaders(final int index, final String columnHeader) {
		this.columnHeader=columnHeader;
		this.columnIndex=index;
	}

	public static TargetProjectDefinitionColumnHeaders[] getColumnHeadersByIndex() {
		TargetProjectDefinitionColumnHeaders[] columnHeadersByIndex = new TargetProjectDefinitionColumnHeaders[TargetProjectDefinitionColumnHeaders
		                                                                                                       .values().length];
		int count = 0;
		for (TargetProjectDefinitionColumnHeaders eCColumnHeader : TargetProjectDefinitionColumnHeaders
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
