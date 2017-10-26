/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, see http://www.gnu.org/licenses or write to the * 
 * Free Software Foundation, Inc.,                                            *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * Copyright (C) 2003-2016                                                    *
 * All Rights Reserved.                                                       *
 *****************************************************************************/
package org.eam.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.compiere.model.MAsset;
import org.compiere.model.MDocType;
import org.compiere.model.MPeriod;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;

/**
 * Service Order (Work Order)
 * @author OFB Consulting http://www.ofbconsulting.com
 * 	<li> Initial Contributor
 * @contributor Mario Calderon, Systemhaus Westfalia, http://www.westfalia-it.com
 * @contributor Adaxa http://www.adaxa.com
 * @contributor Deepak Pansheriya, Loglite Technologies, http://logilite.com
 * @contributor Victor Perez, victor.perez@e-evolution.com, eEvolution http://www.e-evolution.com
 * @contributor Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 * @contributor Sachin Bhimani
 */
public class MAMServiceOrder extends X_AM_ServiceOrder implements DocAction, DocOptions
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 152203887207801137L;
	/** Process Message */
	private String				m_processMsg		= null;
	/** Just Prepared Flag */
	private boolean				m_justPrepared		= false;

	public MAMServiceOrder(Properties ctx, int AM_ServiceOrder_ID, String trxName)
	{
		super(ctx, AM_ServiceOrder_ID, trxName);
	}

	public MAMServiceOrder(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	/**
	 * Add to Description
	 * 
	 * @param description text
	 */
	public void addDescription(String description)
	{
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	} // addDescription

	/**
	 * Get Document Info
	 * 
	 * @return document info (untranslated)
	 */
	public String getDocumentInfo()
	{
		return Msg.getElement(getCtx(), "AM_ServiceOrder_ID") + " " + getDocumentNo();
	} // getDocumentInfo

	/**
	 * Create PDF
	 * 
	 * @return File or null
	 */
	public File createPDF()
	{
		try
		{
			File temp = File.createTempFile(get_TableName() + get_ID() + "_", ".pdf");
			return createPDF(temp);
		}
		catch (Exception e)
		{
			log.severe("Could not create PDF - " + e.getMessage());
		}
		return null;
	} // getPDF

	/**
	 * Create PDF file
	 * 
	 * @param file output file
	 * @return file if success
	 */
	public File createPDF(File file)
	{
		return null;
	} // createPDF

	/**************************************************************************
	 * Process document
	 * 
	 * @param processAction document action
	 * @return true if performed
	 */
	public boolean processIt(String processAction)
	{
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine(this, getDocStatus());
		return engine.processIt(processAction, getDocAction());
	} // processIt

	/**
	 * Unlock Document.
	 * 
	 * @return true if success
	 */
	public boolean unlockIt()
	{
		log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	} // unlockIt

	/**
	 * Invalidate Document
	 * 
	 * @return true if success
	 */
	public boolean invalidateIt()
	{
		log.info("invalidateIt - " + toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	} // invalidateIt

	/**
	 * Prepare Document
	 * 
	 * @return new status (In Progress or Invalid)
	 */
	public String prepareIt() {
		log.info(toString());
		m_justPrepared = true;
		
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		//	Std Period open?
		if (!MPeriod.isOpen(getCtx(), getDateTrx(), dt.getDocBaseType(), getAD_Org_ID())) {
			m_processMsg = "@PeriodClosed@";
			return DocAction.STATUS_Invalid;
		}
		//	Set from maintenance or pattern
		if(getAM_Maintenance_ID() != 0) {
			createFromMaintenance(getAM_Maintenance_ID());
		} else if(getAM_Pattern_ID() != 0) {
			createFromPattern(getAM_Pattern_ID());
		}
		//	Update Cost
		updateCosts();
		//	
		if (!DOCACTION_Complete.equals(getDocAction()))
			setDocAction(DOCACTION_Complete);
		return DocAction.STATUS_InProgress;
	} // prepareIt
	
	/**
	 * 	Set the definite document number after completed
	 */
	private void setDefiniteDocumentNo() {
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		if (dt.isOverwriteDateOnComplete()) {
			setDateTrx(new Timestamp (System.currentTimeMillis()));
		}
		if (dt.isOverwriteSeqOnComplete()) {
			String value = DB.getDocumentNo(getC_DocType_ID(), get_TrxName(), true, this);
			if (value != null)
				setDocumentNo(value);
		}
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		if(newRecord) {
			if(getC_DocType_ID() == 0) {
				MDocType [] documentType = MDocType.getOfDocBaseType(Env.getCtx(), "MSO");
				if(documentType.length > 0) {
					setC_DocType_ID(documentType[0].getC_DocType_ID());
				}
			}
		}
		return super.beforeSave(newRecord);
	}
	
	/**
	 * Approve Document
	 * 
	 * @return true if success
	 */
	public boolean approveIt()
	{
		log.info("approveIt - " + toString());
		setIsApproved(true);
		return true;
	} // approveIt

	/**
	 * Reject Approval
	 * 
	 * @return true if success
	 */
	public boolean rejectIt()
	{
		log.info("rejectIt - " + toString());
		setIsApproved(false);
		return true;
	} // rejectIt

	/**
	 * Complete Document
	 * 
	 * @return new status (Complete, In Progress, Invalid, Waiting ..)
	 */
	public String completeIt()
	{
		// Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		// Implicit Approval
		if (!isApproved())
			approveIt();
		log.info("completeIt - " + toString());

		// User Validation
		String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			m_processMsg = valid;
			return DocAction.STATUS_Invalid;
		}
		//	Set Definitive Document No
		setDefiniteDocumentNo();
		//	
		setProcessed(true);
		setDocAction(DOCACTION_Close);
		// update Preventive Maintenance's Field "Date Last Work Order"
		if (getAM_Maintenance_ID() != 0) {
			MAMMaintenance maintenance = MAMMaintenance.get(getCtx(), getAM_Maintenance_ID());
			//	
			// update Preventive Maintenance with real or Work Order's official
			// time?
			if (isTimeReal()) {
				maintenance.setDateLastSO(getDateTrx());
			} else {
				maintenance.setDateLastSO(getDateStartPlan());
			}
			maintenance.saveEx();
		}

		// update Asset's Last Maintenance date
		MAsset asset = MAsset.get(getCtx(), getA_Asset_ID(), get_TrxName());
		if (asset != null) {
			// update Asset: with real or Work order's official time?
			if (isTimeReal()) {
				asset.setLastMaintenanceDate(getDateTrx());
			} else {
				asset.setLastMaintenanceDate(getDateStartPlan());
			}
			asset.saveEx();
		}
		return DocAction.STATUS_Completed;
	} // completeIt

	/**
	 * Close Document. Cancel not delivered Quantities
	 * 
	 * @return true if success
	 */
	public boolean closeIt()
	{
		log.info("closeIt - " + toString());

		setDocAction(DOCACTION_None);
		return true;
	} // closeIt

	/**
	 * Reverse Correction
	 * 
	 * @return false
	 */
	public boolean reverseCorrectIt()
	{
		log.info("reverseCorrectIt - " + toString());
		return false;
	} // reverseCorrectionIt

	/**
	 * Reverse Accrual - none
	 * 
	 * @returif(getAM_Maintenance_ID() == 0) {
			//	Change Programmed Maintenance
			if(getDocStatus().equals(DOCSTATUS_Completed)) {
				n false
	 */
	public boolean reverseAccrualIt()
	{
		log.info("reverseAccrualIt - " + toString());
		return false;
	} // reverseAccrualIt

	/**
	 * Re-activate
	 * 
	 * @return false
	 */
	public boolean reActivateIt()
	{
		log.info("reActivateIt - " + toString());
		return false;
	} // reActivateIt

	/**
	 * Void Document.
	 * 
	 * @return false
	 */
	public boolean voidIt() {
		log.info("voidIt - " + toString());
		if(getAM_Maintenance_ID() == 0) {
			if(getDocStatus().equals(DOCSTATUS_Completed)) {
				MAMMaintenance maintenance = MAMMaintenance.get(getCtx(), getAM_Maintenance_ID());
				if (maintenance.getProgrammingType().equals(MAMMaintenance.PROGRAMMINGTYPE_Calendar)) {
					maintenance.setDateNextRun(new Timestamp(maintenance.getDateLastRun().getTime()
							- (maintenance.getInterval().longValue() * 86400000)));
				} else {
					maintenance.setNextAM(maintenance.getNextAM().subtract(maintenance.getInterval()));
					maintenance.setLastAM(maintenance.getNextAM().subtract(maintenance.getInterval()));
				}
				//	
				maintenance.saveEx();
			}
		}
		//	
		return true;
	} // voidIt

	/*************************************************************************
	 * Get Summary
	 * 
	 * @return Summary of Document
	 */
	public String getSummary() {
		return getDocumentInfo();
	} // getSummary

	/**
	 * Get Process Message
	 * 
	 * @return clear text error message
	 */
	public String getProcessMsg()
	{
		return m_processMsg;
	} // getProcessMsg

	/**
	 * Get Document Owner (Responsible)
	 * 
	 * @return AD_User_ID
	 */
	public int getDoc_User_ID()
	{
		return getUpdatedBy();
	} // getDoc_User_ID

	/**
	 * Get Document Currency
	 * 
	 * @return C_Currency_ID
	 */

	public BigDecimal getApprovalAmt()
	{

		return Env.ZERO;
	}

	public int getC_Currency_ID()
	{
		return Env.getContextAsInt(getCtx(), "$C_Currency_ID");
	} // getC_Currency_ID

	/**
	 * get the costs of a job
	 * 
	 * @return Costs
	 */
	public BigDecimal getCosts() {
		BigDecimal workOrderCosts = BigDecimal.ZERO;
		for (MAMServiceOrderTask task : getTasks()) {
			task.updateCosts();
			workOrderCosts = workOrderCosts.add(task.getCostAmt());
		}
		return workOrderCosts;
	} // getSOCosts

	/**
	 * update the costs of a SO
	 */
	private void updateCosts() {
		setCostAmt(getCosts());
		saveEx();
	} // updateSOCosts
	
	/**
	 * Get Tasks from Service Order
	 * @return
	 */
	public List<MAMServiceOrderTask> getTasks() {
		String whereClause = COLUMNNAME_AM_ServiceOrder_ID + "=? ";
		List<MAMServiceOrderTask> list = new Query(getCtx(), I_AM_ServiceOrderTask.Table_Name,
				whereClause, get_TrxName())
						.setClient_ID()
						.setParameters(getAM_ServiceOrder_ID())
						.setOnlyActiveRecords(true)
						.list();

		return list;
	} // getJSTaskResources

	@Override
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) {
		//	Valid Document Action
		if (AD_Table_ID == Table_ID){
			if (docStatus.equals(DocumentEngine.STATUS_Drafted)
					|| docStatus.equals(DocumentEngine.STATUS_InProgress)
					|| docStatus.equals(DocumentEngine.STATUS_Invalid)) {
					options[index++] = DocumentEngine.ACTION_Prepare;
					options[index++] = DocumentEngine.ACTION_Close;
					options[index++] = DocumentEngine.ACTION_Complete;
			} else if(docStatus.equals(DocumentEngine.STATUS_Completed)){
				options[index++] = DocumentEngine.ACTION_Close;
				options[index++] = DocumentEngine.ACTION_Void;
			} else {
				options[index++] = DocumentEngine.ACTION_None;
			}
		}
		return index;
	}
	
	/**
	 * Create Tasks and resources from maintenance pattern
	 * @param patternId
	 * @return
	 */
	public boolean createFromPattern(int patternId) {
		//	Validate if exist pattern
		if(getAM_Pattern_ID() <= 0) {
			return false;
		}
		//	Already have tasks
		List<MAMServiceOrderTask> tasks = getTasks();
		if(tasks == null
				|| tasks.size() > 0) {
			return false;
		}
		MAMPattern pattern = MAMPattern.get(getCtx(), patternId);
		//	Iterate
		pattern.getTasks().stream().forEach(patternTask -> {
			MAMServiceOrderTask serviceOrderTask = new MAMServiceOrderTask(getCtx(), 0, get_TrxName());
			serviceOrderTask.setAM_ServiceOrder_ID(getAM_ServiceOrder_ID());
			serviceOrderTask.setPatternTask(patternTask);
			//	Save
			serviceOrderTask.saveEx();
			//	Get Resource
			patternTask.getResources().stream().forEach(taskResource -> {
				MAMServiceOrderResource serviceOrderResource = new MAMServiceOrderResource(getCtx(), 0, get_TrxName());
				serviceOrderResource.setAM_ServiceOrderTask_ID(serviceOrderTask.getAM_ServiceOrderTask_ID());
				serviceOrderResource.setPatternResource(taskResource);
				serviceOrderResource.saveEx();
			});
		});
		//	
		setAM_Pattern_ID(pattern.getAM_Pattern_ID());
		// copy Asset and Asset Group from Maintenance Pattern
		setA_Asset_ID(pattern.getA_Asset_ID());
		// copy Maintenance Area and Costs from Maintenance Pattern
		setCostAmt(pattern.getCostAmt());
		setAM_Area_ID(pattern.getAM_Area_ID());
		saveEx();
		//	Return ok
		return true;
	}
	
	/**
	 * Create Tasks and resources from maintenance
	 * @param maintenanceId
	 * @return
	 */
	public boolean createFromMaintenance(int maintenanceId) {
		//	Validate if exist pattern
		if(getAM_Maintenance_ID() <= 0) {
			return false;
		}
		//	Already have tasks
		List<MAMServiceOrderTask> tasks = getTasks();
		if(tasks == null
				|| tasks.size() > 0) {
			return false;
		}
		MAMMaintenance maintenance = MAMMaintenance.get(getCtx(), maintenanceId);
		//	Iterate
		maintenance.getTasks().stream().forEach(maintenanceTask -> {
			MAMServiceOrderTask serviceOrderTask = new MAMServiceOrderTask(getCtx(), 0, get_TrxName());
			serviceOrderTask.setAM_ServiceOrder_ID(getAM_ServiceOrder_ID());
			serviceOrderTask.setMaintenanceTask(maintenanceTask);
			//	Save
			serviceOrderTask.saveEx();
			//	Get Resource
			maintenanceTask.getResources().stream().forEach(taskResource -> {
				MAMServiceOrderResource serviceOrderResource = new MAMServiceOrderResource(getCtx(), 0, get_TrxName());
				serviceOrderResource.setAM_ServiceOrderTask_ID(serviceOrderTask.getAM_ServiceOrderTask_ID());
				serviceOrderResource.setMaintenanceResource(taskResource);
				serviceOrderResource.saveEx();
			});
		});
		//	
		setAM_Pattern_ID(maintenance.getAM_Pattern_ID());
		// copy Asset and Asset Group from Maintenance Pattern
		setA_Asset_ID(maintenance.getA_Asset_ID());
		// copy Maintenance Area and Costs from Maintenance Pattern
		setCostAmt(maintenance.getCostAmt());
		setAM_Area_ID(maintenance.getAM_Area_ID());
		saveEx();
		//	Return ok
		return true;
	}
	
	/**
	 * Set values from maintenance
	 * @param maintenance
	 */
	public void setMaintenance(MAMMaintenance maintenance) {
		if(maintenance == null) {
			return;
		}
		setAM_Maintenance_ID(maintenance.getAM_Maintenance_ID());
		setAM_Area_ID(maintenance.getAM_Area_ID());
		if(maintenance.getAM_Pattern_ID() != 0) {
			setAM_Pattern_ID(maintenance.getAM_Pattern_ID());
		}
		setAD_User_ID(maintenance.getAD_User_ID());
		addDescription(maintenance.getDescription());
	}
	
	/**
	 * Set values from pattern
	 * @param pattern
	 */
	public void setPattern(MAMPattern pattern) {
		if(pattern == null) {
			return;
		}
		setAM_Pattern_ID(pattern.getAM_Pattern_ID());
		setAM_Area_ID(pattern.getAM_Area_ID());
		addDescription(pattern.getDescription());
	}

	@Override
	public String toString() {
		return "MAMServiceOrder [getAM_ServiceOrder_ID()=" + getAM_ServiceOrder_ID() + ", getDocumentNo()="
				+ getDocumentNo() + "]";
	}
}
