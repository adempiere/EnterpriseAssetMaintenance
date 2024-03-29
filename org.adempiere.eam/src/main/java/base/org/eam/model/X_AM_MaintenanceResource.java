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

/** Generated Model for AM_MaintenanceResource
 *  @author Adempiere (generated) 
 *  @version Release 3.9.3 - $Id$ */
public class X_AM_MaintenanceResource extends PO implements I_AM_MaintenanceResource, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20200320L;

    /** Standard Constructor */
    public X_AM_MaintenanceResource (Properties ctx, int AM_MaintenanceResource_ID, String trxName)
    {
      super (ctx, AM_MaintenanceResource_ID, trxName);
      /** if (AM_MaintenanceResource_ID == 0)
        {
			setAM_MaintenanceResource_ID (0);
			setAM_MaintenanceTask_ID (0);
			setResourceType (null);
        } */
    }

    /** Load Constructor */
    public X_AM_MaintenanceResource (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_AM_MaintenanceResource[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Maintenance Resource.
		@param AM_MaintenanceResource_ID 
		Resource used for make a maintenance order
	  */
	public void setAM_MaintenanceResource_ID (int AM_MaintenanceResource_ID)
	{
		if (AM_MaintenanceResource_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AM_MaintenanceResource_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AM_MaintenanceResource_ID, Integer.valueOf(AM_MaintenanceResource_ID));
	}

	/** Get Maintenance Resource.
		@return Resource used for make a maintenance order
	  */
	public int getAM_MaintenanceResource_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AM_MaintenanceResource_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.eam.model.I_AM_MaintenanceTask getAM_MaintenanceTask() throws RuntimeException
    {
		return (org.eam.model.I_AM_MaintenanceTask)MTable.get(getCtx(), org.eam.model.I_AM_MaintenanceTask.Table_Name)
			.getPO(getAM_MaintenanceTask_ID(), get_TrxName());	}

	/** Set Maintenance Task.
		@param AM_MaintenanceTask_ID 
		Task that will be maked for asset maintenance
	  */
	public void setAM_MaintenanceTask_ID (int AM_MaintenanceTask_ID)
	{
		if (AM_MaintenanceTask_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AM_MaintenanceTask_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AM_MaintenanceTask_ID, Integer.valueOf(AM_MaintenanceTask_ID));
	}

	/** Get Maintenance Task.
		@return Task that will be maked for asset maintenance
	  */
	public int getAM_MaintenanceTask_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AM_MaintenanceTask_ID);
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

	/** Set Resource Quantity.
		@param ResourceQuantity 
		Resource Quantity used for Maintenance
	  */
	public void setResourceQuantity (BigDecimal ResourceQuantity)
	{
		set_Value (COLUMNNAME_ResourceQuantity, ResourceQuantity);
	}

	/** Get Resource Quantity.
		@return Resource Quantity used for Maintenance
	  */
	public BigDecimal getResourceQuantity () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ResourceQuantity);
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