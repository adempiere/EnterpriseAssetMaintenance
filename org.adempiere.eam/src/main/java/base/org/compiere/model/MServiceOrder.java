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
package org.compiere.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;

import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.Env;
import org.compiere.util.Msg;

/**
 * Service Order (Work Order)
 * 
 * @author Sachin Bhimani
 */
public class MServiceOrder extends X_AM_ServiceOrder implements DocAction
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 152203887207801137L;
	/** Process Message */
	private String				m_processMsg		= null;
	/** Just Prepared Flag */
	private boolean				m_justPrepared		= false;

	public MServiceOrder(Properties ctx, int AM_ServiceOrder_ID, String trxName)
	{
		super(ctx, AM_ServiceOrder_ID, trxName);
	}

	public MServiceOrder(Properties ctx, ResultSet rs, String trxName)
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
	public String prepareIt()
	{
		log.info(toString());
		m_justPrepared = true;

		// the Service Order starts officially with Action "In Process"
		if (DOCACTION_Prepare.equals(getDocAction()))
		{
			Date today = new java.util.Date();
			Timestamp now = new Timestamp(today.getTime());
			this.setDateTrx(now);
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

		setProcessed(true);
		setDocAction(DOCACTION_Close);

		Date today = new java.util.Date();
		Timestamp now = new Timestamp(today.getTime());

		// update Preventive Maintenance's Field "Date Last Work Order"
		if (this.getAM_Maintenance_ID() != 0)
		{
			X_AM_Maintenance mp = new X_AM_Maintenance(Env.getCtx(), this.getAM_Maintenance_ID(), this.get_TrxName());

			// update Preventive Maintenance with real or Work Order's official
			// time?
			if (this.isTimeReal())
				mp.setDateLastSO(now);
			else
				mp.setDateLastSO(this.getDateStartPlan());
			mp.saveEx();
		}

		// update Asset's Last Maintenance date
		X_A_Asset ass = new X_A_Asset(Env.getCtx(), this.getA_Asset_ID(), this.get_TrxName());
		if (ass != null)
		{
			// update Asset: with real or Work order's official time?
			if (this.isTimeReal())
				ass.setLastMaintenanceDate(now);
			else
				ass.setLastMaintenanceDate(this.getDateStartPlan());
			ass.saveEx();
		}

		// update forecast
		MPrognosis forecast = MPrognosis.getByServiceOrder_ID(Env.getCtx(), this.getAM_ServiceOrder_ID());
		if (forecast != null)
		{
			// update Forecast: with real or Work Order's official time?
			if (this.isTimeReal())
				forecast.setLastMaintenanceDate(now);
			else
				forecast.setLastMaintenanceDate(this.getDateStartPlan());
			forecast.saveEx();
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
		return false;
	} // reActivateIt

	/**
	 * Void Document.
	 * 
	 * @return false
	 */
	public boolean voidIt()
	{
		log.info("voidIt - " + toString());
		return false;
	} // voidIt

	/*************************************************************************
	 * Get Summary
	 * 
	 * @return Summary of Document
	 */
	public String getSummary()
	{
		return "No Summary";
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
	public BigDecimal getSOCosts()
	{
		X_AM_ServiceOrder_Task[] tasks = MServiceOrder_Task.getSOTasks(this);
		BigDecimal workOrderCosts = BigDecimal.ZERO;
		for (X_AM_ServiceOrder_Task task : tasks)
		{
			workOrderCosts = workOrderCosts.add(task.getCostAmt());
		}
		return workOrderCosts;
	} // getSOCosts

	/**
	 * update the costs of a SO
	 */
	public void updateSOCosts()
	{
		this.setCostAmt(getSOCosts());
		this.saveEx();
	} // updateSOCosts

}
