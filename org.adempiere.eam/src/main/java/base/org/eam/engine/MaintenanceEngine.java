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
 * Copyright (C) 2012-2018 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpya.com				  		                 *
 *************************************************************************************/
package org.eam.engine;

import java.sql.Timestamp;

import org.compiere.process.DocAction;
import org.eam.model.MAMMaintenance;

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
	 * Create Schedule from engine
	 */
	protected void createSchedule() {
		//	TODO: Implement it
	}
	
	/**
	 * Process Schedule for engine
	 * @param startDate: Start date for maintenance schedule
	 * @param durationUnit: Duration unit (D = Day, W = Week, M = MMonth, Y = Year)
	 * @param duration: Duration for duration unit
	 * @return
	 */
	public abstract boolean processSchedule(Timestamp startDate, String durationUnit, int duration);

	/**
	 * Process a specific document
	 * @return
	 */
	public abstract boolean processDocument();
	
}
