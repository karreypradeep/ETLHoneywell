package com.brilapps.etl.target;

public class LegacyProjectDefinitionReferenceTable {

	String projectNo;
	long serialNo;
	String projectType;
	String pspid;

	/**
	 * @return the pspid
	 */
	public String getPspid() {
		return pspid;
	}

	/**
	 * @param pspid
	 *            the pspid to set
	 */
	public void setPspid(final String pspid) {
		this.pspid = pspid;
	}

	/**
	 * @return the projectNo
	 */
	public String getProjectNo() {
		return projectNo;
	}

	/**
	 * @param projectNo
	 *            the projectNo to set
	 */
	public void setProjectNo(final String projectNo) {
		this.projectNo = projectNo;
	}

	/**
	 * @return the serialNo
	 */
	public long getSerialNo() {
		return serialNo;
	}

	/**
	 * @param serialNo
	 *            the serialNo to set
	 */
	public void setSerialNo(final long serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * @return the projectType
	 */
	public String getProjectType() {
		return projectType;
	}

	/**
	 * @param projectType
	 *            the projectType to set
	 */
	public void setProjectType(final String projectType) {
		this.projectType = projectType;
	}

	@Override
	public boolean equals(final Object o){
		if(o == null) {
			return false;
		}
		if (!(o instanceof LegacyProjectDefinitionReferenceTable)) {
			return false;
		}

		LegacyProjectDefinitionReferenceTable other = (LegacyProjectDefinitionReferenceTable) o;

		if (!this.projectNo.equals(other.projectNo)) {
			return false;
		}


		return true;
	}

	@Override
	public int hashCode() {
		return projectNo.hashCode();
	}
}
