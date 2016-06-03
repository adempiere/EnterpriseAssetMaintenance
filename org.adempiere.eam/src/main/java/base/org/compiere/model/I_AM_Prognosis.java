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

/** Generated Interface for AM_Prognosis
 *  @author Adempiere (generated) 
 *  @version 1.4.0
 */
public interface I_AM_Prognosis 
{

    /** TableName=AM_Prognosis */
    public static final String Table_Name = "AM_Prognosis";

    /** AD_Table_ID=54118 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name A_Asset_ID */
    public static final String COLUMNNAME_A_Asset_ID = "A_Asset_ID";

	/** Set Asset.
	  * Asset used internally or by customers
	  */
	public void setA_Asset_ID (int A_Asset_ID);

	/** Get Asset.
	  * Asset used internally or by customers
	  */
	public int getA_Asset_ID();

	public I_A_Asset getA_Asset() throws RuntimeException;

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

    /** Column name AD_PInstance_ID */
    public static final String COLUMNNAME_AD_PInstance_ID = "AD_PInstance_ID";

	/** Set Process Instance.
	  * Instance of the process
	  */
	public void setAD_PInstance_ID (int AD_PInstance_ID);

	/** Get Process Instance.
	  * Instance of the process
	  */
	public int getAD_PInstance_ID();

	public I_AD_PInstance getAD_PInstance() throws RuntimeException;

    /** Column name AM_Maintenance_ID */
    public static final String COLUMNNAME_AM_Maintenance_ID = "AM_Maintenance_ID";

	/** Set AM Maintenance	  */
	public void setAM_Maintenance_ID (int AM_Maintenance_ID);

	/** Get AM Maintenance	  */
	public int getAM_Maintenance_ID();

	public I_AM_Maintenance getAM_Maintenance() throws RuntimeException;

    /** Column name AM_Prognosis_ID */
    public static final String COLUMNNAME_AM_Prognosis_ID = "AM_Prognosis_ID";

	/** Set AM Prognosis	  */
	public void setAM_Prognosis_ID (int AM_Prognosis_ID);

	/** Get AM Prognosis	  */
	public int getAM_Prognosis_ID();

    /** Column name AM_ProgrammingType */
    public static final String COLUMNNAME_AM_ProgrammingType = "AM_ProgrammingType";

	/** Set AM Programming Type	  */
	public void setAM_ProgrammingType (String AM_ProgrammingType);

	/** Get AM Programming Type	  */
	public String getAM_ProgrammingType();

    /** Column name AM_ServiceOrder_ID */
    public static final String COLUMNNAME_AM_ServiceOrder_ID = "AM_ServiceOrder_ID";

	/** Set AM ServiceOrder	  */
	public void setAM_ServiceOrder_ID (int AM_ServiceOrder_ID);

	/** Get AM ServiceOrder	  */
	public int getAM_ServiceOrder_ID();

	public I_AM_ServiceOrder getAM_ServiceOrder() throws RuntimeException;

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

    /** Column name DateTrx */
    public static final String COLUMNNAME_DateTrx = "DateTrx";

	/** Set Transaction Date.
	  * Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx);

	/** Get Transaction Date.
	  * Transaction Date
	  */
	public Timestamp getDateTrx();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name Frequency */
    public static final String COLUMNNAME_Frequency = "Frequency";

	/** Set Frequency.
	  * Frequency of events
	  */
	public void setFrequency (int Frequency);

	/** Get Frequency.
	  * Frequency of events
	  */
	public int getFrequency();

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

    /** Column name IsSelected */
    public static final String COLUMNNAME_IsSelected = "IsSelected";

	/** Set Selected	  */
	public void setIsSelected (boolean IsSelected);

	/** Get Selected	  */
	public boolean isSelected();

    /** Column name LastMaintenanceDate */
    public static final String COLUMNNAME_LastMaintenanceDate = "LastMaintenanceDate";

	/** Set Last Maintenance.
	  * Last Maintenance Date
	  */
	public void setLastMaintenanceDate (Timestamp LastMaintenanceDate);

	/** Get Last Maintenance.
	  * Last Maintenance Date
	  */
	public Timestamp getLastMaintenanceDate();

    /** Column name NextMaintenenceDate */
    public static final String COLUMNNAME_NextMaintenenceDate = "NextMaintenenceDate";

	/** Set Next Maintenance.
	  * Next Maintenance Date
	  */
	public void setNextMaintenenceDate (Timestamp NextMaintenenceDate);

	/** Get Next Maintenance.
	  * Next Maintenance Date
	  */
	public Timestamp getNextMaintenenceDate();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

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
