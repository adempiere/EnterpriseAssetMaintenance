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
import java.util.Properties;

import org.compiere.model.MDocType;
import org.compiere.model.MPeriod;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;

/**
 * Service Order Request
 * @author OFB Consulting http://www.ofbconsulting.com
 * 	<li> Initial Contributor
 * @contributor Mario Calderon, Systemhaus Westfalia, http://www.westfalia-it.com
 * @contributor Adaxa http://www.adaxa.com
 * @contributor Deepak Pansheriya, Loglite Technologies, http://logilite.com
 * @contributor Victor Perez, victor.perez@e-evolution.com, eEvolution http://www.e-evolution.com
 * @contributor Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 * @contributor Sachin Bhimani
 */
public class MAMServiceRequest extends X_AM_ServiceRequest implements DocAction, DocOptions
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -6383613040883826726L;
	/** Process Message */
	private String				m_processMsg		= null;
	/** Just Prepared Flag */
	private boolean				m_justPrepared		= false;

	public MAMServiceRequest(Properties ctx, int AM_ServiceOrder_Request_ID, String trxName)
	{
		super(ctx, AM_ServiceOrder_Request_ID, trxName);
	}

	public MAMServiceRequest(Properties ctx, ResultSet rs, String trxName)
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
		return Msg.getElement(getCtx(), "AM_ServiceRequest_ID") + " " + getDocumentNo();
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
		if (!MPeriod.isOpen(getCtx(), getDateDoc(), dt.getDocBaseType(), getAD_Org_ID())) {
			m_processMsg = "@PeriodClosed@";
			return DocAction.STATUS_Invalid;
		}
		if (!DOCACTION_Complete.equals(getDocAction()))
			setDocAction(DOCACTION_Complete);
		return DocAction.STATUS_InProgress;
	} // prepareIt

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
	 * @return new status (Complete, In Progress, Invalid, Waiting...)
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
		
		//	Definitive Document No
		setDefiniteDocumentNo();

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

		setProcessed(true);
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	} // completeIt

	/**
	 * 	Set the definite document number after completed
	 */
	private void setDefiniteDocumentNo() {
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		if (dt.isOverwriteDateOnComplete()) {
			setDateDoc(new Timestamp (System.currentTimeMillis()));
		}
		if (dt.isOverwriteSeqOnComplete()) {
			String value = DB.getDocumentNo(getC_DocType_ID(), get_TrxName(), true, this);
			if (value != null)
				setDocumentNo(value);
		}
	}
	
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
	 * @return false
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
		m_processMsg = validateReference();
		return !(m_processMsg != null);
	} // reActivateIt

	/**
	 * Void Document.
	 * 
	 * @return false
	 */
	public boolean voidIt()
	{
		log.info("voidIt - " + toString());
		m_processMsg = validateReference();
		return !(m_processMsg != null);
	} // voidIt

	/*************************************************************************
	 * Get Summary
	 * 
	 * @return Summary of Document
	 */
	public String getSummary()
	{
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
	public int getC_Currency_ID()
	{
		Integer ii = (Integer) get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	} // getC_Currency_ID

	public BigDecimal getApprovalAmt()
	{
		BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_GrandTotal);
		if (bd == null)
			return Env.ZERO;
		return bd;
	} // getApprovalAmt
	
	/**
	 * Validate reference to service order
	 * @return
	 */
	private String validateReference() {
		String referenceNo = DB.getSQLValueString(get_TrxName(), "SELECT so.DocumentNo "
				+ "FROM AM_ServiceOrder so "
				+ "WHERE so.DocStatus IN('CO', 'CL') "
				+ "AND so.AM_ServiceRequest_ID = ?", getAM_ServiceRequest_ID());
		//	Validate
		if(referenceNo != null) {
			return "@SQLErrorReferenced@ @AM_ServiceOrder_ID@ " + referenceNo;
		}
		//	
		return null;
	}
	
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
				options[index++] = DocumentEngine.ACTION_ReActivate;
				options[index++] = DocumentEngine.ACTION_Void;
				options[index++] = DocumentEngine.ACTION_Close;
			} else {
				options[index++] = DocumentEngine.ACTION_None;
			}
		}
		return index;
	}
}
