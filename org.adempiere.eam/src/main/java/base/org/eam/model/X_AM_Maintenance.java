/******************************************************************************
 * Product: ADempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2006-2017 ADempiere Foundation, All Rights Reserved.         *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * or (at your option) any later version.										*
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * or via info@adempiere.net or http://www.adempiere.net/license.html         *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.eam.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for AM_Maintenance
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_AM_Maintenance extends PO implements I_AM_Maintenance, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20171004L;

    /** Standard Constructor */
    public X_AM_Maintenance (Properties ctx, int AM_Maintenance_ID, String trxName)
    {
      super (ctx, AM_Maintenance_ID, trxName);
      /** if (AM_Maintenance_ID == 0)
        {
			setAM_Maintenance_ID (0);
			setDocStatus (null);
			setDocumentNo (null);
			setInterval (Env.ZERO);
			setPriorityRule (null);
			setProgrammingType (null);
        } */
    }

    /** Load Constructor */
    public X_AM_Maintenance (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_AM_Maintenance[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_A_Asset_Group getA_Asset_Group() throws RuntimeException
    {
		return (org.compiere.model.I_A_Asset_Group)MTable.get(getCtx(), org.compiere.model.I_A_Asset_Group.Table_Name)
			.getPO(getA_Asset_Group_ID(), get_TrxName());	}

	/** Set Asset Group.
		@param A_Asset_Group_ID 
		Group of Assets
	  */
	public void setA_Asset_Group_ID (int A_Asset_Group_ID)
	{
		if (A_Asset_Group_ID < 1) 
			set_Value (COLUMNNAME_A_Asset_Group_ID, null);
		else 
			set_Value (COLUMNNAME_A_Asset_Group_ID, Integer.valueOf(A_Asset_Group_ID));
	}

	/** Get Asset Group.
		@return Group of Assets
	  */
	public int getA_Asset_Group_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_Group_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_A_Asset getA_Asset() throws RuntimeException
    {
		return (org.compiere.model.I_A_Asset)MTable.get(getCtx(), org.compiere.model.I_A_Asset.Table_Name)
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

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
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

	public org.eam.model.I_AM_Area getAM_Area() throws RuntimeException
    {
		return (org.eam.model.I_AM_Area)MTable.get(getCtx(), org.eam.model.I_AM_Area.Table_Name)
			.getPO(getAM_Area_ID(), get_TrxName());	}

	/** Set Maintenance Area.
		@param AM_Area_ID Maintenance Area	  */
	public void setAM_Area_ID (int AM_Area_ID)
	{
		if (AM_Area_ID < 1) 
			set_Value (COLUMNNAME_AM_Area_ID, null);
		else 
			set_Value (COLUMNNAME_AM_Area_ID, Integer.valueOf(AM_Area_ID));
	}

	/** Get Maintenance Area.
		@return Maintenance Area	  */
	public int getAM_Area_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AM_Area_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set AM Maintenance.
		@param AM_Maintenance_ID AM Maintenance	  */
	public void setAM_Maintenance_ID (int AM_Maintenance_ID)
	{
		if (AM_Maintenance_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AM_Maintenance_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AM_Maintenance_ID, Integer.valueOf(AM_Maintenance_ID));
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

	public org.eam.model.I_AM_Maintenance getAM_MaintenanceParent() throws RuntimeException
    {
		return (org.eam.model.I_AM_Maintenance)MTable.get(getCtx(), org.eam.model.I_AM_Maintenance.Table_Name)
			.getPO(getAM_MaintenanceParent_ID(), get_TrxName());	}

	/** Set AM Maintenance Parent.
		@param AM_MaintenanceParent_ID AM Maintenance Parent	  */
	public void setAM_MaintenanceParent_ID (int AM_MaintenanceParent_ID)
	{
		if (AM_MaintenanceParent_ID < 1) 
			set_Value (COLUMNNAME_AM_MaintenanceParent_ID, null);
		else 
			set_Value (COLUMNNAME_AM_MaintenanceParent_ID, Integer.valueOf(AM_MaintenanceParent_ID));
	}

	/** Get AM Maintenance Parent.
		@return AM Maintenance Parent	  */
	public int getAM_MaintenanceParent_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AM_MaintenanceParent_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.eam.model.I_AM_Meter getAM_Meter() throws RuntimeException
    {
		return (org.eam.model.I_AM_Meter)MTable.get(getCtx(), org.eam.model.I_AM_Meter.Table_Name)
			.getPO(getAM_Meter_ID(), get_TrxName());	}

	/** Set AM Meter.
		@param AM_Meter_ID AM Meter	  */
	public void setAM_Meter_ID (int AM_Meter_ID)
	{
		if (AM_Meter_ID < 1) 
			set_Value (COLUMNNAME_AM_Meter_ID, null);
		else 
			set_Value (COLUMNNAME_AM_Meter_ID, Integer.valueOf(AM_Meter_ID));
	}

	/** Get AM Meter.
		@return AM Meter	  */
	public int getAM_Meter_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AM_Meter_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.eam.model.I_AM_Pattern getAM_Pattern() throws RuntimeException
    {
		return (org.eam.model.I_AM_Pattern)MTable.get(getCtx(), org.eam.model.I_AM_Pattern.Table_Name)
			.getPO(getAM_Pattern_ID(), get_TrxName());	}

	/** Set AM Maintenance Pattern.
		@param AM_Pattern_ID AM Maintenance Pattern	  */
	public void setAM_Pattern_ID (int AM_Pattern_ID)
	{
		if (AM_Pattern_ID < 1) 
			set_Value (COLUMNNAME_AM_Pattern_ID, null);
		else 
			set_Value (COLUMNNAME_AM_Pattern_ID, Integer.valueOf(AM_Pattern_ID));
	}

	/** Get AM Maintenance Pattern.
		@return AM Maintenance Pattern	  */
	public int getAM_Pattern_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AM_Pattern_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Average Use.
		@param AverageUse Average Use	  */
	public void setAverageUse (BigDecimal AverageUse)
	{
		set_Value (COLUMNNAME_AverageUse, AverageUse);
	}

	/** Get Average Use.
		@return Average Use	  */
	public BigDecimal getAverageUse () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AverageUse);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** CalendarType AD_Reference_ID=53823 */
	public static final int CALENDARTYPE_AD_Reference_ID=53823;
	/** Daily = D */
	public static final String CALENDARTYPE_Daily = "D";
	/** MonthlyFixed = M */
	public static final String CALENDARTYPE_MonthlyFixed = "M";
	/** MonthlyRepetitive = R */
	public static final String CALENDARTYPE_MonthlyRepetitive = "R";
	/** Weekly = W */
	public static final String CALENDARTYPE_Weekly = "W";
	/** Yearly = Y */
	public static final String CALENDARTYPE_Yearly = "Y";
	/** Set AM Calendar Type.
		@param CalendarType AM Calendar Type	  */
	public void setCalendarType (String CalendarType)
	{

		set_Value (COLUMNNAME_CalendarType, CalendarType);
	}

	/** Get AM Calendar Type.
		@return AM Calendar Type	  */
	public String getCalendarType () 
	{
		return (String)get_Value(COLUMNNAME_CalendarType);
	}

	/** Set Copy From.
		@param CopyFrom 
		Copy From Record
	  */
	public void setCopyFrom (String CopyFrom)
	{
		set_Value (COLUMNNAME_CopyFrom, CopyFrom);
	}

	/** Get Copy From.
		@return Copy From Record
	  */
	public String getCopyFrom () 
	{
		return (String)get_Value(COLUMNNAME_CopyFrom);
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

	/** Set CurrentAM.
		@param CurrentAM CurrentAM	  */
	public void setCurrentAM (BigDecimal CurrentAM)
	{
		set_Value (COLUMNNAME_CurrentAM, CurrentAM);
	}

	/** Get CurrentAM.
		@return CurrentAM	  */
	public BigDecimal getCurrentAM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CurrentAM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Date last run.
		@param DateLastRun 
		Date the process was last run.
	  */
	public void setDateLastRun (Timestamp DateLastRun)
	{
		set_Value (COLUMNNAME_DateLastRun, DateLastRun);
	}

	/** Get Date last run.
		@return Date the process was last run.
	  */
	public Timestamp getDateLastRun () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateLastRun);
	}

	/** Set Date Last Run AM.
		@param DateLastRunAM Date Last Run AM	  */
	public void setDateLastRunAM (Timestamp DateLastRunAM)
	{
		set_Value (COLUMNNAME_DateLastRunAM, DateLastRunAM);
	}

	/** Get Date Last Run AM.
		@return Date Last Run AM	  */
	public Timestamp getDateLastRunAM () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateLastRunAM);
	}

	/** Set Date Last Service Order.
		@param DateLastSO Date Last Service Order	  */
	public void setDateLastSO (Timestamp DateLastSO)
	{
		set_Value (COLUMNNAME_DateLastSO, DateLastSO);
	}

	/** Get Date Last Service Order.
		@return Date Last Service Order	  */
	public Timestamp getDateLastSO () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateLastSO);
	}

	/** Set Date next run.
		@param DateNextRun 
		Date the process will run next
	  */
	public void setDateNextRun (Timestamp DateNextRun)
	{
		set_Value (COLUMNNAME_DateNextRun, DateNextRun);
	}

	/** Get Date next run.
		@return Date the process will run next
	  */
	public Timestamp getDateNextRun () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateNextRun);
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

	/** DocStatus AD_Reference_ID=53822 */
	public static final int DOCSTATUS_AD_Reference_ID=53822;
	/** Critical = AC */
	public static final String DOCSTATUS_Critical = "AC";
	/** Active = AT */
	public static final String DOCSTATUS_Active = "AT";
	/** InActive = IT */
	public static final String DOCSTATUS_InActive = "IT";
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

	/** Set Interval.
		@param Interval Interval	  */
	public void setInterval (BigDecimal Interval)
	{
		set_Value (COLUMNNAME_Interval, Interval);
	}

	/** Get Interval.
		@return Interval	  */
	public BigDecimal getInterval () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Interval);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Child.
		@param IsChild Child	  */
	public void setIsChild (boolean IsChild)
	{
		set_Value (COLUMNNAME_IsChild, Boolean.valueOf(IsChild));
	}

	/** Get Child.
		@return Child	  */
	public boolean isChild () 
	{
		Object oo = get_Value(COLUMNNAME_IsChild);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Friday.
		@param IsFriday Friday	  */
	public void setIsFriday (boolean IsFriday)
	{
		set_Value (COLUMNNAME_IsFriday, Boolean.valueOf(IsFriday));
	}

	/** Get Friday.
		@return Friday	  */
	public boolean isFriday () 
	{
		Object oo = get_Value(COLUMNNAME_IsFriday);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Monday.
		@param IsMonday Monday	  */
	public void setIsMonday (boolean IsMonday)
	{
		set_Value (COLUMNNAME_IsMonday, Boolean.valueOf(IsMonday));
	}

	/** Get Monday.
		@return Monday	  */
	public boolean isMonday () 
	{
		Object oo = get_Value(COLUMNNAME_IsMonday);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Saturday.
		@param IsSaturday Saturday	  */
	public void setIsSaturday (boolean IsSaturday)
	{
		set_Value (COLUMNNAME_IsSaturday, Boolean.valueOf(IsSaturday));
	}

	/** Get Saturday.
		@return Saturday	  */
	public boolean isSaturday () 
	{
		Object oo = get_Value(COLUMNNAME_IsSaturday);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Sunday.
		@param IsSunday Sunday	  */
	public void setIsSunday (boolean IsSunday)
	{
		set_Value (COLUMNNAME_IsSunday, Boolean.valueOf(IsSunday));
	}

	/** Get Sunday.
		@return Sunday	  */
	public boolean isSunday () 
	{
		Object oo = get_Value(COLUMNNAME_IsSunday);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Thursday.
		@param IsThursday Thursday	  */
	public void setIsThursday (boolean IsThursday)
	{
		set_Value (COLUMNNAME_IsThursday, Boolean.valueOf(IsThursday));
	}

	/** Get Thursday.
		@return Thursday	  */
	public boolean isThursday () 
	{
		Object oo = get_Value(COLUMNNAME_IsThursday);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Tuesday.
		@param IsTuesday Tuesday	  */
	public void setIsTuesday (boolean IsTuesday)
	{
		set_Value (COLUMNNAME_IsTuesday, Boolean.valueOf(IsTuesday));
	}

	/** Get Tuesday.
		@return Tuesday	  */
	public boolean isTuesday () 
	{
		Object oo = get_Value(COLUMNNAME_IsTuesday);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Wednesday.
		@param IsWednesday Wednesday	  */
	public void setIsWednesday (boolean IsWednesday)
	{
		set_Value (COLUMNNAME_IsWednesday, Boolean.valueOf(IsWednesday));
	}

	/** Get Wednesday.
		@return Wednesday	  */
	public boolean isWednesday () 
	{
		Object oo = get_Value(COLUMNNAME_IsWednesday);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Last AM.
		@param LastAM Last AM	  */
	public void setLastAM (BigDecimal LastAM)
	{
		set_Value (COLUMNNAME_LastAM, LastAM);
	}

	/** Get Last AM.
		@return Last AM	  */
	public BigDecimal getLastAM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LastAM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Last Read.
		@param LastRead Last Read	  */
	public void setLastRead (BigDecimal LastRead)
	{
		set_Value (COLUMNNAME_LastRead, LastRead);
	}

	/** Get Last Read.
		@return Last Read	  */
	public BigDecimal getLastRead () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LastRead);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set NextAM.
		@param NextAM NextAM	  */
	public void setNextAM (BigDecimal NextAM)
	{
		set_Value (COLUMNNAME_NextAM, NextAM);
	}

	/** Get NextAM.
		@return NextAM	  */
	public BigDecimal getNextAM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NextAM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** PriorityRule AD_Reference_ID=154 */
	public static final int PRIORITYRULE_AD_Reference_ID=154;
	/** High = 3 */
	public static final String PRIORITYRULE_High = "3";
	/** Medium = 5 */
	public static final String PRIORITYRULE_Medium = "5";
	/** Low = 7 */
	public static final String PRIORITYRULE_Low = "7";
	/** Urgent = 1 */
	public static final String PRIORITYRULE_Urgent = "1";
	/** Minor = 9 */
	public static final String PRIORITYRULE_Minor = "9";
	/** Set Priority.
		@param PriorityRule 
		Priority of a document
	  */
	public void setPriorityRule (String PriorityRule)
	{

		set_Value (COLUMNNAME_PriorityRule, PriorityRule);
	}

	/** Get Priority.
		@return Priority of a document
	  */
	public String getPriorityRule () 
	{
		return (String)get_Value(COLUMNNAME_PriorityRule);
	}

	/** ProgrammingType AD_Reference_ID=53827 */
	public static final int PROGRAMMINGTYPE_AD_Reference_ID=53827;
	/** Calendar = C */
	public static final String PROGRAMMINGTYPE_Calendar = "C";
	/** Meter = M */
	public static final String PROGRAMMINGTYPE_Meter = "M";
	/** Set AM Programming Type.
		@param ProgrammingType AM Programming Type	  */
	public void setProgrammingType (String ProgrammingType)
	{

		set_Value (COLUMNNAME_ProgrammingType, ProgrammingType);
	}

	/** Get AM Programming Type.
		@return AM Programming Type	  */
	public String getProgrammingType () 
	{
		return (String)get_Value(COLUMNNAME_ProgrammingType);
	}

	/** Set Range.
		@param Range Range	  */
	public void setRange (BigDecimal Range)
	{
		set_Value (COLUMNNAME_Range, Range);
	}

	/** Get Range.
		@return Range	  */
	public BigDecimal getRange () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Range);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}