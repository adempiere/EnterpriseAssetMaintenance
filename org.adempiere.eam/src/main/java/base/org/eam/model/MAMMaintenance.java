/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, see http://www.gnu.org/licenses or write to the * 
 * Free Software Foundation, Inc.,                                            *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * Copyright (C) 2003-2016                                                    *
 * All Rights Reserved.                                                       *
 *****************************************************************************/
package org.eam.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;
import org.compiere.util.CCache;
import org.compiere.util.Env;

/**
 * Maintenance
 * @author OFB Consulting http://www.ofbconsulting.com
 * 	<li> Initial Contributor
 * @contributor Mario Calderon, Systemhaus Westfalia, http://www.westfalia-it.com
 * @contributor Adaxa http://www.adaxa.com
 * @contributor Deepak Pansheriya, Loglite Technologies, http://logilite.com
 * @contributor Victor Perez, victor.perez@e-evolution.com, eEvolution http://www.e-evolution.com
 * @contributor Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 * @contributor Sachin Bhimani
 */
public class MAMMaintenance extends X_AM_Maintenance {

	/**
	 * 
	 */
	private static final long serialVersionUID = -873522901044261117L;

	public MAMMaintenance(Properties ctx, int AM_Maintenance_ID, String trxName) {
		super(ctx, AM_Maintenance_ID, trxName);
	}

