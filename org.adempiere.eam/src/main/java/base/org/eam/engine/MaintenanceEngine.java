/*************************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                              *
 * This program is free software; you can redistribute it and/or modify it    		 *
 * under the terms version 2 or later of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope   		 *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied 		 *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           		 *
 * See the GNU General Public License for more details.                       		 *
 * You should have received a copy of the GNU General Public License along    		 *
 * with this program; if not, write to the Free Software Foundation, Inc.,    		 *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     		 *
 * For the text or an alternative of this public license, you may reach us    		 *
 * Copyright (C) 2012-2020 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpya.com				  		                 *
 *************************************************************************************/
package org.eam.engine;

import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.DocAction;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eam.model.MAMMaintenance;
import org.eam.model.MAMSchedule;

/**
 * Abstract class for handle maintenance type engines
 * @author Yamel Senih, ysenih@erpya.com , http://www.erpya.com
 */
public abstract class MaintenanceEngine {
	public MaintenanceEngine() {
		
	}
	
	/**	Document for process	*/
	private DocAction document;
	/**	Maintenance for it	*/
	private MAMMaintenance maintenance;
	/**	Transaction Name	*/
	private String trxName;
	/**	Logger	*/
	private static final Logger logger = Logger.getLogger(MaintenanceEngine.class.getName());
	
	/**
	 * Set Maintenance for process
	 * @param maintenanceId
	 */
	public void setMaintenanceDefinition(MAMMaintenance maintenance) {
		this.maintenance = maintenance;
	}
	
	/**
	 * Get Maintenance definition for engines
	 * @return
	 */
	protected MAMMaintenance getMaintenanceDefinition() {
		return maintenance;
	}
	
	/**
	 * @return the trxName
	 */
	public final String getTrxName() {
		return trxName;
	}

	/**
	 * @param trxName the trxName to set
	 */
	public final void setTrxName(String trxName) {
		this.trxName = trxName;
	}

	/**
	 * Set document to process
	 * @param document
	 */
	public void setDocument(DocAction document) {
		this.document = document;
	}
	
	/**
	 * Get current document
	 * @return
	 */
	protected DocAction getDocument() {
		return document;
	}
	
	/**
	 * Process Schedule for engine
	 * @param startDate: Start date for maintenance schedule
	 * @param endDate: End Date for schedule
	 * @return
	 */
	public int createSchedule(Timestamp startDate, Timestamp endDate) {
		if(startDate == null
				|| endDate == null) {
			throw new AdempiereException("@StartDate@ / @EndDate@ @FillMandarory@");
		}
		//	
		if(startDate.after(endDate)) {
			throw new AdempiereException("@StartDate@ / @EndDate@ @Invalid@");
		}
		//	Delete before create\
		int deleted = DB.executeUpdate("DELETE FROM AM_Schedule WHERE AM_Maintenance_ID = ? AND AD_Org_ID = ? AND Processed = 'N' AND MaintenanceDate BETWEEN ? AND ? ", 
				new Object[]{getMaintenanceDefinition().getAM_Maintenance_ID(), Env.getAD_Org_ID(getMaintenanceDefinition().getCtx()), startDate, endDate}, 
				false, getTrxName());
		logger.fine("Deleted # " + deleted);
		AtomicInteger created = new AtomicInteger(0);
		//	Iterate
		while(startDate.before(endDate)) {
			Timestamp nextMaintenanceDate = getDateNextMaintenance(startDate, endDate);
			if(nextMaintenanceDate != null
					&& (nextMaintenanceDate.after(endDate)
							|| nextMaintenanceDate.before(startDate))
					|| nextMaintenanceDate == null) {
				break;
			} else if(nextMaintenanceDate != null) {
				startDate = nextMaintenanceDate;
				//	Create maintenance here
				createSchedule(nextMaintenanceDate);
				created.getAndIncrement();
			}
		}
		//	Created
		return created.get();
	}
	
	/**
	 * Get date for next maintenance
	 * @param currentDate
	 * @param offsetDate
	 * @return
	 */
	public abstract Timestamp getDateNextMaintenance(Timestamp currentDate, Timestamp offsetDate);
	
	/**
	 * Validate if is applicable a maintenance date
	 * @param maintenanceDate
	 * @return
	 */
	public abstract boolean isValidMaintenanceDate(Timestamp maintenanceDate);
	
	/**
	 * Create Schedule
	 */
	private void createSchedule(Timestamp nextMaintenanceDate) {
		MAMSchedule schedule = new MAMSchedule(maintenance.getCtx(), 0, getTrxName());
		schedule.setMaintenanceDate(nextMaintenanceDate);
		schedule.setMaintenance(maintenance);
		//	Save it
		schedule.saveEx();
	}
}
