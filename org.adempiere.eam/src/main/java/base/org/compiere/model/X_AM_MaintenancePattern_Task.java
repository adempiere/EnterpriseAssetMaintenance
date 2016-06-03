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
import java.util.Properties;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for AM_MaintenancePattern_Task
 *  @author Adempiere (generated) 
 *  @version 1.4.0 - $Id$ */
public class X_AM_MaintenancePattern_Task extends PO implements I_AM_MaintenancePattern_Task, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160517L;

    /** Standard Constructor */
    public X_AM_MaintenancePattern_Task (Properties ctx, int AM_MaintenancePattern_Task_ID, String trxName)
    {
      super (ctx, AM_MaintenancePattern_Task_ID, trxName);
      /** if (AM_MaintenancePattern_Task_ID == 0)
        {
			setAM_MaintenancePattern_ID (0);
			setAM_MaintenancePattern_Task_ID (0);
			setC_UOM_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AM_MaintenancePattern_Task (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_AM_MaintenancePattern_Task[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set AM Maintenance Pattern Task.
		@param AM_MaintenancePattern_Task_ID AM Maintenance Pattern Task	  */
	public void setAM_MaintenancePattern_Task_ID (int AM_MaintenancePattern_Task_ID)
	{
		if (AM_MaintenancePattern_Task_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AM_MaintenancePattern_Task_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AM_MaintenancePattern_Task_ID, Integer.valueOf(AM_MaintenancePattern_Task_ID));
	}

	/** Get AM Maintenance Pattern Task.
		@return AM Maintenance Pattern Task	  */
	public int getAM_MaintenancePattern_Task_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AM_MaintenancePattern_Task_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_UOM getC_UOM() throws RuntimeException
    {
		return (I_C_UOM)MTable.get(getCtx(), I_C_UOM.Table_Name)
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
	public void setDuration (int Duration)
	{
		set_Value (COLUMNNAME_Duration, Integer.valueOf(Duration));
	}

	/** Get Duration.
		@return Normal Duration in Duration Unit
	  */
	public int getDuration () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Duration);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }
}