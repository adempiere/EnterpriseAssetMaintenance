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
package org.eam.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for AM_Maintenance
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0
 */
public interface I_AM_Maintenance 
{

    /** TableName=AM_Maintenance */
    public static final String Table_Name = "AM_Maintenance";

    /** AD_Table_ID=54109 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name A_Asset_Group_ID */
    public static final String COLUMNNAME_A_Asset_Group_ID = "A_Asset_Group_ID";

	/** Set Asset Group.
	  * Group of Assets
	  */
	public void setA_Asset_Group_ID (int A_Asset_Group_ID);

	/** Get Asset Group.
	  * Group of Assets
	  */
	public int getA_Asset_Group_ID();

	public org.compiere.model.I_A_Asset_Group getA_Asset_Group() throws RuntimeException;

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

	public org.compiere.model.I_A_Asset getA_Asset() throws RuntimeException;

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/** Set User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID);

	/** Get User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID();

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException;

    /** Column name AM_Area_ID */
    public static final String COLUMNNAME_AM_Area_ID = "AM_Area_ID";

	/** Set Maintenance Area	  */
	public void setAM_Area_ID (int AM_Area_ID);

	/** Get Maintenance Area	  */
	public int getAM_Area_ID();

	public org.eam.model.I_AM_Area getAM_Area() throws RuntimeException;

    /** Column name AM_Maintenance_ID */
    public static final String COLUMNNAME_AM_Maintenance_ID = "AM_Maintenance_ID";

	/** Set AM Maintenance ID	  */
	public void setAM_Maintenance_ID (int AM_Maintenance_ID);

	/** Get AM Maintenance ID	  */
	public int getAM_Maintenance_ID();

    /** Column name AM_MaintenanceParent_ID */
    public static final String COLUMNNAME_AM_MaintenanceParent_ID = "AM_MaintenanceParent_ID";

	/** Set AM Maintenance Parent	  */
	public void setAM_MaintenanceParent_ID (int AM_MaintenanceParent_ID);

	/** Get AM Maintenance Parent	  */
	public int getAM_MaintenanceParent_ID();

	public org.eam.model.I_AM_Maintenance getAM_MaintenanceParent() throws RuntimeException;

    /** Column name AM_Meter_ID */
    public static final String COLUMNNAME_AM_Meter_ID = "AM_Meter_ID";

	/** Set AM Meter	  */
	public void setAM_Meter_ID (int AM_Meter_ID);

	/** Get AM Meter	  */
	public int getAM_Meter_ID();

	public org.eam.model.I_AM_Meter getAM_Meter() throws RuntimeException;

    /** Column name AM_Pattern_ID */
    public static final String COLUMNNAME_AM_Pattern_ID = "AM_Pattern_ID";

	/** Set AM Maintenance Pattern	  */
	public void setAM_Pattern_ID (int AM_Pattern_ID);

	/** Get AM Maintenance Pattern	  */
	public int getAM_Pattern_ID();

	public org.eam.model.I_AM_Pattern getAM_Pattern() throws RuntimeException;

    /** Column name AverageUse */
    public static final String COLUMNNAME_AverageUse = "AverageUse";

	/** Set Average Use	  */
	public void setAverageUse (BigDecimal AverageUse);

	/** Get Average Use	  */
	public BigDecimal getAverageUse();

    /** Column name CalendarType */
    public static final String COLUMNNAME_CalendarType = "CalendarType";

	/** Set AM Calendar Type	  */
	public void setCalendarType (String CalendarType);

	/** Get AM Calendar Type	  */
	public String getCalendarType();

    /** Column name CopyFrom */
    public static final String COLUMNNAME_CopyFrom = "CopyFrom";

	/** Set Copy From.
	  * Copy From Record
	  */
	public void setCopyFrom (String CopyFrom);

	/** Get Copy From.
	  * Copy From Record
	  */
	public String getCopyFrom();

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

    /** Column name CurrentAM */
    public static final String COLUMNNAME_CurrentAM = "CurrentAM";

	/** Set CurrentAM	  */
	public void setCurrentAM (BigDecimal CurrentAM);

	/** Get CurrentAM	  */
	public BigDecimal getCurrentAM();

    /** Column name DateLastRun */
    public static final String COLUMNNAME_DateLastRun = "DateLastRun";

	/** Set Date last run.
	  * Date the process was last run.
	  */
	public void setDateLastRun (Timestamp DateLastRun);

	/** Get Date last run.
	  * Date the process was last run.
	  */
	public Timestamp getDateLastRun();

    /** Column name DateLastRunAM */
    public static final String COLUMNNAME_DateLastRunAM = "DateLastRunAM";

	/** Set Date Last Run AM	  */
	public void setDateLastRunAM (Timestamp DateLastRunAM);

