package com.brilapps.etl;

import java.util.HashMap;
import java.util.Map;

import com.brilapps.etl.target.LegacyProjectDefinitionReferenceTable;
import com.brilapps.etl.target.NetworkHeaderWBSReferenceTable;

public final class ETLUtil {

	private static Map<String, NetworkHeaderWBSReferenceTable> networkHeaderWBSReferenceTable =
			new HashMap<String, NetworkHeaderWBSReferenceTable>();

	private static Map<String, LegacyProjectDefinitionReferenceTable> legacyProjectDefinitionReferenceTable = new HashMap<String, LegacyProjectDefinitionReferenceTable>();

	/**
	 * @return the networkHeaderWBSReferenceTable
	 */
	public static Map<String, NetworkHeaderWBSReferenceTable> getNetworkHeaderWBSReferenceTable() {
		return networkHeaderWBSReferenceTable;
	}

	/**
	 * @return the legacyProjectDefinitionReferenceTable
	 */
	public static Map<String, LegacyProjectDefinitionReferenceTable> getLegacyProjectDefinitionReferenceTable() {
		return legacyProjectDefinitionReferenceTable;
	}

}
