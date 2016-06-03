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

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.compiere.util.CCache;

/**
 * Prognosis Model
 * @author OFB Consulting http://www.ofbconsulting.com
 * 	<li> Initial Contributor
 * @contributor Mario Calderon, Systemhaus Westfalia, http://www.westfalia-it.com
 * @contributor Adaxa http://www.adaxa.com
 * @contributor Deepak Pansheriya, Loglite Technologies, http://logilite.com
 * @contributor Victor Perez, victor.perez@e-evolution.com, eEvolution http://www.e-evolution.com
 * @contributor Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 * @contributor Sachin Bhimani
 */
public class MPrognosis extends X_AM_Prognosis
{

	/**
	 * 
	 */
	private static final long					serialVersionUID	= 6370525644757806667L;
	// 5 minutes to expire
	private static CCache<Integer, MPrognosis>	s_cache				= new CCache<Integer, MPrognosis>(Table_Name, 40,
			5);

	public MPrognosis(Properties ctx, int AM_Prognosis_ID, String trxName)
	{
		super(ctx, AM_Prognosis_ID, trxName);
		if (AM_Prognosis_ID == 0)
		{
			setIsActive(true);
			setProcessed(false);
		}
	}

	public MPrognosis(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	/**
	 * Get MPrognosis from Cache
	 * 
	 * @param ctx context
	 * @param AM_Prognosis_ID id
	 * @return MPrognosis or null
	 */
	public static MPrognosis get(Properties ctx, int AM_Prognosis_ID)
	{
		if (AM_Prognosis_ID <= 0)
		{
			return null;
		}

		Integer key = new Integer(AM_Prognosis_ID);

		MPrognosis retValue = (MPrognosis) s_cache.get(key);
		if (retValue != null)
		{
			return retValue;
		}

		retValue = new MPrognosis(ctx, AM_Prognosis_ID, null);
		if (retValue.get_ID() != 0)
		{
			s_cache.put(key, retValue);
		}

		return retValue;
	} // get

	/**
	 * Get MPrognosis from Cache
	 * 
	 * @param ctx context
	 * @param whereClause sql where clause
	 * @param trxName trx
	 * @return MPrognosis
	 */
	public static MPrognosis[] get(Properties ctx, String whereClause, String trxName)
	{
		List<MPrognosis> list = new Query(ctx, Table_Name, whereClause, trxName).setClient_ID().list();
		return list.toArray(new MPrognosis[list.size()]);
	} // get

	public static MPrognosis getByServiceOrder_ID(Properties ctx, int AM_ServiceOrder_ID)
	{

		MPrognosis retValue = new Query(ctx, MPrognosis.Table_Name, "AM_ServiceOrder_ID = ?", null)
				.setParameters(new Object[] { AM_ServiceOrder_ID }).firstOnly();
		return retValue;
	} // getByServiceOrder_ID

	/**
	 * Get Calendar Forecasts
	 * 
	 * @param ctx context
	 * @param trxName optional trx
	 * @param isInFuture true: forecasts in the future; false: forecasts in the
	 *            past
	 * @param dateAndTime starting point to compare (discriminatory)
	 * @return Array of MPrognosis
	 */
	public static MPrognosis[] getCalendarForecastsByTime(Properties ctx, String trxName, boolean isInFuture,
			Timestamp dateAndTime)
	{
		ArrayList<Object> params = new ArrayList<Object>();
		String discriminant;
		if (isInFuture)
			discriminant = " >= ";
		else
			discriminant = " < ";
		params.add(dateAndTime); // discriminant
		params.add("N"); // processed
		params.add("C"); // Programming type
		params.add("Y"); // isActive

		String whereClause = I_AM_Prognosis.COLUMNNAME_DateTrx + discriminant + "?"
				+ " AND processed=? AND AM_ProgrammingType=? AND IsActive=?";

		List<MPrognosis> forecastList = new Query(ctx, I_AM_Prognosis.Table_Name, whereClause, trxName)
				.setParameters(params).setOrderBy(MPrognosis.COLUMNNAME_AM_Prognosis_ID).list();

		MPrognosis[] forecastArray = new MPrognosis[forecastList.size()];
		forecastList.toArray(forecastArray);
		return forecastArray;
	} // getCalendarForecastsByTime

	/**
	 * Delete Calendar Forecasts
	 * 
	 * @param ctx context
	 * @param trxName optional trx
	 * @param isInFuture true: forecasts in the future; false: forecasts in the
	 *            past
	 * @param dateAndTime starting point to compare (discriminatory)
	 * @return Array of MPrognosis
	 */
	public static boolean deleteCalendarForecastsByTime(Properties ctx, String trxName, boolean isInFuture,
			Timestamp dateAndTime)
	{
		boolean result = true;
		MPrognosis[] forecastArray = getCalendarForecastsByTime(ctx, trxName, isInFuture, dateAndTime);
		for (MPrognosis forecast : forecastArray)
		{
			try
			{
				forecast.deleteEx(true);
			}
			catch (Exception e)
			{
				forecast.setDescription("The process of prognosis could not delete this record!!. To do so manually."
						+ forecast.getDescription());
				forecast.saveEx();
				result = false;
			}
		}
		return result;
	} // deleteCalendarForecastsByTime

	/**
	 * Find Maintenance out of Forecast and correct DateNextRun in Calendar
	 * Maintenance
	 * 
	 * @param ctx context
	 * @param trxName optional trx
	 * @param isInFuture true: forecasts in the future; false: forecasts in the
	 *            past
	 * @param dateNow starting point to compare (discriminatory)
	 * @return Array of MPrognosis
	 */
	public static boolean correctCalendarMaintenanceByTime(Properties ctx, String trxName, boolean isInFuture,
			Timestamp dateNow)
	{
		ArrayList<MMaintenance> treatedMaintenances = new ArrayList<MMaintenance>();

		// If required, you can directly access all preventive maintenances:
		MMaintenance maintenances[] = MMaintenance.getByActive(ctx);
		for (MMaintenance maint : maintenances)
		{
			if (treatedMaintenances.contains(maint))
				continue; // Maintenance already treated, so continue in loop

			if (maint.isActive() && maint.getAM_ProgrammingType().equals(AM_PROGRAMMINGTYPE_Calendar))
			{
				maint.correctDateNextRun(dateNow);
				treatedMaintenances.add(maint);
			}
		}

		return true;
	} // correctCalendarMaitenanceByTime

}