	/** Get Date Last Run AM	  */
	public Timestamp getDateLastRunAM();

    /** Column name DateLastSO */
    public static final String COLUMNNAME_DateLastSO = "DateLastSO";

	/** Set Date Last Service Order	  */
	public void setDateLastSO (Timestamp DateLastSO);

	/** Get Date Last Service Order	  */
	public Timestamp getDateLastSO();

    /** Column name DateNextRun */
    public static final String COLUMNNAME_DateNextRun = "DateNextRun";

	/** Set Date next run.
	  * Date the process will run next
	  */
	public void setDateNextRun (Timestamp DateNextRun);

	/** Get Date next run.
	  * Date the process will run next
	  */
	public Timestamp getDateNextRun();

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

    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/** Set Document Status.
	  * The current status of the document
	  */
	public void setDocStatus (String DocStatus);

	/** Get Document Status.
	  * The current status of the document
	  */
	public String getDocStatus();

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Document No.
	  * Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo);

	/** Get Document No.
	  * Document sequence number of the document
	  */
	public String getDocumentNo();

    /** Column name Interval */
    public static final String COLUMNNAME_Interval = "Interval";

	/** Set Interval	  */
	public void setInterval (BigDecimal Interval);

	/** Get Interval	  */
	public BigDecimal getInterval();

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

    /** Column name IsChild */
    public static final String COLUMNNAME_IsChild = "IsChild";

	/** Set Child	  */
	public void setIsChild (boolean IsChild);

	/** Get Child	  */
	public boolean isChild();

    /** Column name IsFriday */
    public static final String COLUMNNAME_IsFriday = "IsFriday";

	/** Set Friday	  */
	public void setIsFriday (boolean IsFriday);

	/** Get Friday	  */
	public boolean isFriday();

    /** Column name IsMonday */
    public static final String COLUMNNAME_IsMonday = "IsMonday";

	/** Set Monday	  */
	public void setIsMonday (boolean IsMonday);

	/** Get Monday	  */
	public boolean isMonday();

    /** Column name IsSaturday */
    public static final String COLUMNNAME_IsSaturday = "IsSaturday";

	/** Set Saturday	  */
	public void setIsSaturday (boolean IsSaturday);

	/** Get Saturday	  */
	public boolean isSaturday();

    /** Column name IsSunday */
    public static final String COLUMNNAME_IsSunday = "IsSunday";

	/** Set Sunday	  */
	public void setIsSunday (boolean IsSunday);

	/** Get Sunday	  */
	public boolean isSunday();

    /** Column name IsThursday */
    public static final String COLUMNNAME_IsThursday = "IsThursday";

	/** Set Thursday	  */
	public void setIsThursday (boolean IsThursday);

	/** Get Thursday	  */
	public boolean isThursday();

    /** Column name IsTuesday */
    public static final String COLUMNNAME_IsTuesday = "IsTuesday";

	/** Set Tuesday	  */
	public void setIsTuesday (boolean IsTuesday);

	/** Get Tuesday	  */
	public boolean isTuesday();

    /** Column name IsWednesday */
    public static final String COLUMNNAME_IsWednesday = "IsWednesday";

	/** Set Wednesday	  */
	public void setIsWednesday (boolean IsWednesday);

	/** Get Wednesday	  */
	public boolean isWednesday();

    /** Column name LastAM */
    public static final String COLUMNNAME_LastAM = "LastAM";

	/** Set Last AM	  */
	public void setLastAM (BigDecimal LastAM);

	/** Get Last AM	  */
	public BigDecimal getLastAM();

    /** Column name LastRead */
    public static final String COLUMNNAME_LastRead = "LastRead";

	/** Set Last Read	  */
	public void setLastRead (BigDecimal LastRead);

	/** Get Last Read	  */
	public BigDecimal getLastRead();

    /** Column name NextAM */
    public static final String COLUMNNAME_NextAM = "NextAM";

	/** Set NextAM	  */
	public void setNextAM (BigDecimal NextAM);

	/** Get NextAM	  */
	public BigDecimal getNextAM();

    /** Column name PriorityRule */
    public static final String COLUMNNAME_PriorityRule = "PriorityRule";

	/** Set Priority.
	  * Priority of a document
	  */
	public void setPriorityRule (String PriorityRule);

	/** Get Priority.
	  * Priority of a document
	  */
	public String getPriorityRule();

    /** Column name ProgrammingType */
    public static final String COLUMNNAME_ProgrammingType = "ProgrammingType";

	/** Set AM Programming Type	  */
	public void setProgrammingType (String ProgrammingType);

	/** Get AM Programming Type	  */
	public String getProgrammingType();

    /** Column name Range */
    public static final String COLUMNNAME_Range = "Range";

	/** Set Range	  */
	public void setRange (BigDecimal Range);

	/** Get Range	  */
	public BigDecimal getRange();

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
