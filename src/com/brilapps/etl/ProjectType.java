package com.brilapps.etl;

import java.util.Arrays;
import java.util.List;

import com.brilapps.etl.target.TargetProjectDefinitionColumnHeaders;

public enum ProjectType {
	EB(2,"EB", "BP", Arrays.asList("Z000003"), "Z000001", "PS02", "OCOST", "01", 7001,"EB-00","14"),
	EC(1,"EC", "CE", Arrays.asList("Z000001"),"Z000003", "ZS02", "PROFIT", "01",6001,"EC-00","30"),
	ED(3,"ED", "IR", Arrays.asList("Z000003"), "Z000001", "PS02", "OCOST","01",9001,"ED-00","14"),
	EG(4,"EG", "GE", Arrays.asList("Z000001"), "Z000003", "ZS02", "PROFIT", "01",5001,"EG-00","11"),
	EI(5,"EI", "EF",Arrays.asList("Z000003", "Z002000"), "Z000001", "PS02", "OCOST", "01",8001,"EI-00","40");

	private int projectTypeSerialNo;
	private String projectPrefix;
	private String projectType;
	private List<String> projectProfiles;
	private String networkProfile;
	private String networkType;
	private String scope;
	private String taxPurpose;
	private String VTWEG;
	private int pspidStartIndex;
	private String PSPID_PREFIX;

	private ProjectType(final int projectTypeSerialNo, final String projectPrefix, final String projectType,
			final List<String> projectProfiles,
			final String networkProfile, final String networkType, final String scope, final String VTWEG,
			final int pspidStartIndex, final String PSPID_PREFIX, final String taxPurpose) {
		this.projectTypeSerialNo = projectTypeSerialNo;
		this.projectPrefix = projectPrefix;
		this.projectType = projectType;
		this.projectProfiles = projectProfiles;
		this.networkProfile = networkProfile;
		this.networkType = networkType;
		this.scope = scope;
		this.VTWEG = VTWEG;
		this.pspidStartIndex = pspidStartIndex;
		this.PSPID_PREFIX = PSPID_PREFIX;
		this.taxPurpose = taxPurpose;
	}

	/**
	 * @return the projectTypeSerialNo
	 */
	public int getProjectTypeSerialNo() {
		return projectTypeSerialNo;
	}

	/**
	 * @return the projectPrefix
	 */
	public String getProjectPrefix() {
		return projectPrefix;
	}

	/**
	 * @return the projectType
	 */
	public String getProjectType() {
		return projectType;
	}

	/**
	 * @return the projectProfiles
	 */
	public List<String> getProjectProfiles() {
		return projectProfiles;
	}

	/**
	 * @return the networkProfile
	 */
	public String getNetworkProfile() {
		return networkProfile;
	}

	/**
	 * @return the networkType
	 */
	public String getNetworkType() {
		return networkType;
	}

	/**
	 * @return the scope
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * @return the vTWEG
	 */
	public String getVTWEG() {
		return VTWEG;
	}

	/**
	 * @return the taxPurpose
	 */
	public String getTaxPurpose() {
		return taxPurpose;
	}

	/**
	 * @return the pspidStartIndex
	 */
	public int getPspidStartIndex() {
		return pspidStartIndex;
	}

	/**
	 * @return the pSPID_PREFIX
	 */
	public String getPSPID_PREFIX() {
		return PSPID_PREFIX;
	}

	public String getReferenceColumnValue(final TargetProjectDefinitionColumnHeaders generatorColumn) {

		if(TargetProjectDefinitionColumnHeaders.VPROF.equals(generatorColumn)){
			return getNetworkProfile();
		}else if(TargetProjectDefinitionColumnHeaders.SCOPE.equals(generatorColumn)){
			return getScope();
		}else if(TargetProjectDefinitionColumnHeaders.VTWEG.equals(generatorColumn)){
			return getVTWEG();
		} else {
			return null;
		}

	}

	public static ProjectType getProjectTypeByProjectPrefix(final String projectPrefix) {
		for (ProjectType projectType : ProjectType.values()) {
			if (projectType.getProjectPrefix().equals(projectPrefix)) {
				return projectType;
			}
		}
		return null;
	}

	public static ProjectType[] getColumnHeadersByProjectTypeSerialNo() {
		ProjectType[] columnHeadersByIndex = new ProjectType[ProjectType.values().length];
		int count = 0;
		for (ProjectType eCColumnHeader : ProjectType.values()) {
			columnHeadersByIndex[count] = eCColumnHeader;
			count++;
		}
		return columnHeadersByIndex;
	}
}
