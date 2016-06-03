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

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/** Generated Model for AM_Prognosis
 *  @author Adempiere (generated) 
 *  @version 1.4.0 - $Id$ */
public class X_AM_Prognosis extends PO implements I_AM_Prognosis, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160517L;

    /** Standard Constructor */
    public X_AM_Prognosis (Properties ctx, int AM_Prognosis_ID, String trxName)
    {
      super (ctx, AM_Prognosis_ID, trxName);
      /** if (AM_Prognosis_ID == 0)
        {
			setAD_PInstance_ID (0);
			setAM_Prognosis_ID (0);
			setAM_ProgrammingType (null);
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
        } */
    }

    /** Load Constructor */
    public X_AM_Prognosis (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_AM_Prognosis[")
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

	public I_AD_PInstance getAD_PInstance() throws RuntimeException
    {
		return (I_AD_PInstance)MTable.get(getCtx(), I_AD_PInstance.Table_Name)
			.getPO(getAD_PInstance_ID(), get_TrxName());	}

	/** Set Process Instance.
		@param AD_PInstance_ID 
		Instance of the process
	  */
	public void setAD_PInstance_ID (int AD_PInstance_ID)
	{
		if (AD_PInstance_ID < 1) 
			set_Value (COLUMNNAME_AD_PInstance_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PInstance_ID, Integer.valueOf(AD_PInstance_ID));
	}

	/** Get Process Instance.
		@return Instance of the process
	  */
	public int getAD_PInstance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PInstance_ID);
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

	/** Set AM Prognosis.
		@param AM_Prognosis_ID AM Prognosis	  */
	public void setAM_Prognosis_ID (int AM_Prognosis_ID)
	{
		if (AM_Prognosis_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AM_Prognosis_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AM_Prognosis_ID, Integer.valueOf(AM_Prognosis_ID));
	}

	/** Get AM Prognosis.
		@return AM Prognosis	  */
	public int getAM_Prognosis_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AM_Prognosis_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** AM_ProgrammingType AD_Reference_ID=53827 */
	public static final int AM_PROGRAMMINGTYPE_AD_Reference_ID=53827;
	/** Calendar = C */
	public static final String AM_PROGRAMMINGTYPE_Calendar = "C";
	/** Meter = M */
	public static final String AM_PROGRAMMINGTYPE_Meter = "M";
	/** Set AM Programming Type.
		@param AM_ProgrammingType AM Programming Type	  */
	public void setAM_ProgrammingType (String AM_ProgrammingType)
	{

		set_Value (COLUMNNAME_AM_ProgrammingType, AM_ProgrammingType);
	}

	/** Get AM Programming Type.
		@return AM Programming Type	  */
	public String getAM_ProgrammingType () 
	{
		return (String)get_Value(COLUMNNAME_AM_ProgrammingType);
	}

	public I_AM_ServiceOrder getAM_ServiceOrder() throws RuntimeException
    {
		return (I_AM_ServiceOrder)MTable.get(getCtx(), I_AM_ServiceOrder.Table_Name)
			.getPO(getAM_ServiceOrder_ID(), get_TrxName());	}

	/** Set AM ServiceOrder.
		@param AM_ServiceOrder_ID AM ServiceOrder	  */
	public void setAM_ServiceOrder_ID (int AM_ServiceOrder_ID)
	{
		if (AM_ServiceOrder_ID < 1) 
			set_Value (COLUMNNAME_AM_ServiceOrder_ID, null);
		else 
			set_Value (COLUMNNAME_AM_ServiceOrder_ID, Integer.valueOf(AM_ServiceOrder_ID));
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

	/** Set Frequency.
		@param Frequency 
		Frequency of events
	  */
	public void setFrequency (int Frequency)
	{
		set_Value (COLUMNNAME_Frequency, Integer.valueOf(Frequency));
	}

	/** Get Frequency.
		@return Frequency of events
	  */
	public int getFrequency () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Frequency);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Selected.
		@param IsSelected Selected	  */
	public void setIsSelected (boolean IsSelected)
	{
		set_Value (COLUMNNAME_IsSelected, Boolean.valueOf(IsSelected));
	}

	/** Get Selected.
		@return Selected	  */
	public boolean isSelected () 
	{
		Object oo = get_Value(COLUMNNAME_IsSelected);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Last Maintenance.
		@param LastMaintenanceDate 
		Last Maintenance Date
	  */
	public void setLastMaintenanceDate (Timestamp LastMaintenanceDate)
	{
		set_Value (COLUMNNAME_LastMaintenanceDate, LastMaintenanceDate);
	}

	/** Get Last Maintenance.
		@return Last Maintenance Date
	  */
	public Timestamp getLastMaintenanceDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_LastMaintenanceDate);
	}

	/** Set Next Maintenance.
		@param NextMaintenenceDate 
		Next Maintenance Date
	  */
	public void setNextMaintenenceDate (Timestamp NextMaintenenceDate)
	{
		set_Value (COLUMNNAME_NextMaintenenceDate, NextMaintenenceDate);
	}

	/** Get Next Maintenance.
		@return Next Maintenance Date
	  */
	public Timestamp getNextMaintenenceDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_NextMaintenenceDate);
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
}