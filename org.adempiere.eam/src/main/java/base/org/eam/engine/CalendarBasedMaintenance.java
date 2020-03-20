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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;

import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eam.model.MAMMaintenance;

/**
 * Maintenance class for handle Time Based Maintenance (PVM)
 * @author Yamel Senih, ysenih@erpya.com , http://www.erpya.com
 */
public class CalendarBasedMaintenance extends MaintenanceEngine {
	
	@Override
	public Timestamp getDateNextMaintenance(Timestamp scheduleDate, Timestamp offseDate) {
		Timestamp dateLastMaintenance = null;
		Timestamp dateNextMaintenance = null;
		if (getMaintenanceDefinition().getDateLastServiceOrder() == null) {
			dateLastMaintenance = getMaintenanceDefinition().getDateNextRun() == null? 
					scheduleDate: 
					getMaintenanceDefinition().getDateNextRun();
		} else {
			dateLastMaintenance = getMaintenanceDefinition().getDateLastServiceOrder();
		}
		//	
		if(dateLastMaintenance.after(scheduleDate)) {
			scheduleDate = dateLastMaintenance;
		}
		String frequencyType = getMaintenanceDefinition().getFrequencyType();
		BigDecimal inverval = getMaintenanceDefinition().getInterval();
		if(inverval == null) {
			inverval = Env.ZERO;
		}
		int offset = inverval.intValue();
		if(offset == 0) {
			offset = 1;
		}
		if(frequencyType.equals(MAMMaintenance.FREQUENCYTYPE_Day)) {
			do {
				dateNextMaintenance = TimeUtil.addDays(scheduleDate, offset);
				if(isValidMaintenanceDate(dateNextMaintenance)) {
					break;
				}
				scheduleDate = dateNextMaintenance;
			} while(dateNextMaintenance.before(offseDate));
		} else if(frequencyType.equals(MAMMaintenance.FREQUENCYTYPE_Weekly)) {
			dateNextMaintenance = TimeUtil.addDuration(scheduleDate, TimeUtil.DURATIONUNIT_Week, offset);
		} else if(frequencyType.equals(MAMMaintenance.FREQUENCYTYPE_Biweekly)) {
			dateNextMaintenance = TimeUtil.addDuration(scheduleDate, TimeUtil.DURATIONUNIT_Week, offset * 2);
		} else if(frequencyType.equals(MAMMaintenance.FREQUENCYTYPE_Monthly)) {
			dateNextMaintenance = TimeUtil.addMonths(scheduleDate, offset);
		} else if(frequencyType.equals(MAMMaintenance.FREQUENCYTYPE_Yearly)) {
			dateNextMaintenance = TimeUtil.addYears(scheduleDate, offset);
		}
		//	Default
		return dateNextMaintenance;
	}

	@Override
	public boolean isValidMaintenanceDate(Timestamp maintenanceDate) {
		//	Validate with last run
		if (getMaintenanceDefinition().getDateLastServiceOrder() != null
				&& getMaintenanceDefinition().getDateLastServiceOrder().after(maintenanceDate)) {
			return false;
		}
		//	Validate with next run
		if(getMaintenanceDefinition().getDateNextRun() != null
				&& getMaintenanceDefinition().getDateNextRun().after(maintenanceDate)) {
			return false;
		}
		if(getMaintenanceDefinition().getFrequencyType().equals(MAMMaintenance.FREQUENCYTYPE_Day)) {
			Calendar maintenanceDateAsCalendar = TimeUtil.getCalendar(maintenanceDate);
			if ((getMaintenanceDefinition().isOnSaturday() && maintenanceDateAsCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
					|| (getMaintenanceDefinition().isOnSunday() && maintenanceDateAsCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
					|| (getMaintenanceDefinition().isOnMonday() && maintenanceDateAsCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY)
					|| (getMaintenanceDefinition().isOnTuesday() && maintenanceDateAsCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY)
					|| (getMaintenanceDefinition().isOnWednesday() && maintenanceDateAsCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY)
					|| (getMaintenanceDefinition().isOnThursday() && maintenanceDateAsCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY)
					|| (getMaintenanceDefinition().isOnFriday() && maintenanceDateAsCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)) {
				return true;
			} else {
				return false;
			}
		}
		return true;
	}
}
