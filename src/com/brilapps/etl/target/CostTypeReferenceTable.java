package com.brilapps.etl.target;

public class CostTypeReferenceTable {
	String costType;
	String costTypeDescription;
	String costTypeActualDescription;

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