	public MAMMaintenance(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	/**	Cache						*/
	private static CCache<Integer, MAMMaintenance> cache = new CCache<Integer, MAMMaintenance>(Table_Name, 40, 5);	//	5 minutes

	/**
	 * get the tasks for a job
	 * 
	 * @param Maintenance
	 * @return List with tasks
	 */
	public List<MAMMaintenanceTask> getTasks() {
		String whereClause = COLUMNNAME_AM_Maintenance_ID + "=? ";
		List<MAMMaintenanceTask> list = new Query(getCtx(), I_AM_MaintenanceTask.Table_Name,
				whereClause, get_TrxName())
						.setClient_ID()
						.setParameters(getAM_Maintenance_ID())
						.setOnlyActiveRecords(true)
						.list();
		return list;
	} // getMaintenanceTasks
	
	/**
	 * get the costs of a Maintenance
	 * 
	 * @param none
	 * @return Costs
	 */
	public BigDecimal getMaintenanceCosts() {
		BigDecimal maintainCosts = BigDecimal.ZERO;
		for (X_AM_MaintenanceTask task : getTasks()){
			maintainCosts = maintainCosts.add(task.getCostAmt());
		}
		return maintainCosts;
	} // getMaintainCosts

	/**
	 * update the costs of a job
	 * 
	 * @param none
	 * @return none
	 */
	public void updateMaintenanceCosts() {
		this.setCostAmt(getMaintenanceCosts());
		this.saveEx();
	} // updateMaintenanceCosts

	/**
	 * calculate and correct the field DateNextRun
	 */
	public void correctDateNextRun(Timestamp dateTimeNow) {
		Timestamp calculatedDateNextRun = dateTimeNow;
		Timestamp dateTimeReference;

		// try to get dateNextRun as reference
		if (getDateLastServiceOrder() == null) {
			dateTimeReference = getDateNextRun() == null ? dateTimeNow : getDateNextRun();
		} else {
			dateTimeReference = getDateLastServiceOrder();
		}
		if (getFrequencyType().equals(MAMMaintenance.FREQUENCYTYPE_Day))
			calculatedDateNextRun = calculateDateNextRun(dateTimeNow, dateTimeReference, Calendar.DATE);
		else if (getFrequencyType().equals(MAMMaintenance.FREQUENCYTYPE_Monthly))
			calculatedDateNextRun = calculateDateNextRun(dateTimeNow, dateTimeReference, Calendar.MONTH);
		else if (getFrequencyType().equals(MAMMaintenance.FREQUENCYTYPE_Yearly))
			calculatedDateNextRun = calculateDateNextRun(dateTimeNow, dateTimeReference, Calendar.YEAR);
		else if (getFrequencyType().equals(MAMMaintenance.FREQUENCYTYPE_Weekly))
			calculatedDateNextRun = calculateDateNextRun(dateTimeNow, dateTimeReference, Calendar.WEEK_OF_YEAR);

		setDateNextRun(calculatedDateNextRun);
		saveEx();
	} // correctDateNextRun

	/**
	 * calculates -based on the reference- the dateNextRun
	 * 
	 * @param dateTimeNow
	 * @param calendarIncrement depending on the Calendar Type
	 * @return Timestamp
	 */
	public Timestamp calculateDateNextRun(Timestamp dateTimeNow, Timestamp dateTimeReference, int calendarIncrement)
	{
		Calendar dateNow = Calendar.getInstance();
		dateNow.setTimeInMillis(dateTimeNow.getTime());

		Calendar suggestedDate = Calendar.getInstance();
		suggestedDate.setTimeInMillis(dateTimeReference.getTime());

		// First future date is brought to past, then past is brought to NEXT
		// future, preserving interval
		while (suggestedDate.after(dateNow)) { // suggested date AFTER now: go into a PAST date
			if (getFrequencyType().equals(MAMMaintenance.FREQUENCYTYPE_Monthly)
					|| getFrequencyType().equals(MAMMaintenance.FREQUENCYTYPE_Weekly)) {
				// for repetitive months or weekly: DECREMENT months as defined
				// in interval
				suggestedDate.add(calendarIncrement, - getInterval().intValue());
			} else if (getFrequencyType().equals(MAMMaintenance.FREQUENCYTYPE_Monthly)) {	//	TODO: Add IsFixed attribute
				// set day of month as defined in interval
				suggestedDate.add(calendarIncrement, -1);
				suggestedDate.set(Calendar.DAY_OF_MONTH, getInterval().intValue());
			} else if (getFrequencyType().equals(MAMMaintenance.FREQUENCYTYPE_Yearly)) {
				// set day of year as defined in interval
				suggestedDate.add(calendarIncrement, -1);
				suggestedDate.set(Calendar.DAY_OF_YEAR, getInterval().intValue());
			} else {
				// for daily Maintenance:DECREMENT just by the Calendar
				suggestedDate.add(calendarIncrement, -1);
			}
		}
		//	Iterate
		while (suggestedDate.before(dateNow)) {
			// suggested date BEFORE now: go into a FUTURE date
			if (getFrequencyType().equals(MAMMaintenance.FREQUENCYTYPE_Monthly)
					|| getFrequencyType().equals(MAMMaintenance.FREQUENCYTYPE_Weekly)) {
				// for repetitive months or weekly: INCREMENT months as defined
				// in interval
				suggestedDate.add(calendarIncrement, getInterval().intValue());
			} else if (getFrequencyType().equals(MAMMaintenance.FREQUENCYTYPE_Monthly)) {	//	TODO_ Add Fixed attribute
				// set day of month as defined in interval
				suggestedDate.add(calendarIncrement, 1);
				suggestedDate.set(Calendar.DAY_OF_MONTH, getInterval().intValue());
			} else if (getFrequencyType().equals(MAMMaintenance.FREQUENCYTYPE_Yearly)) {
				// set day of year as defined in interval
				suggestedDate.add(calendarIncrement, 1);
				suggestedDate.set(Calendar.DAY_OF_YEAR, getInterval().intValue());
			} else {
				// for daily Maintenance: INCREMENT just by the Calendar
				suggestedDate.add(calendarIncrement, 1);
			}
		}
		// suggested date inside range
		return (new Timestamp(suggestedDate.getTimeInMillis()));

	} // calculateDateNextRun

	/**
	 * Get All
	 * @param ctx
	 * @return
	 */
	public static List<MAMMaintenance> getByActive(Properties ctx) {
		List<MAMMaintenance> list = new Query(ctx, Table_Name, null, null)
				.setParameters(true, Env.getAD_Client_ID(ctx))
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.list();
		return list;
	} // getByActive

	/**
	 * Get from cache
	 * @param ctx
	 * @param maintenanceId
	 * @return
	 */
	public static MAMMaintenance get (Properties ctx, int maintenanceId) {
		if (maintenanceId <= 0) {
			return null;
		}
		MAMMaintenance retValue = (MAMMaintenance) cache.get (maintenanceId);
		if (retValue != null) {
			return retValue;
		}
		retValue = new MAMMaintenance(ctx, maintenanceId, null);
		if (retValue.get_ID () != 0) {
			cache.put (maintenanceId, retValue);
		}
		return retValue;
	}	//	get
	
	/**
	 * Create Tasks and resources from maintenance pattern
	 * @param patternId
	 * @return
	 */
	public boolean createFromPattern(int patternId) {
		//	Validate if exist pattern
		if(getAM_Maintenance_ID() <= 0) {
			return false;
		}
		//	Already have tasks
		List<MAMMaintenanceTask> tasks = getTasks();
		if(tasks == null
				|| tasks.size() > 0) {
			return false;
		}
		MAMPattern pattern = MAMPattern.get(getCtx(), patternId);
		//	Iterate
		pattern.getTasks().stream().forEach(patternTask -> {
			MAMMaintenanceTask maintenanceTask = new MAMMaintenanceTask(getCtx(), 0, get_TrxName());
			maintenanceTask.setAM_Maintenance_ID(getAM_Maintenance_ID());
			maintenanceTask.setPatternTask(patternTask);
			//	Save
			maintenanceTask.saveEx();
			//	Get Resource
			patternTask.getResources().stream().forEach(taskResource -> {
				MAMMaintenanceResource maintenanceResource = new MAMMaintenanceResource(getCtx(), 0, get_TrxName());
				maintenanceResource.setAM_MaintenanceTask_ID(maintenanceTask.getAM_MaintenanceTask_ID());
				maintenanceResource.setPatternResource(taskResource);
				maintenanceResource.saveEx();
			});
		});
		//	
		setAM_Pattern_ID(pattern.getAM_Pattern_ID());
		// copy Asset and Asset Group from Maintenance Pattern
		setA_Asset_ID(pattern.getA_Asset_ID());
		// copy Maintenance Area and Costs from Maintenance Pattern
		setCostAmt(pattern.getCostAmt());
		setAM_Area_ID(pattern.getAM_Area_ID());
		saveEx();
		//	Return ok
		return true;
	}
	
	@Override
	public String toString() {
		return "MAMMaintenance [getAM_Maintenance_ID()=" + getAM_Maintenance_ID() + ", getDocumentNo()="
				+ getDocumentNo() + "]";
	}
}
