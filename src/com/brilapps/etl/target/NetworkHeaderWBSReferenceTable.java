package com.brilapps.etl.target;

import com.brilapps.etl.ProjectType;

public class NetworkHeaderWBSReferenceTable {

	String projectNo;
	String taskNo;
	String ident;
	String psAufart;
	int werks;
	int prctr;
	String profid;
	String scope;
	String kalsm;
	String zschl;
	long serialNo;
	ProjectType projectType;
	String gstrp;
	String gltrp;
	String fabkl;
	String usr03;

	/**
	 * @return the projectType
	 */
	public ProjectType getProjectType() {
		return projectType;
	}

	/**
	 * @param projectType
	 *            the projectType to set
	 */
	public void setProjectType(final ProjectType projectType) {
		this.projectType = projectType;
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
	 * @return the ident
	 */
	public String getIdent() {
		return ident;
	}

	/**
	 * @param ident
	 *            the ident to set
	 */
	public void setIdent(final String ident) {
		this.ident = ident;
	}

	/**
	 * @return the psAufart
	 */
	public String getPsAufart() {
		return psAufart;
	}

	/**
	 * @param psAufart
	 *            the psAufart to set
	 */
	public void setPsAufart(final String psAufart) {
		this.psAufart = psAufart;
	}

	/**
	 * @return the werks
	 */
	public int getWerks() {
		return werks;
	}

	/**
	 * @param werks
	 *            the werks to set
	 */
	public void setWerks(final int werks) {
		this.werks = werks;
	}

	/**
	 * @return the prctr
	 */
	public int getPrctr() {
		return prctr;
	}

	/**
	 * @param prctr
	 *            the prctr to set
	 */
	public void setPrctr(final int prctr) {
		this.prctr = prctr;
	}

	/**
	 * @return the profid
	 */
	public String getProfid() {
		return profid;
	}

	/**
	 * @param profid
	 *            the profid to set
	 */
	public void setProfid(final String profid) {
		this.profid = profid;
	}

	/**
	 * @return the scope
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * @param scope
	 *            the scope to set
	 */
	public void setScope(final String scope) {
		this.scope = scope;
	}

	/**
	 * @return the kalsm
	 */
	public String getKalsm() {
		return kalsm;
	}

	/**
	 * @param kalsm
	 *            the kalsm to set
	 */
	public void setKalsm(final String kalsm) {
		this.kalsm = kalsm;
	}

	/**
	 * @return the zschl
	 */
	public String getZschl() {
		return zschl;
	}

	/**
	 * @param zschl
	 *            the zschl to set
	 */
	public void setZschl(final String zschl) {
		this.zschl = zschl;
	}

	/**
	 * @return the gstrp
	 */
	public String getGstrp() {
		return gstrp;
	}

	/**
	 * @param gstrp
	 *            the gstrp to set
	 */
	public void setGstrp(final String gstrp) {
		this.gstrp = gstrp;
	}

	/**
	 * @return the gltrp
	 */
	public String getGltrp() {
		return gltrp;
	}

	/**
	 * @param gltrp
	 *            the gltrp to set
	 */
	public void setGltrp(final String gltrp) {
		this.gltrp = gltrp;
	}

	/**
	 * @return the fabkl
	 */
	public String getFabkl() {
		return fabkl;
	}

	/**
	 * @param fabkl
	 *            the fabkl to set
	 */
	public void setFabkl(final String fabkl) {
		this.fabkl = fabkl;
	}

	/**
	 * @return the usr03
	 */
	public String getUsr03() {
		return usr03;
	}

	/**
	 * @param usr03
	 *            the usr03 to set
	 */
	public void setUsr03(final String usr03) {
		this.usr03 = usr03;
	}

	@Override
	public boolean equals(final Object o){
		if(o == null) {
			return false;
		}
		if (!(o instanceof NetworkHeaderWBSReferenceTable)) {
			return false;
		}

		NetworkHeaderWBSReferenceTable other = (NetworkHeaderWBSReferenceTable) o;

		if (!this.projectNo.equals(other.projectNo)) {
			return false;
		}
		if (!this.taskNo.equals(other.taskNo)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return projectNo.hashCode() * taskNo.hashCode();
	}
}
