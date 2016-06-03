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
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.util.KeyNamePair;

/** Generated Interface for AM_Maintenance_Resource
 *  @author Adempiere (generated) 
 *  @version 1.4.0
 */
public interface I_AM_Maintenance_Resource 
{

    /** TableName=AM_Maintenance_Resource */
    public static final String Table_Name = "AM_Maintenance_Resource";

    /** AD_Table_ID=54113 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organisation.
	  * Organisational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organisation.
	  * Organisational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name AM_Maintenance_Resource_ID */
    public static final String COLUMNNAME_AM_Maintenance_Resource_ID = "AM_Maintenance_Resource_ID";

	/** Set AM Maintenance Resource	  */
	public void setAM_Maintenance_Resource_ID (int AM_Maintenance_Resource_ID);

	/** Get AM Maintenance Resource	  */
	public int getAM_Maintenance_Resource_ID();

    /** Column name AM_Maintenance_Task_ID */
    public static final String COLUMNNAME_AM_Maintenance_Task_ID = "AM_Maintenance_Task_ID";

	/** Set AM Maintenance Task	  */
	public void setAM_Maintenance_Task_ID (int AM_Maintenance_Task_ID);

	/** Get AM Maintenance Task	  */
	public int getAM_Maintenance_Task_ID();

	public I_AM_Maintenance_Task getAM_Maintenance_Task() throws RuntimeException;

    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/** Set UOM.
	  * Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID);

	/** Get UOM.
	  * Unit of Measure
	  */
	public int getC_UOM_ID();

	public I_C_UOM getC_UOM() throws RuntimeException;

    /** Column name CostAmt */
    public static final String COLUMNNAME_CostAmt = "CostAmt";

	/** Set Cost Value.
	  * Value with Cost
	  */
	public void setCostAmt (BigDecimal CostAmt);

	/** Get Cost Value.
	  * Value with Cost
	  */
	public BigDecimal getCostAmt();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name M_BOM_ID */
    public static final String COLUMNNAME_M_BOM_ID = "M_BOM_ID";

	/** Set BOM.
	  * Bill of Material
	  */
	public void setM_BOM_ID (int M_BOM_ID);

	/** Get BOM.
	  * Bill of Material
	  */
	public int getM_BOM_ID();

	public I_M_BOM getM_BOM() throws RuntimeException;

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Product.
	  * Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Product.
	  * Product, Service, Item
	  */
	public int getM_Product_ID();

	public I_M_Product getM_Product() throws RuntimeException;

    /** Column name ResourceQty */
    public static final String COLUMNNAME_ResourceQty = "ResourceQty";

	/** Set Resource Qty	  */
	public void setResourceQty (BigDecimal ResourceQty);

	/** Get Resource Qty	  */
	public BigDecimal getResourceQty();

    /** Column name ResourceType */
    public static final String COLUMNNAME_ResourceType = "ResourceType";

	/** Set Resource Type	  */
	public void setResourceType (String ResourceType);

	/** Get Resource Type	  */
	public String getResourceType();

    /** Column name S_Resource_ID */
    public static final String COLUMNNAME_S_Resource_ID = "S_Resource_ID";

	/** Set Resource.
	  * Resource
	  */
	public void setS_Resource_ID (int S_Resource_ID);

	/** Get Resource.
	  * Resource
	  */
	public int getS_Resource_ID();

	public I_S_Resource getS_Resource() throws RuntimeException;

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();
}
