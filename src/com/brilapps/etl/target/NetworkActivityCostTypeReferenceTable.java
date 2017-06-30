package com.brilapps.etl.target;

public class NetworkActivityCostTypeReferenceTable {

	String projectNo;
	String taskNo;
	String costType;
	String vornr;
	String costTypeDescription;
	String costTypeActualDescription;

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
	 * @return the taskNo
	 */
	public String getTaskNo() {
		return taskNo;
	}

	/**
	 * @param taskNo
	 *            the taskNo to set
	 */
	public void setTaskNo(final String taskNo) {
		this.taskNo = taskNo;
	}

	/**
	 * @return the costType
	 */
	public String getCostType() {
		return costType;
	}

	/**
	 * @param costType
	 *            the costType to set
	 */
	public void setCostType(final String costType) {
		this.costType = costType;
	}

	/**
	 * @return the vornr
	 */
	public String getVornr() {
		return vornr;
	}

	/**
	 * @param vornr
	 *            the vornr to set
	 */
	public void setVornr(final String vornr) {
		this.vornr = vornr;
	}

	/**
	 * @return the costTypeDescription
	 */
	public String getCostTypeDescription() {
		return costTypeDescription;
	}

	/**
	 * @param costTypeDescription
	 *            the costTypeDescription to set
	 */
	public void setCostTypeDescription(final String costTypeDescription) {
		this.costTypeDescription = costTypeDescription;
	}

	/**
	 * @return the costTypeActualDescription
	 */
	public String getCostTypeActualDescription() {
		return costTypeActualDescription;
	}

	/**
	 * @param costTypeActualDescription
	 *            the costTypeActualDescription to set
	 */
	public void setCostTypeActualDescription(final String costTypeActualDescription) {
		this.costTypeActualDescription = costTypeActualDescription;
	}

}
