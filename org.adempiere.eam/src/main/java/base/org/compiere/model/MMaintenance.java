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
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

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
public class MMaintenance extends X_AM_Maintenance
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -873522901044261117L;

	public MMaintenance(Properties ctx, int AM_Maintenance_ID, String trxName)
	{
		super(ctx, AM_Maintenance_ID, trxName);
	}

	public MMaintenance(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	/**
	 * get the costs of a Maintenance
	 * 
	 * @param none
	 * @return Costs
	 */
	public BigDecimal getMaintenanceCosts()
	{
		X_AM_Maintenance_Task[] tasks = MMaintenance_Task.getMaintenanceTasks(this);
		BigDecimal maintainCosts = BigDecimal.ZERO;
		for (X_AM_Maintenance_Task task : tasks)
		{
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
	public void updateMaintenanceCosts()
	{
		this.setCostAmt(getMaintenanceCosts());
		this.saveEx();
	} // updateMaintenanceCosts

	/**
	 * calculate and correct the field DateNextRun
	 */
	public void correctDateNextRun(Timestamp dateTimeNow)
	{
		Timestamp calculatedDateNextRun = dateTimeNow;
		Timestamp dateTimeReference;

		// try to get dateNextRun as reference
		if (getDateLastSO() == null)
		{
			dateTimeReference = getDateNextRun() == null ? dateTimeNow : getDateNextRun();
		}
		else
		{
			dateTimeReference = getDateLastSO();
		}
		if (getAM_CalendarType().equals(MMaintenance.AM_CALENDARTYPE_Daily))
			calculatedDateNextRun = calculateDateNextRun(dateTimeNow, dateTimeReference, Calendar.DATE);
		else if (getAM_CalendarType().equals(MMaintenance.AM_CALENDARTYPE_MonthlyFixed))
			calculatedDateNextRun = calculateDateNextRun(dateTimeNow, dateTimeReference, Calendar.MONTH);
		else if (getAM_CalendarType().equals(MMaintenance.AM_CALENDARTYPE_MonthlyRepetitive))
			calculatedDateNextRun = calculateDateNextRun(dateTimeNow, dateTimeReference, Calendar.MONTH);
		else if (getAM_CalendarType().equals(MMaintenance.AM_CALENDARTYPE_Yearly))
			calculatedDateNextRun = calculateDateNextRun(dateTimeNow, dateTimeReference, Calendar.YEAR);
		else if (getAM_CalendarType().equals(MMaintenance.AM_CALENDARTYPE_Weekly))
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
		while (suggestedDate.after(dateNow))
		{ // suggested date AFTER now: go into a PAST date
			if (getAM_CalendarType().equals(MMaintenance.AM_CALENDARTYPE_MonthlyRepetitive)
					|| getAM_CalendarType().equals(MMaintenance.AM_CALENDARTYPE_Weekly))
			{
				// for repetitive months or weekly: DECREMENT months as defined
				// in interval
				suggestedDate.add(calendarIncrement, -getInterval().intValue());
			}
			else if (getAM_CalendarType().equals(MMaintenance.AM_CALENDARTYPE_MonthlyFixed))
			{
				// set day of month as defined in interval
				suggestedDate.add(calendarIncrement, -1);
				suggestedDate.set(Calendar.DAY_OF_MONTH, getInterval().intValue());
			}
			else if (getAM_CalendarType().equals(MMaintenance.AM_CALENDARTYPE_Yearly))
			{
				// set day of year as defined in interval
				suggestedDate.add(calendarIncrement, -1);
				suggestedDate.set(Calendar.DAY_OF_YEAR, getInterval().intValue());
			}
			else
			{
				// for daily Maintenance:DECREMENT just by the Calendar
				suggestedDate.add(calendarIncrement, -1);
			}
		}

		while (suggestedDate.before(dateNow))
		{
			// suggested date BEFORE now: go into a FUTURE date
			if (getAM_CalendarType().equals(MMaintenance.AM_CALENDARTYPE_MonthlyRepetitive)
					|| getAM_CalendarType().equals(MMaintenance.AM_CALENDARTYPE_Weekly))
			{
				// for repetitive months or weekly: INCREMENT months as defined
				// in interval
				suggestedDate.add(calendarIncrement, getInterval().intValue());
			}
			else if (getAM_CalendarType().equals(MMaintenance.AM_CALENDARTYPE_MonthlyFixed))
			{
				// set day of month as defined in interval
				suggestedDate.add(calendarIncrement, 1);
				suggestedDate.set(Calendar.DAY_OF_MONTH, getInterval().intValue());
			}
			else if (getAM_CalendarType().equals(MMaintenance.AM_CALENDARTYPE_Yearly))
			{
				// set day of year as defined in interval
				suggestedDate.add(calendarIncrement, 1);
				suggestedDate.set(Calendar.DAY_OF_YEAR, getInterval().intValue());
			}
			else
			{
				// for daily Maintenance: INCREMENT just by the Calendar
				suggestedDate.add(calendarIncrement, 1);
			}
		}
		// suggested date inside range
		return (new Timestamp(suggestedDate.getTimeInMillis()));

	} // calculateDateNextRun

	public static MMaintenance[] getByActive(Properties ctx)
	{
		String whereClause = "IsActive=? AND AD_Client_ID=?";
		List<MMaintenance> maintainList = new Query(ctx, MMaintenance.Table_Name, whereClause.toString(), null)
				.setParameters(new Object[] { true, Env.getAD_Client_ID(ctx) }).setOnlyActiveRecords(true).list();
		MMaintenance[] maintenances = new MMaintenance[maintainList.size()];
		maintainList.toArray(maintenances);

		return maintenances;
	} // getByActive

}
