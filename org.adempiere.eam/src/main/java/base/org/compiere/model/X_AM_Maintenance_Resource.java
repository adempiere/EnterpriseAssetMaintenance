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

/** Generated Model for AM_Maintenance_Resource
 *  @author Adempiere (generated) 
 *  @version 1.4.0 - $Id$ */
public class X_AM_Maintenance_Resource extends PO implements I_AM_Maintenance_Resource, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160517L;

    /** Standard Constructor */
    public X_AM_Maintenance_Resource (Properties ctx, int AM_Maintenance_Resource_ID, String trxName)
    {
      super (ctx, AM_Maintenance_Resource_ID, trxName);
      /** if (AM_Maintenance_Resource_ID == 0)
        {
			setAM_Maintenance_Resource_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AM_Maintenance_Resource (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_AM_Maintenance_Resource[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AM Maintenance Resource.
		@param AM_Maintenance_Resource_ID AM Maintenance Resource	  */
	public void setAM_Maintenance_Resource_ID (int AM_Maintenance_Resource_ID)
	{
		if (AM_Maintenance_Resource_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AM_Maintenance_Resource_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AM_Maintenance_Resource_ID, Integer.valueOf(AM_Maintenance_Resource_ID));
	}

	/** Get AM Maintenance Resource.
		@return AM Maintenance Resource	  */
	public int getAM_Maintenance_Resource_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AM_Maintenance_Resource_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AM_Maintenance_Task getAM_Maintenance_Task() throws RuntimeException
    {
		return (I_AM_Maintenance_Task)MTable.get(getCtx(), I_AM_Maintenance_Task.Table_Name)
			.getPO(getAM_Maintenance_Task_ID(), get_TrxName());	}

	/** Set AM Maintenance Task.
		@param AM_Maintenance_Task_ID AM Maintenance Task	  */
	public void setAM_Maintenance_Task_ID (int AM_Maintenance_Task_ID)
	{
		if (AM_Maintenance_Task_ID < 1) 
			set_Value (COLUMNNAME_AM_Maintenance_Task_ID, null);
		else 
			set_Value (COLUMNNAME_AM_Maintenance_Task_ID, Integer.valueOf(AM_Maintenance_Task_ID));
	}

	/** Get AM Maintenance Task.
		@return AM Maintenance Task	  */
	public int getAM_Maintenance_Task_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AM_Maintenance_Task_ID);
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

	public I_M_BOM getM_BOM() throws RuntimeException
    {
		return (I_M_BOM)MTable.get(getCtx(), I_M_BOM.Table_Name)
			.getPO(getM_BOM_ID(), get_TrxName());	}

	/** Set BOM.
		@param M_BOM_ID 
		Bill of Material
	  */
	public void setM_BOM_ID (int M_BOM_ID)
	{
		if (M_BOM_ID < 1) 
			set_Value (COLUMNNAME_M_BOM_ID, null);
		else 
			set_Value (COLUMNNAME_M_BOM_ID, Integer.valueOf(M_BOM_ID));
	}

	/** Get BOM.
		@return Bill of Material
	  */
	public int getM_BOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_BOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_Product getM_Product() throws RuntimeException
    {
		return (I_M_Product)MTable.get(getCtx(), I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Resource Qty.
		@param ResourceQty Resource Qty	  */
	public void setResourceQty (BigDecimal ResourceQty)
	{
		set_Value (COLUMNNAME_ResourceQty, ResourceQty);
	}

	/** Get Resource Qty.
		@return Resource Qty	  */
	public BigDecimal getResourceQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ResourceQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** ResourceType AD_Reference_ID=53821 */
	public static final int RESOURCETYPE_AD_Reference_ID=53821;
	/** Bom Parts = BP */
	public static final String RESOURCETYPE_BomParts = "BP";
	/** BOM Tools = BT */
	public static final String RESOURCETYPE_BOMTools = "BT";
	/** Consumption = CO */
	public static final String RESOURCETYPE_Consumption = "CO";
	/** Human Resource = HH */
	public static final String RESOURCETYPE_HumanResource = "HH";
	/** Part = RR */
	public static final String RESOURCETYPE_Part = "RR";
	/** Tool = TT */
	public static final String RESOURCETYPE_Tool = "TT";
	/** Set Resource Type.
		@param ResourceType Resource Type	  */
	public void setResourceType (String ResourceType)
	{

		set_Value (COLUMNNAME_ResourceType, ResourceType);
	}

	/** Get Resource Type.
		@return Resource Type	  */
	public String getResourceType () 
	{
		return (String)get_Value(COLUMNNAME_ResourceType);
	}

	public I_S_Resource getS_Resource() throws RuntimeException
    {
		return (I_S_Resource)MTable.get(getCtx(), I_S_Resource.Table_Name)
			.getPO(getS_Resource_ID(), get_TrxName());	}

	/** Set Resource.
		@param S_Resource_ID 
		Resource
	  */
	public void setS_Resource_ID (int S_Resource_ID)
	{
		if (S_Resource_ID < 1) 
			set_Value (COLUMNNAME_S_Resource_ID, null);
		else 
			set_Value (COLUMNNAME_S_Resource_ID, Integer.valueOf(S_Resource_ID));
	}

	/** Get Resource.
		@return Resource
	  */
	public int getS_Resource_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_S_Resource_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}