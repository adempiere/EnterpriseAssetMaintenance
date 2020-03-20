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

package org.eam.process;

import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicInteger;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_Ref_List;
import org.compiere.model.MRefList;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util;
import org.eam.engine.MaintenanceEngine;
import org.eam.engine.MaintenanceEngineFactory;
import org.eam.model.I_AM_Maintenance;
import org.eam.model.MAMMaintenance;

/** 
 * Create Schedule from Maintenance Definition
 * 	@author Yamel Senih, ysenih@erpya.com , http://www.erpya.com
 */
public class CreateScheduleForMaintenance extends CreateScheduleForMaintenanceAbstract {
	@Override
	protected void prepare() {
		super.prepare();
		if(getInterval() == null
				|| getInterval().equals(Env.ZERO)) {
			throw new AdempiereException("@Interval@ @NotFound@");
		}
		counter = new AtomicInteger(0);
	}

	/**	Day	*/
	private final String DAY = "D";
	/**	Week	*/
	private final String WEEK = "W";
	/**	Month	*/
	private final String MONTH = "M";
	/**	Year	*/
	private final String YEAR = "Y";
	private Timestamp offsetDate;
	/**	Counter	*/
	private AtomicInteger counter;
	
	@Override
	protected String doIt() throws Exception {
		offsetDate = getStartDate();
		int offset = getInterval().intValue();
		if(offset == 0) {
			offset = 1;
		}
		if(getTimeUnit().equals(DAY)) {
			offsetDate = TimeUtil.addDays(getStartDate(), offset);
		} else if(getTimeUnit().equals(WEEK)) {
			offsetDate = TimeUtil.addDuration(getStartDate(), TimeUtil.DURATIONUNIT_Week, offset);
		} else if(getTimeUnit().equals(MONTH)) {
			offsetDate = TimeUtil.addMonths(getStartDate(), offset);
		} else if(getTimeUnit().equals(YEAR)) {
			offsetDate = TimeUtil.addYears(getStartDate(), offset);
		}
		//	Get Engine
		if(Util.isEmpty(getMaintenanceType())) {
			new Query(getCtx(), I_AD_Ref_List.Table_Name, I_AD_Ref_List.COLUMNNAME_AD_Reference_ID + " = ?", get_TrxName())
				.setParameters(MAMMaintenance.MAINTENANCETYPE_AD_Reference_ID)
				.setOnlyActiveRecords(true)
				.<MRefList>list()
				.forEach(reference -> {
					generateSchedule(reference.getValue());
				});
		} else {
			generateSchedule(getMaintenanceType());
		}
		return "@Created@ " + counter.get();
	}
	
	/**
	 * Generate Schedule for Asset
	 * @param maintenanceType
	 */
	private void generateSchedule(String maintenanceType) {
		new Query(getCtx(), I_AM_Maintenance.Table_Name, I_AM_Maintenance.COLUMNNAME_MaintenanceType + " = ? AND " + I_AM_Maintenance.COLUMNNAME_Status + " <> ?", get_TrxName())
			.setParameters(maintenanceType, MAMMaintenance.STATUS_InActive)
			.setOnlyActiveRecords(true)
			.setClient_ID()
			.<MAMMaintenance>list()
			.forEach(maintenanceDefinition -> {
				try {
					MaintenanceEngine engine = MaintenanceEngineFactory.getInstance().getEngine(maintenanceDefinition);
					engine.setTrxName(get_TrxName());
					counter.addAndGet(engine.createSchedule(getStartDate(), getOffsetDate()));
				} catch (Exception e) {
					addLog(e.getLocalizedMessage());
				}
			});
	}
	
	/**
	 * Get offset date
	 * @return
	 */
	private Timestamp getOffsetDate() {
		return offsetDate;
	}
}