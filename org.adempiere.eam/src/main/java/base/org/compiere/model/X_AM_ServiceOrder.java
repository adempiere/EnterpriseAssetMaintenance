/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for AM_ServiceOrder
 *  @author Adempiere (generated) 
 *  @version 1.4.0 - $Id$ */
public class X_AM_ServiceOrder extends PO implements I_AM_ServiceOrder, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160517L;

    /** Standard Constructor */
    public X_AM_ServiceOrder (Properties ctx, int AM_ServiceOrder_ID, String trxName)
    {
      super (ctx, AM_ServiceOrder_ID, trxName);
      /** if (AM_ServiceOrder_ID == 0)
        {
			setA_Asset_ID (0);
			setAM_ServiceOrder_ID (0);
			setDateStartPlan (new Timestamp( System.currentTimeMillis() ));
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setIsApproved (false);
// N
			setProcessed (false);
        } */
    }

    /** Load Constructor */
    public X_AM_ServiceOrder (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_AM_ServiceOrder[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_A_Asset getA_Asset() throws RuntimeException
    {
		return (I_A_Asset)MTable.get(getCtx(), I_A_Asset.Table_Name)
			.getPO(getA_Asset_ID(), get_TrxName());	}

	/** Set Asset.
		@param A_Asset_ID 
		Asset used internally or by customers
	  */
	public void setA_Asset_ID (int A_Asset_ID)
	{
		if (A_Asset_ID < 1) 
			set_Value (COLUMNNAME_A_Asset_ID, null);
		else 
			set_Value (COLUMNNAME_A_Asset_ID, Integer.valueOf(A_Asset_ID));
	}

	/** Get Asset.
		@return Asset used internally or by customers
	  */
	public int getA_Asset_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_User getAD_User() throws RuntimeException
    {
		return (I_AD_User)MTable.get(getCtx(), I_AD_User.Table_Name)
			.getPO(getAD_User_ID(), get_TrxName());	}

	/** Set User/Contact.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 1) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get User/Contact.
		@return User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AM_Maintenance getAM_Maintenance() throws RuntimeException
    {
		return (I_AM_Maintenance)MTable.get(getCtx(), I_AM_Maintenance.Table_Name)
			.getPO(getAM_Maintenance_ID(), get_TrxName());	}

	/** Set AM Maintenance.
		@param AM_Maintenance_ID AM Maintenance	  */
	public void setAM_Maintenance_ID (int AM_Maintenance_ID)
	{
		if (AM_Maintenance_ID < 1) 
			set_Value (COLUMNNAME_AM_Maintenance_ID, null);
		else 
			set_Value (COLUMNNAME_AM_Maintenance_ID, Integer.valueOf(AM_Maintenance_ID));
	}

	/** Get AM Maintenance.
		@return AM Maintenance	  */
	public int getAM_Maintenance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AM_Maintenance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AM_MaintenancePattern getAM_MaintenancePattern() throws RuntimeException
    {
		return (I_AM_MaintenancePattern)MTable.get(getCtx(), I_AM_MaintenancePattern.Table_Name)
			.getPO(getAM_MaintenancePattern_ID(), get_TrxName());	}

	/** Set AM Maintenance Pattern.
		@param AM_MaintenancePattern_ID AM Maintenance Pattern	  */
	public void setAM_MaintenancePattern_ID (int AM_MaintenancePattern_ID)
	{
		if (AM_MaintenancePattern_ID < 1) 
			set_Value (COLUMNNAME_AM_MaintenancePattern_ID, null);
		else 
			set_Value (COLUMNNAME_AM_MaintenancePattern_ID, Integer.valueOf(AM_MaintenancePattern_ID));
	}

	/** Get AM Maintenance Pattern.
		@return AM Maintenance Pattern	  */
	public int getAM_MaintenancePattern_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AM_MaintenancePattern_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set AM ServiceOrder.
		@param AM_ServiceOrder_ID AM ServiceOrder	  */
	public void setAM_ServiceOrder_ID (int AM_ServiceOrder_ID)
	{
		if (AM_ServiceOrder_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AM_ServiceOrder_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AM_ServiceOrder_ID, Integer.valueOf(AM_ServiceOrder_ID));
	}

	/** Get AM ServiceOrder.
		@return AM ServiceOrder	  */
	public int getAM_ServiceOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AM_ServiceOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AM_ServiceOrder_Request getAM_ServiceOrder_Request() throws RuntimeException
    {
		return (I_AM_ServiceOrder_Request)MTable.get(getCtx(), I_AM_ServiceOrder_Request.Table_Name)
			.getPO(getAM_ServiceOrder_Request_ID(), get_TrxName());	}

	/** Set AM Service Order Request.
		@param AM_ServiceOrder_Request_ID AM Service Order Request	  */
	public void setAM_ServiceOrder_Request_ID (int AM_ServiceOrder_Request_ID)
	{
		if (AM_ServiceOrder_Request_ID < 1) 
			set_Value (COLUMNNAME_AM_ServiceOrder_Request_ID, null);
		else 
			set_Value (COLUMNNAME_AM_ServiceOrder_Request_ID, Integer.valueOf(AM_ServiceOrder_Request_ID));
	}

	/** Get AM Service Order Request.
		@return AM Service Order Request	  */
	public int getAM_ServiceOrder_Request_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AM_ServiceOrder_Request_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Activity getC_Activity() throws RuntimeException
    {
		return (I_C_Activity)MTable.get(getCtx(), I_C_Activity.Table_Name)
			.getPO(getC_Activity_ID(), get_TrxName());	}

	/** Set Activity.
		@param C_Activity_ID 
		Business Activity
	  */
	public void setC_Activity_ID (int C_Activity_ID)
	{
		if (C_Activity_ID < 1) 
			set_Value (COLUMNNAME_C_Activity_ID, null);
		else 
			set_Value (COLUMNNAME_C_Activity_ID, Integer.valueOf(C_Activity_ID));
	}

	/** Get Activity.
		@return Business Activity
	  */
	public int getC_Activity_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Activity_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_DocType getC_DocType() throws RuntimeException
    {
		return (I_C_DocType)MTable.get(getCtx(), I_C_DocType.Table_Name)
			.getPO(getC_DocType_ID(), get_TrxName());	}

	/** Set Document Type.
		@param C_DocType_ID 
		Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Document Type.
		@return Document type or rules
	  */
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Cost Value.
		@param CostAmt 
		Value with Cost
	  */
	public void setCostAmt (BigDecimal CostAmt)
	{
		set_Value (COLUMNNAME_CostAmt, CostAmt);
	}

	/** Get Cost Value.
		@return Value with Cost
	  */
	public BigDecimal getCostAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CostAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Cost Amt Plan.
		@param CostAmtPlan Cost Amt Plan	  */
	public void setCostAmtPlan (BigDecimal CostAmtPlan)
	{
		set_Value (COLUMNNAME_CostAmtPlan, CostAmtPlan);
	}

	/** Get Cost Amt Plan.
		@return Cost Amt Plan	  */
	public BigDecimal getCostAmtPlan () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CostAmtPlan);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Finish Date.
		@param DateFinish 
		Finish or (planned) completion date
	  */
	public void setDateFinish (Timestamp DateFinish)
	{
		set_Value (COLUMNNAME_DateFinish, DateFinish);
	}

	/** Get Finish Date.
		@return Finish or (planned) completion date
	  */
	public Timestamp getDateFinish () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateFinish);
	}

	/** Set Start Plan.
		@param DateStartPlan 
		Planned Start Date
	  */
	public void setDateStartPlan (Timestamp DateStartPlan)
	{
		set_Value (COLUMNNAME_DateStartPlan, DateStartPlan);
	}

	/** Get Start Plan.
		@return Planned Start Date
	  */
	public Timestamp getDateStartPlan () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateStartPlan);
	}

	/** Set Transaction Date.
		@param DateTrx 
		Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx)
	{
		set_Value (COLUMNNAME_DateTrx, DateTrx);
	}

	/** Get Transaction Date.
		@return Transaction Date
	  */
	public Timestamp getDateTrx () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTrx);
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** DocAction AD_Reference_ID=135 */
	public static final int DOCACTION_AD_Reference_ID=135;
	/** Complete = CO */
	public static final String DOCACTION_Complete = "CO";
	/** Approve = AP */
	public static final String DOCACTION_Approve = "AP";
	/** Reject = RJ */
	public static final String DOCACTION_Reject = "RJ";
	/** Post = PO */
	public static final String DOCACTION_Post = "PO";
	/** Void = VO */
	public static final String DOCACTION_Void = "VO";
	/** Close = CL */
	public static final String DOCACTION_Close = "CL";
	/** Reverse - Correct = RC */
	public static final String DOCACTION_Reverse_Correct = "RC";
	/** Reverse - Accrual = RA */
	public static final String DOCACTION_Reverse_Accrual = "RA";
	/** Invalidate = IN */
	public static final String DOCACTION_Invalidate = "IN";
	/** Re-activate = RE */
	public static final String DOCACTION_Re_Activate = "RE";
	/** <None> = -- */
	public static final String DOCACTION_None = "--";
	/** Prepare = PR */
	public static final String DOCACTION_Prepare = "PR";
	/** Unlock = XL */
	public static final String DOCACTION_Unlock = "XL";
	/** Wait Complete = WC */
	public static final String DOCACTION_WaitComplete = "WC";
	/** Set Document Action.
		@param DocAction 
		The targeted status of the document
	  */
	public void setDocAction (String DocAction)
	{

		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	/** Get Document Action.
		@return The targeted status of the document
	  */
	public String getDocAction () 
	{
		return (String)get_Value(COLUMNNAME_DocAction);
	}

	/** DocStatus AD_Reference_ID=131 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** Not Approved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** In Progress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** Waiting Payment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** Waiting Confirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	/** Set Document Status.
		@param DocStatus 
		The current status of the document
	  */
	public void setDocStatus (String DocStatus)
	{

		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Document Status.
		@return The current status of the document
	  */
	public String getDocStatus () 
	{
		return (String)get_Value(COLUMNNAME_DocStatus);
	}

	/** Set Document No.
		@param DocumentNo 
		Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getDocumentNo());
    }

	/** Set Comment/Help.
		@param Help 
		Comment or Hint
	  */
	public void setHelp (String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Comment/Help.
		@return Comment or Hint
	  */
	public String getHelp () 
	{
		return (String)get_Value(COLUMNNAME_Help);
	}

	/** Set Info.
		@param Info 
		Information
	  */
	public void setInfo (String Info)
	{
		set_Value (COLUMNNAME_Info, Info);
	}

	/** Get Info.
		@return Information
	  */
	public String getInfo () 
	{
		return (String)get_Value(COLUMNNAME_Info);
	}

	/** Set Approved.
		@param IsApproved 
		Indicates if this document requires approval
	  */
	public void setIsApproved (boolean IsApproved)
	{
		set_Value (COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
	}

	/** Get Approved.
		@return Indicates if this document requires approval
	  */
	public boolean isApproved () 
	{
		Object oo = get_Value(COLUMNNAME_IsApproved);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Service Order Voided.
		@param IsServiceOrderVoided Service Order Voided	  */
	public void setIsServiceOrderVoided (boolean IsServiceOrderVoided)
	{
		set_Value (COLUMNNAME_IsServiceOrderVoided, Boolean.valueOf(IsServiceOrderVoided));
	}

	/** Get Service Order Voided.
		@return Service Order Voided	  */
	public boolean isServiceOrderVoided () 
	{
		Object oo = get_Value(COLUMNNAME_IsServiceOrderVoided);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Time Real.
		@param IsTimeReal Time Real	  */
	public void setIsTimeReal (boolean IsTimeReal)
	{
		set_Value (COLUMNNAME_IsTimeReal, Boolean.valueOf(IsTimeReal));
	}

	/** Get Time Real.
		@return Time Real	  */
	public boolean isTimeReal () 
	{
		Object oo = get_Value(COLUMNNAME_IsTimeReal);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_M_Warehouse getM_Warehouse() throws RuntimeException
    {
		return (I_M_Warehouse)MTable.get(getCtx(), I_M_Warehouse.Table_Name)
			.getPO(getM_Warehouse_ID(), get_TrxName());	}

	/** Set Warehouse.
		@param M_Warehouse_ID 
		Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Warehouse.
		@return Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** MaintenanceArea AD_Reference_ID=53818 */
	public static final int MAINTENANCEAREA_AD_Reference_ID=53818;
	/** Electrical = EL */
	public static final String MAINTENANCEAREA_Electrical = "EL";
	/** General = GE */
	public static final String MAINTENANCEAREA_General = "GE";
	/** Mechanic = ME */
	public static final String MAINTENANCEAREA_Mechanic = "ME";
	/** Refrigeration = RE */
	public static final String MAINTENANCEAREA_Refrigeration = "RE";
	/** Set Maintenance Area.
		@param MaintenanceArea Maintenance Area	  */
	public void setMaintenanceArea (String MaintenanceArea)
	{

		set_Value (COLUMNNAME_MaintenanceArea, MaintenanceArea);
	}

	/** Get Maintenance Area.
		@return Maintenance Area	  */
	public String getMaintenanceArea () 
	{
		return (String)get_Value(COLUMNNAME_MaintenanceArea);
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Quantity Plan.
		@param QtyPlan 
		Planned Quantity
	  */
	public void setQtyPlan (BigDecimal QtyPlan)
	{
		set_Value (COLUMNNAME_QtyPlan, QtyPlan);
	}

	/** Get Quantity Plan.
		@return Planned Quantity
	  */
	public BigDecimal getQtyPlan () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyPlan);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Service Order Justification.
		@param ServiceOrderJustification Service Order Justification	  */
	public void setServiceOrderJustification (String ServiceOrderJustification)
	{
		set_Value (COLUMNNAME_ServiceOrderJustification, ServiceOrderJustification);
	}

	/** Get Service Order Justification.
		@return Service Order Justification	  */
	public String getServiceOrderJustification () 
	{
		return (String)get_Value(COLUMNNAME_ServiceOrderJustification);
	}

	/** ServiceOrderVoidingCause AD_Reference_ID=53831 */
	public static final int SERVICEORDERVOIDINGCAUSE_AD_Reference_ID=53831;
	/** By Production = 1 */
	public static final String SERVICEORDERVOIDINGCAUSE_ByProduction = "1";
	/** Lack of person available = 2 */
	public static final String SERVICEORDERVOIDINGCAUSE_LackOfPersonAvailable = "2";
	/** Machine out of service = 3 */
	public static final String SERVICEORDERVOIDINGCAUSE_MachineOutOfService = "3";
	/** Lack of spare = 4 */
	public static final String SERVICEORDERVOIDINGCAUSE_LackOfSpare = "4";
	/** Other = 5 */
	public static final String SERVICEORDERVOIDINGCAUSE_Other = "5";
	/** Set Service Order Voiding Cause .
		@param ServiceOrderVoidingCause Service Order Voiding Cause 	  */
	public void setServiceOrderVoidingCause (String ServiceOrderVoidingCause)
	{

		set_Value (COLUMNNAME_ServiceOrderVoidingCause, ServiceOrderVoidingCause);
	}

	/** Get Service Order Voiding Cause .
		@return Service Order Voiding Cause 	  */
	public String getServiceOrderVoidingCause () 
	{
		return (String)get_Value(COLUMNNAME_ServiceOrderVoidingCause);
	}

	public I_C_ElementValue getUser1() throws RuntimeException
    {
		return (I_C_ElementValue)MTable.get(getCtx(), I_C_ElementValue.Table_Name)
			.getPO(getUser1_ID(), get_TrxName());	}

	/** Set User List 1.
		@param User1_ID 
		User defined list element #1
	  */
	public void setUser1_ID (int User1_ID)
	{
		if (User1_ID < 1) 
			set_Value (COLUMNNAME_User1_ID, null);
		else 
			set_Value (COLUMNNAME_User1_ID, Integer.valueOf(User1_ID));
	}

	/** Get User List 1.
		@return User defined list element #1
	  */
	public int getUser1_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_User1_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}