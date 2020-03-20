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

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Model for AM_Schedule
 *  @author Adempiere (generated) 
 *  @version Release 3.9.3 - $Id$ */
public class X_AM_Schedule extends PO implements I_AM_Schedule, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20200320L;

    /** Standard Constructor */
    public X_AM_Schedule (Properties ctx, int AM_Schedule_ID, String trxName)
    {
      super (ctx, AM_Schedule_ID, trxName);
      /** if (AM_Schedule_ID == 0)
        {
			setAM_Maintenance_ID (0);
			setAM_Schedule_ID (0);
			setMaintenanceDate (new Timestamp( System.currentTimeMillis() ));
			setPriorityRule (null);
			setProcessed (false);
// N
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_AM_Schedule (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_AM_Schedule[")
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

	/** Set Maintenance Schedule.
		@param AM_Schedule_ID Maintenance Schedule	  */
	public void setAM_Schedule_ID (int AM_Schedule_ID)
	{
		if (AM_Schedule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AM_Schedule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AM_Schedule_ID, Integer.valueOf(AM_Schedule_ID));
	}

	/** Get Maintenance Schedule.
		@return Maintenance Schedule	  */
	public int getAM_Schedule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AM_Schedule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.eam.model.I_AM_ServiceRequest getAM_ServiceRequest() throws RuntimeException
    {
		return (org.eam.model.I_AM_ServiceRequest)MTable.get(getCtx(), org.eam.model.I_AM_ServiceRequest.Table_Name)
			.getPO(getAM_ServiceRequest_ID(), get_TrxName());	}

	/** Set Service Order Request.
		@param AM_ServiceRequest_ID 
		Request for a Service Order
	  */
	public void setAM_ServiceRequest_ID (int AM_ServiceRequest_ID)
	{
		if (AM_ServiceRequest_ID < 1) 
			set_Value (COLUMNNAME_AM_ServiceRequest_ID, null);
		else 
			set_Value (COLUMNNAME_AM_ServiceRequest_ID, Integer.valueOf(AM_ServiceRequest_ID));
	}

	/** Get Service Order Request.
		@return Request for a Service Order
	  */
	public int getAM_ServiceRequest_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AM_ServiceRequest_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Closed Status.
		@param IsClosed 
		The status is closed
	  */
	public void setIsClosed (boolean IsClosed)
	{
		set_Value (COLUMNNAME_IsClosed, Boolean.valueOf(IsClosed));
	}

	/** Get Closed Status.
		@return The status is closed
	  */
	public boolean isClosed () 
	{
		Object oo = get_Value(COLUMNNAME_IsClosed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Maintenance Date.
		@param MaintenanceDate 
		Maintenance Date for Schedule
	  */
	public void setMaintenanceDate (Timestamp MaintenanceDate)
	{
		set_Value (COLUMNNAME_MaintenanceDate, MaintenanceDate);
	}

	/** Get Maintenance Date.
		@return Maintenance Date for Schedule
	  */
	public Timestamp getMaintenanceDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_MaintenanceDate);
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

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getValue());
    }
}