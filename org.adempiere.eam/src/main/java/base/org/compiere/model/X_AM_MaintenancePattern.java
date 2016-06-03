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

/** Generated Model for AM_MaintenancePattern
 *  @author Adempiere (generated) 
 *  @version 1.4.0 - $Id$ */
public class X_AM_MaintenancePattern extends PO implements I_AM_MaintenancePattern, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160517L;

    /** Standard Constructor */
    public X_AM_MaintenancePattern (Properties ctx, int AM_MaintenancePattern_ID, String trxName)
    {
      super (ctx, AM_MaintenancePattern_ID, trxName);
      /** if (AM_MaintenancePattern_ID == 0)
        {
			setAM_MaintenancePattern_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AM_MaintenancePattern (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_AM_MaintenancePattern[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_A_Asset_Group getA_Asset_Group() throws RuntimeException
    {
		return (I_A_Asset_Group)MTable.get(getCtx(), I_A_Asset_Group.Table_Name)
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

	/** Set AM Maintenance Pattern.
		@param AM_MaintenancePattern_ID AM Maintenance Pattern	  */
	public void setAM_MaintenancePattern_ID (int AM_MaintenancePattern_ID)
	{
		if (AM_MaintenancePattern_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AM_MaintenancePattern_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AM_MaintenancePattern_ID, Integer.valueOf(AM_MaintenancePattern_ID));
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

	/** MaintenancePatternType AD_Reference_ID=53817 */
	public static final int MAINTENANCEPATTERNTYPE_AD_Reference_ID=53817;
	/** Type A = AA */
	public static final String MAINTENANCEPATTERNTYPE_TypeA = "AA";
	/** Type B = BB */
	public static final String MAINTENANCEPATTERNTYPE_TypeB = "BB";
	/** Type C = CC */
	public static final String MAINTENANCEPATTERNTYPE_TypeC = "CC";
	/** Set Maintenance Pattern Type.
		@param MaintenancePatternType Maintenance Pattern Type	  */
	public void setMaintenancePatternType (String MaintenancePatternType)
	{

		set_Value (COLUMNNAME_MaintenancePatternType, MaintenancePatternType);
	}

	/** Get Maintenance Pattern Type.
		@return Maintenance Pattern Type	  */
	public String getMaintenancePatternType () 
	{
		return (String)get_Value(COLUMNNAME_MaintenancePatternType);
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
}