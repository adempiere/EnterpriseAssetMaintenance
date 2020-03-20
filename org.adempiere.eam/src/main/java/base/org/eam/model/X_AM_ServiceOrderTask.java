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
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for AM_ServiceOrderTask
 *  @author Adempiere (generated) 
 *  @version Release 3.9.3 - $Id$ */
public class X_AM_ServiceOrderTask extends PO implements I_AM_ServiceOrderTask, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20200320L;

    /** Standard Constructor */
    public X_AM_ServiceOrderTask (Properties ctx, int AM_ServiceOrderTask_ID, String trxName)
    {
      super (ctx, AM_ServiceOrderTask_ID, trxName);
      /** if (AM_ServiceOrderTask_ID == 0)
        {
			setAM_ServiceOrder_ID (0);
			setAM_ServiceOrderTask_ID (0);
			setC_UOM_ID (0);
			setName (null);
			setProcessed (false);
			setStatus (null);
// 'NS'
        } */
    }

    /** Load Constructor */
    public X_AM_ServiceOrderTask (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_AM_ServiceOrderTask[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.eam.model.I_AM_Maintenance getAM_Maintenance() throws RuntimeException
    {
		return (org.eam.model.I_AM_Maintenance)MTable.get(getCtx(), org.eam.model.I_AM_Maintenance.Table_Name)
			.getPO(getAM_Maintenance_ID(), get_TrxName());	}

	/** Set Asset Maintenance.
		@param AM_Maintenance_ID 
		Define a maintenance program assigned to Asset
	  */
	public void setAM_Maintenance_ID (int AM_Maintenance_ID)
	{
		if (AM_Maintenance_ID < 1) 
			set_Value (COLUMNNAME_AM_Maintenance_ID, null);
		else 
			set_Value (COLUMNNAME_AM_Maintenance_ID, Integer.valueOf(AM_Maintenance_ID));
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

	public org.eam.model.I_AM_ServiceOrder getAM_ServiceOrder() throws RuntimeException
    {
		return (org.eam.model.I_AM_ServiceOrder)MTable.get(getCtx(), org.eam.model.I_AM_ServiceOrder.Table_Name)
			.getPO(getAM_ServiceOrder_ID(), get_TrxName());	}

	/** Set Service Order.
		@param AM_ServiceOrder_ID 
		Service Order for maintenance
	  */
	public void setAM_ServiceOrder_ID (int AM_ServiceOrder_ID)
	{
		if (AM_ServiceOrder_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AM_ServiceOrder_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AM_ServiceOrder_ID, Integer.valueOf(AM_ServiceOrder_ID));
	}

	/** Get Service Order.
		@return Service Order for maintenance
	  */
	public int getAM_ServiceOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AM_ServiceOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Service Order Task.
		@param AM_ServiceOrderTask_ID 
		Task for a service order of maintenance
	  */
	public void setAM_ServiceOrderTask_ID (int AM_ServiceOrderTask_ID)
	{
		if (AM_ServiceOrderTask_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AM_ServiceOrderTask_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AM_ServiceOrderTask_ID, Integer.valueOf(AM_ServiceOrderTask_ID));
	}

	/** Get Service Order Task.
		@return Task for a service order of maintenance
	  */
	public int getAM_ServiceOrderTask_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AM_ServiceOrderTask_ID);
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

	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
    {
		return (org.compiere.model.I_C_UOM)MTable.get(getCtx(), org.compiere.model.I_C_UOM.Table_Name)
			.getPO(getC_UOM_ID(), get_TrxName());	}

	/** Set UOM.
		@param C_UOM_ID 
		Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get UOM.
		@return Unit of Measure
	  */
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Duration.
		@param Duration 
		Normal Duration in Duration Unit
	  */
	public void setDuration (BigDecimal Duration)
	{
		set_Value (COLUMNNAME_Duration, Duration);
	}

	/** Get Duration.
		@return Normal Duration in Duration Unit
	  */
	public BigDecimal getDuration () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Duration);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Duration Real.
		@param DurationReal Duration Real	  */
	public void setDurationReal (BigDecimal DurationReal)
	{
		set_Value (COLUMNNAME_DurationReal, DurationReal);
	}

	/** Get Duration Real.
		@return Duration Real	  */
	public BigDecimal getDurationReal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DurationReal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Line No.
		@param Line 
		Unique line for this document
	  */
	public void setLine (int Line)
	{
		set_Value (COLUMNNAME_Line, Integer.valueOf(Line));
	}

	/** Get Line No.
		@return Unique line for this document
	  */
	public int getLine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
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

	/** Status AD_Reference_ID=53833 */
	public static final int STATUS_AD_Reference_ID=53833;
	/** Completed = CO */
	public static final String STATUS_Completed = "CO";
	/** In Progress = IP */
	public static final String STATUS_InProgress = "IP";
	/** Not Started = NS */
	public static final String STATUS_NotStarted = "NS";
	/** Stop = ST */
	public static final String STATUS_Stop = "ST";
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