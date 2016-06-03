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
import java.util.Properties;
import org.compiere.util.KeyNamePair;

/** Generated Model for AM_Meter
 *  @author Adempiere (generated) 
 *  @version 1.4.0 - $Id$ */
public class X_AM_Meter extends PO implements I_AM_Meter, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160517L;

    /** Standard Constructor */
    public X_AM_Meter (Properties ctx, int AM_Meter_ID, String trxName)
    {
      super (ctx, AM_Meter_ID, trxName);
      /** if (AM_Meter_ID == 0)
        {
			setAM_Meter_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AM_Meter (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_AM_Meter[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AM Meter.
		@param AM_Meter_ID AM Meter	  */
	public void setAM_Meter_ID (int AM_Meter_ID)
	{
		if (AM_Meter_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AM_Meter_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AM_Meter_ID, Integer.valueOf(AM_Meter_ID));
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

	/** Set Max Day.
		@param MaxDay Max Day	  */
	public void setMaxDay (int MaxDay)
	{
		set_Value (COLUMNNAME_MaxDay, Integer.valueOf(MaxDay));
	}

	/** Get Max Day.
		@return Max Day	  */
	public int getMaxDay () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MaxDay);
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