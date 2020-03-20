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
 *  @version Release 3.9.3 - $Id$ */
public class X_AM_Maintenance extends PO implements I_AM_Maintenance, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20200320L;

    /** Standard Constructor */
    public X_AM_Maintenance (Properties ctx, int AM_Maintenance_ID, String trxName)
    {
      super (ctx, AM_Maintenance_ID, trxName);
      /** if (AM_Maintenance_ID == 0)
        {
			setAM_Area_ID (0);
			setAM_Maintenance_ID (0);
			setDocumentNo (null);
			setInterval (Env.ZERO);
			setMaintenanceType (null);
			setPriorityRule (null);
			setStatus (null);
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

	public org.compiere.model.I_A_Asset getA_Asset() throws RuntimeException
    {
		return (org.compiere.model.I_A_Asset)MTable.get(getCtx(), org.compiere.model.I_A_Asset.Table_Name)
			.getPO(getA_Asset_ID(), get_TrxName());	}

	/** Set Fixed Asset.
		@param A_Asset_ID 
		Fixed Asset used internally or by customers
	  */
	public void setA_Asset_ID (int A_Asset_ID)
	{
		if (A_Asset_ID < 1) 
			set_Value (COLUMNNAME_A_Asset_ID, null);
		else 
			set_Value (COLUMNNAME_A_Asset_ID, Integer.valueOf(A_Asset_ID));
	}

	/** Get Fixed Asset.
		@return Fixed Asset used internally or by customers
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
		@param AM_Area_ID 
		Maintenance Area where will be process a work order
	  */
	public void setAM_Area_ID (int AM_Area_ID)
	{
		if (AM_Area_ID < 1) 
			set_Value (COLUMNNAME_AM_Area_ID, null);
		else 
			set_Value (COLUMNNAME_AM_Area_ID, Integer.valueOf(AM_Area_ID));
	}

	/** Get Maintenance Area.
		@return Maintenance Area where will be process a work order
	  */
	public int getAM_Area_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AM_Area_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Asset Maintenance.
		@param AM_Maintenance_ID 
		Define a maintenance program assigned to Asset
	  */
	public void setAM_Maintenance_ID (int AM_Maintenance_ID)
	{
		if (AM_Maintenance_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AM_Maintenance_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AM_Maintenance_ID, Integer.valueOf(AM_Maintenance_ID));
	}

	/** Get Asset Maintenance.
		@return Define a maintenance program assigned to Asset
	  */
	public int getAM_Maintenance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AM_Maintenance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.eam.model.I_AM_Meter getAM_Meter() throws RuntimeException
    {
		return (org.eam.model.I_AM_Meter)MTable.get(getCtx(), org.eam.model.I_AM_Meter.Table_Name)
			.getPO(getAM_Meter_ID(), get_TrxName());	}

	/** Set Meter.
		@param AM_Meter_ID 
		Asset meter master
	  */
	public void setAM_Meter_ID (int AM_Meter_ID)
	{
		if (AM_Meter_ID < 1) 
			set_Value (COLUMNNAME_AM_Meter_ID, null);
		else 
			set_Value (COLUMNNAME_AM_Meter_ID, Integer.valueOf(AM_Meter_ID));
	}

	/** Get Meter.
		@return Asset meter master
	  */
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

	/** Set Maintenance Pattern.
		@param AM_Pattern_ID 
		Maintenance Pattern or template for maintenance
	  */
	public void setAM_Pattern_ID (int AM_Pattern_ID)
	{
		if (AM_Pattern_ID < 1) 
			set_Value (COLUMNNAME_AM_Pattern_ID, null);
		else 
			set_Value (COLUMNNAME_AM_Pattern_ID, Integer.valueOf(AM_Pattern_ID));
	}

	/** Get Maintenance Pattern.
		@return Maintenance Pattern or template for maintenance
	  */
	public int getAM_Pattern_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AM_Pattern_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Average Use.
		@param AverageUse 
		Average Use for Asset
	  */
	public void setAverageUse (BigDecimal AverageUse)
	{
		set_Value (COLUMNNAME_AverageUse, AverageUse);
	}

	/** Get Average Use.
		@return Average Use for Asset
	  */
	public BigDecimal getAverageUse () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AverageUse);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Comments.
		@param Comments 
		Comments or additional information
	  */
	public void setComments (String Comments)
	{
		set_Value (COLUMNNAME_Comments, Comments);
	}

	/** Get Comments.
		@return Comments or additional information
	  */
	public String getComments () 
	{
		return (String)get_Value(COLUMNNAME_Comments);
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

	/** Set Date Last Service Order.
		@param DateLastServiceOrder Date Last Service Order	  */
	public void setDateLastServiceOrder (Timestamp DateLastServiceOrder)
	{
		set_Value (COLUMNNAME_DateLastServiceOrder, DateLastServiceOrder);
	}

	/** Get Date Last Service Order.
		@return Date Last Service Order	  */
	public Timestamp getDateLastServiceOrder () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateLastServiceOrder);
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

	/** FrequencyType AD_Reference_ID=221 */
	public static final int FREQUENCYTYPE_AD_Reference_ID=221;
	/** Minute = M */
	public static final String FREQUENCYTYPE_Minute = "M";
	/** Hour = H */
	public static final String FREQUENCYTYPE_Hour = "H";
	/** Day = D */
	public static final String FREQUENCYTYPE_Day = "D";
	/** Biweekly = B */
	public static final String FREQUENCYTYPE_Biweekly = "B";
	/** Monthly = N */
	public static final String FREQUENCYTYPE_Monthly = "N";
	/** Quarterly = Q */
	public static final String FREQUENCYTYPE_Quarterly = "Q";
	/** Weekly = W */
	public static final String FREQUENCYTYPE_Weekly = "W";
	/** Yearly = Y */
	public static final String FREQUENCYTYPE_Yearly = "Y";
	/** Set Frequency Type.
		@param FrequencyType 
		Frequency of event
	  */
	public void setFrequencyType (String FrequencyType)
	{

		set_Value (COLUMNNAME_FrequencyType, FrequencyType);
	}

	/** Get Frequency Type.
		@return Frequency of event
	  */
	public String getFrequencyType () 
	{
		return (String)get_Value(COLUMNNAME_FrequencyType);
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

	/** Set Last Measuring.
		@param LastMeasuring 
		Last Measuring used from last maintenance
	  */
	public void setLastMeasuring (BigDecimal LastMeasuring)
	{
		set_Value (COLUMNNAME_LastMeasuring, LastMeasuring);
	}

	/** Get Last Measuring.
		@return Last Measuring used from last maintenance
	  */
	public BigDecimal getLastMeasuring () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LastMeasuring);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** MaintenanceType AD_Reference_ID=54201 */
	public static final int MAINTENANCETYPE_AD_Reference_ID=54201;
	/** Calendar-Based Maintenance = CBM */
	public static final String MAINTENANCETYPE_Calendar_BasedMaintenance = "CBM";
	/** Measuring-Based Maintenance = MBM */
	public static final String MAINTENANCETYPE_Measuring_BasedMaintenance = "MBM";
	/** Set Maintenance Type.
		@param MaintenanceType 
		Maintenance Type define maintenance method for schedule
	  */
	public void setMaintenanceType (String MaintenanceType)
	{

		set_Value (COLUMNNAME_MaintenanceType, MaintenanceType);
	}

	/** Get Maintenance Type.
		@return Maintenance Type define maintenance method for schedule
	  */
	public String getMaintenanceType () 
	{
		return (String)get_Value(COLUMNNAME_MaintenanceType);
	}

	/** Set Next Measuring.
		@param NextMeasuring 
		Next value for meter of maintenance
	  */
	public void setNextMeasuring (BigDecimal NextMeasuring)
	{
		set_Value (COLUMNNAME_NextMeasuring, NextMeasuring);
	}

	/** Get Next Measuring.
		@return Next value for meter of maintenance
	  */
	public BigDecimal getNextMeasuring () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NextMeasuring);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Friday.
		@param OnFriday 
		Available on Fridays
	  */
	public void setOnFriday (boolean OnFriday)
	{
		set_Value (COLUMNNAME_OnFriday, Boolean.valueOf(OnFriday));
	}

	/** Get Friday.
		@return Available on Fridays
	  */
	public boolean isOnFriday () 
	{
		Object oo = get_Value(COLUMNNAME_OnFriday);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Monday.
		@param OnMonday 
		Available on Mondays
	  */
	public void setOnMonday (boolean OnMonday)
	{
		set_Value (COLUMNNAME_OnMonday, Boolean.valueOf(OnMonday));
	}

	/** Get Monday.
		@return Available on Mondays
	  */
	public boolean isOnMonday () 
	{
		Object oo = get_Value(COLUMNNAME_OnMonday);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Saturday.
		@param OnSaturday 
		Available on Saturday
	  */
	public void setOnSaturday (boolean OnSaturday)
	{
		set_Value (COLUMNNAME_OnSaturday, Boolean.valueOf(OnSaturday));
	}

	/** Get Saturday.
		@return Available on Saturday
	  */
	public boolean isOnSaturday () 
	{
		Object oo = get_Value(COLUMNNAME_OnSaturday);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Sunday.
		@param OnSunday 
		Available on Sundays
	  */
	public void setOnSunday (boolean OnSunday)
	{
		set_Value (COLUMNNAME_OnSunday, Boolean.valueOf(OnSunday));
	}

	/** Get Sunday.
		@return Available on Sundays
	  */
	public boolean isOnSunday () 
	{
		Object oo = get_Value(COLUMNNAME_OnSunday);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Thursday.
		@param OnThursday 
		Available on Thursdays
	  */
	public void setOnThursday (boolean OnThursday)
	{
		set_Value (COLUMNNAME_OnThursday, Boolean.valueOf(OnThursday));
	}

	/** Get Thursday.
		@return Available on Thursdays
	  */
	public boolean isOnThursday () 
	{
		Object oo = get_Value(COLUMNNAME_OnThursday);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Tuesday.
		@param OnTuesday 
		Available on Tuesdays
	  */
	public void setOnTuesday (boolean OnTuesday)
	{
		set_Value (COLUMNNAME_OnTuesday, Boolean.valueOf(OnTuesday));
	}

	/** Get Tuesday.
		@return Available on Tuesdays
	  */
	public boolean isOnTuesday () 
	{
		Object oo = get_Value(COLUMNNAME_OnTuesday);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Wednesday.
		@param OnWednesday 
		Available on Wednesdays
	  */
	public void setOnWednesday (boolean OnWednesday)
	{
		set_Value (COLUMNNAME_OnWednesday, Boolean.valueOf(OnWednesday));
	}

	/** Get Wednesday.
		@return Available on Wednesdays
	  */
	public boolean isOnWednesday () 
	{
		Object oo = get_Value(COLUMNNAME_OnWednesday);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Status AD_Reference_ID=53822 */
	public static final int STATUS_AD_Reference_ID=53822;
	/** Critical = AC */
	public static final String STATUS_Critical = "AC";
	/** Active = AT */
	public static final String STATUS_Active = "AT";
	/** InActive = IT */
	public static final String STATUS_InActive = "IT";
	/** Set Status.
		@param Status 
		Status of the currently running check
	  */
	public void setStatus (String Status)
	{

		set_Value (COLUMNNAME_Status, Status);
	}

	/** Get Status.
		@return Status of the currently running check
	  */
	public String getStatus () 
	{
		return (String)get_Value(COLUMNNAME_Status);
	}

	/** Set Immutable Universally Unique Identifier.
		@param UUID 
		Immutable Universally Unique Identifier
	  */
	public void setUUID (String UUID)
	{
		set_Value (COLUMNNAME_UUID, UUID);
	}

	/** Get Immutable Universally Unique Identifier.
		@return Immutable Universally Unique Identifier
	  */
	public String getUUID () 
	{
		return (String)get_Value(COLUMNNAME_UUID);
	}
}