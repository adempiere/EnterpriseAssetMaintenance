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
package org.compiere.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;

import org.compiere.model.MMaintenance;
import org.compiere.model.MPrognosis;
import org.compiere.model.X_AM_Maintenance;
import org.compiere.model.X_AM_Prognosis;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author OFB Consulting http://www.ofbconsulting.com
 * 	<li> Initial Contributor
 * @contributor Mario Calderon, Systemhaus Westfalia, http://www.westfalia-it.com
 * @contributor Adaxa http://www.adaxa.com
 * @contributor Deepak Pansheriya, Loglite Technologies, http://logilite.com
 * @contributor Victor Perez, victor.perez@e-evolution.com, eEvolution http://www.e-evolution.com
 * @contributor Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 * @contributor Sachin Bhimani
 */
public class AMAssetPlanning extends SvrProcess
{
	static final int	daysPerWeek				= 7;

	private int			AD_PInstance_ID;
	int					numberOfWeeks			= 0;
	boolean				deleteFutureForecasts	= false;
	String				stringDate;

	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("PeriodNo"))
				numberOfWeeks = para[i].getParameterAsInt();
			else if (name.equals("isDeletable"))
				deleteFutureForecasts = para[i].getParameterAsBoolean();
			else
				log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
		}

		AD_PInstance_ID = getAD_PInstance_ID();

		if (DB.isOracle())
			stringDate = "sysdate";
		else
			stringDate = "now()";
	} // prepare

	@Override
	protected String doIt() throws Exception
	{
		StringBuffer resultMsg = new StringBuffer();
		resultMsg.append("<br>" + "Calculate Maintenance: Beginning");
		resultMsg.append("<br>" + "Weeks for predicting: " + numberOfWeeks);
		resultMsg.append("<br>");

		Date todayAsDate = new java.util.Date();
		Timestamp todayAsTime = new Timestamp(todayAsDate.getTime());

		if (deleteFutureForecasts)
		{
			String resultdeleteForecasts = deleteFutureCalendarForecasts(todayAsTime);
			resultMsg.append(resultdeleteForecasts);
		}

		String resultCalender = forecastByCalendar(todayAsTime);
		resultMsg.append(resultCalender);

		String resultMeter = forecastByMeter();
		resultMsg.append(resultMeter);

		resultMsg.append("<br>");
		resultMsg.append("<br>" + "Calculate Maintenance: Finished");
		return resultMsg.toString();
	} // doIt

	private String deleteFutureCalendarForecasts(Timestamp dateAndTime)
	{
		StringBuffer resultMsg = new StringBuffer();
		resultMsg.append("<br>" + "Delete forecasts after " + dateAndTime + ": Beginning");

		// Delete all forecasts which lie in the future
		resultMsg.append("<br>" + "-- Delete forecasts: Beginning");
		if (!MPrognosis.deleteCalendarForecastsByTime(Env.getCtx(), this.get_TrxName(), true, dateAndTime))
			resultMsg.append("<br>" + "----Error to delete forecasts");
		resultMsg.append("<br>" + "--Delete forecast: finished");

		// Correct every "dateNextRun" of the corresponding Maintenance to
		// today.
		resultMsg.append("<br>" + "--To correct Maintenance for calendar: Beginning");
		MPrognosis.correctCalendarMaintenanceByTime(Env.getCtx(), this.get_TrxName(), false, dateAndTime);
		resultMsg.append("<br>" + "--To correct Maintenance for calendar: finished");

		resultMsg.append("<br>" + "Delete forecasts after " + dateAndTime + ": finished");
		resultMsg.append("<br>");

		return resultMsg.toString();
	} // deleteFutureForecasts

	private String forecastByCalendar(Timestamp today)
	{
		StringBuffer resultMsg = new StringBuffer();

		// SEARCHING BY CALENDAR
		resultMsg.append("<br>" + "To calculate Maintenance for Calendar: Beginning");
		StringBuffer byCalendar = new StringBuffer();
		byCalendar.append(
				"SELECT m.AD_Client_ID, m.AD_Org_ID, m.AM_Maintenance_ID, m.Description, m.AM_ProgrammingType,");
		byCalendar.append("	a.A_Asset_ID, DateNextRun, m.DateLastRun, m.Interval, m.DocumentNo, m.AM_CalendarType,");
		byCalendar
				.append("m.IsSunday, m.IsMonday, m.IsTuesday, m.IsWednesday, m.IsThursday, m.IsFriday, m.IsSaturday,");
		// unused columns, but otherwise exception when instantiating
		// Maintenance
		byCalendar.append(
				"m.A_Asset_Group_ID, m.UpdatedBy, m.Range, m.AverageUse, m.PriorityRule, m.NextAM, m.AM_Meter_ID,");
		byCalendar.append(
				"m.AM_MaintenanceParent_ID, m.AD_User_ID, m.Created, m.CreatedBY, m.CostAmt, m.CurrentAM, m.DateLastSO,");
		byCalendar.append(
				"m.DateLastRunAM, m.IsActive, m.Updated, m.DocStatus, m.LastAM, m.LastRead, m.MaintenanceArea,");
		byCalendar.append("m.AM_MaintenancePattern_ID, m.IsChild ");
		byCalendar.append("FROM AM_Maintenance m ");
		byCalendar.append(
				"INNER JOIN A_Asset A ON (m.A_Asset_ID=A.A_Asset_ID OR A.A_Asset_Group_ID=m.A_Asset_Group_ID) ");
		byCalendar.append("WHERE AM_ProgrammingType = 'C' AND DateNextRun BETWEEN NOW()-60 AND NOW()+(7* "
				+ numberOfWeeks + ") AND m.DocStatus <> 'IT' AND m.IsActive='Y' ");
		byCalendar.append("ORDER BY DateNextRun ASC ");

		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;
		try
		{
			pstmt1 = DB.prepareStatement(byCalendar.toString(), get_TrxName());
			rs1 = pstmt1.executeQuery();

			Calendar datePeriodFrom = Calendar.getInstance();
			Calendar datePeriodEnd = (Calendar) datePeriodFrom.clone();
			datePeriodEnd.add(Calendar.DATE, daysPerWeek * numberOfWeeks);

			Timestamp endOfForecast = new Timestamp(datePeriodEnd.getTimeInMillis());
			String endOfForecastAsString = new SimpleDateFormat("dd/MM/yyyy").format(endOfForecast);
			resultMsg.append("<br>" + "(Date end of predicting for Calendar: " + endOfForecastAsString + ")" + "<br>");

			// for each Preventive Maintenance within range
			while (rs1.next())
			{
				resultMsg.append("<br>" + "--Beginning of forecast of Maintenance \"" + rs1.getString("documentno")
						+ "\" - " + rs1.getString("DESCRIPTION"));

				datePeriodFrom.setTimeInMillis(today.getTime());
				Calendar datePeriodTo = (Calendar) datePeriodFrom.clone();
				datePeriodTo.add(Calendar.DATE, daysPerWeek);

				int frequency = rs1.getInt("interval");
				Timestamp dateNextRun = rs1.getTimestamp("DATENEXTRUN");

				resultMsg.append("<br>" + "----Forecast From: " + dateNextRun);

				String calendarType = rs1.getString("AM_CalendarType");
				resultMsg.append("<br>" + "----Type of calendar: \"" + calendarType + "\"");

				String calendarTypeResult = "";
				if (calendarType.equals(MMaintenance.AM_CALENDARTYPE_Daily))
				{
					// bitset 0 is not taken into account
					BitSet daysToMantain = new BitSet(daysPerWeek + 1);
					if (rs1.getString("IsSunday").equals("Y"))
						daysToMantain.set(1); // Calendar.SUNDAY==1
					if (rs1.getString("IsMonday").equals("Y"))
						daysToMantain.set(2);
					if (rs1.getString("IsTuesday").equals("Y"))
						daysToMantain.set(3);
					if (rs1.getString("IsWednesday").equals("Y"))
						daysToMantain.set(4);
					if (rs1.getString("IsThursday").equals("Y"))
						daysToMantain.set(5);
					if (rs1.getString("IsFriday").equals("Y"))
						daysToMantain.set(6);
					if (rs1.getString("IsSaturday").equals("Y"))
						daysToMantain.set(7); // Calendar.SATURDAY==7

					calendarTypeResult = forecastByCalendarDay(rs1, datePeriodFrom, datePeriodEnd, daysToMantain,
							dateNextRun);
				}
				else if (calendarType.equals(MMaintenance.AM_CALENDARTYPE_MonthlyFixed))
				{
					calendarTypeResult = forecastByCalendarMonthFix(rs1, datePeriodFrom, datePeriodEnd, frequency,
							dateNextRun);
				}
				else if (calendarType.equals(MMaintenance.AM_CALENDARTYPE_MonthlyRepetitive))
				{
					calendarTypeResult = forecastByCalendarMonthRepetitive(rs1, datePeriodFrom, datePeriodEnd,
							frequency, dateNextRun);
				}
				else if (calendarType.equals(MMaintenance.AM_CALENDARTYPE_Yearly))
				{
					calendarTypeResult = forecastByCalendarYear(rs1, datePeriodFrom, datePeriodEnd, frequency,
							dateNextRun);
				}
				else if (calendarType.equals(MMaintenance.AM_CALENDARTYPE_Weekly))
				{
					if (frequency == 0)
					{
						resultMsg.append("<br>"
								+ "----The value of the field Interval = 0 ==> is not predicted for this Maintenance.");
						resultMsg.append(
								"<br>" + "--End of forecast for \"" + rs1.getString("documentno") + "\"" + "<br>");
						continue;
					}
					else
						calendarTypeResult = forecastByCalendarWeek(rs1, datePeriodFrom, datePeriodTo, datePeriodEnd,
								frequency, dateNextRun);
				}
				else
				{ // unknown value for CalendarType
					resultMsg.append("<br>"
							+ "----Value not known for Type of Calendar ==> is not predicted for this Maintenance");
					resultMsg.append("<br>" + "--End of forecast for \"" + rs1.getString("documentno") + "\"" + "<br>");
					continue;
				}
				resultMsg.append(calendarTypeResult);
				resultMsg.append("<br>" + "--End of forecast for Maintenance \"" + rs1.getString("documentno") + "\" - "
						+ rs1.getString("DESCRIPTION") + "<br>");
			}
			commitEx();
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, e.getMessage(), e);
			resultMsg.append("<br>" + "----SERIOUS ERROR ON HAVING PREDICTED MAINTENANCE FOR CALENDAR.");
		}
		finally
		{
			DB.close(rs1, pstmt1);
			rs1 = null;
			pstmt1 = null;
		}
		resultMsg.append("<br>" + "To calculate Maintenance for Calendar: End");
		resultMsg.append("<br>");

		// Nested Maintenance
		resultMsg.append("<br>" + "Calculate Maintenance for nested: Beginning");
		log.info("Correction for nested");
		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;
		try
		{
			pstmt2 = DB.prepareStatement(byCalendar.toString(), get_TrxName());
			rs2 = pstmt2.executeQuery();
			while (rs2.next())
			{
				int maintenanceID = DB.getSQLValue(get_TrxName(), "SELECT p.AM_Maintenance_ID FROM AM_Prognosis p "
						+ " INNER JOIN AM_Maintenance m ON (p.AM_Maintenance_ID=m.AM_Maintenance_ID) "
						+ " WHERE m.AM_MaintenanceParent_ID=" + rs2.getInt("AM_Maintenance_ID") + " AND p.A_Asset_ID="
						+ rs2.getInt("A_Asset_ID") + " AND p.AD_PINSTANCE_ID=" + AD_PInstance_ID);

				while (maintenanceID > 0)
				{
					DB.executeUpdate("UPDATE AM_Prognosis SET IsSelected='Y' WHERE A_Asset_ID="
							+ rs2.getInt("A_ASSET_ID") + " AND AM_Maintenance_ID=" + maintenanceID
							+ " AND AD_PINSTANCE_ID=" + AD_PInstance_ID, get_TrxName());

					maintenanceID = DB.getSQLValue(get_TrxName(),
							"SELECT P.AM_Maintenance_ID FROM AM_Prognosis P "
									+ "INNER JOIN AM_Maintenance M ON (P.AM_Maintenance_ID=M.AM_Maintenance_ID) "
									+ "WHERE M.AM_MaintenanceParent_ID =" + maintenanceID + " AND P.A_ASSET_ID="
									+ rs2.getInt("ASSET_ID") + " AND P.AD_PINSTANCE_ID=" + AD_PInstance_ID);
				}
			}
			commitEx();
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, e.getMessage(), e);
			resultMsg.append("<br>" + "----Fatal Error when calculating nested Maintenance By Calendar");
		}
		finally
		{
			DB.close(rs2, pstmt2);
			rs2 = null;
			pstmt2 = null;
		}
		resultMsg.append("<br>" + "Calculate Maintenance for nested: Finished");

		return resultMsg.toString();
	} // forecastByCalendar

	private String forecastByCalendarDay(ResultSet rs1, Calendar datePeriodFrom, Calendar datePeriodEnd,
			BitSet weekProgram, Timestamp dateNextRun)
	{
		StringBuffer resultMsg = new StringBuffer();
		Calendar nextRun = Calendar.getInstance();
		nextRun.setTimeInMillis(dateNextRun.getTime());

		MMaintenance maintenance = new MMaintenance(getCtx(), rs1, get_TrxName());

		while (datePeriodFrom.before(datePeriodEnd) && nextRun.before(datePeriodEnd))
		{
			int forecastWeek = nextRun.get(Calendar.WEEK_OF_YEAR);
			Calendar dayDateInWeek = (Calendar) nextRun.clone();
			for (int i = 0; i < daysPerWeek; i++) // iterate the week
			{
				Timestamp dateForcastRun = new Timestamp(dayDateInWeek.getTimeInMillis());
				String dateForcastRunAsString = new SimpleDateFormat("dd/MM/yyyy").format(dateForcastRun);

				if ((dayDateInWeek.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY && weekProgram.get(Calendar.SUNDAY))
						|| (dayDateInWeek.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY
								&& weekProgram.get(Calendar.MONDAY))
						|| (dayDateInWeek.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY
								&& weekProgram.get(Calendar.TUESDAY))
						|| (dayDateInWeek.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY
								&& weekProgram.get(Calendar.WEDNESDAY))
						|| (dayDateInWeek.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY
								&& weekProgram.get(Calendar.THURSDAY))
						|| (dayDateInWeek.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY
								&& weekProgram.get(Calendar.FRIDAY))
						|| (dayDateInWeek.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
								&& weekProgram.get(Calendar.SATURDAY)))
				{
					if (checkAndCreateForecastDate(rs1, forecastWeek, dateForcastRun,
							"Planificación by schedule (Daily): " + dateForcastRunAsString))
						resultMsg.append("<br>" + "----forecast daily created for date" + dateForcastRunAsString);
					else
						resultMsg.append("<br>" + "----Forecast daily not created for date " + dateForcastRunAsString
								+ " (Forecast exists already for this date)");

					maintenance.setDateLastRun(new Timestamp(dayDateInWeek.getTimeInMillis()));
					Calendar tempDate = (Calendar) dayDateInWeek.clone();
					tempDate.add(Calendar.DATE, 1);
					maintenance.setDateNextRun(new Timestamp(tempDate.getTimeInMillis()));
					maintenance.saveEx();
				}
				nextRun.add(Calendar.DATE, 1); // add one day
				dayDateInWeek.add(Calendar.DATE, 1); // add one day
			}
			datePeriodFrom = (Calendar) nextRun.clone();
		}
		return resultMsg.toString();
	} // forecastByCalendarDay

	private String forecastByCalendarMonthFix(ResultSet rs1, Calendar datePeriodFrom, Calendar datePeriodEnd,
			int dayOfMonth, Timestamp dateNextRun)
	{
		StringBuffer resultMsg = new StringBuffer();
		Calendar nextRun = Calendar.getInstance();
		nextRun.setTimeInMillis(dateNextRun.getTime());
		nextRun.set(Calendar.DAY_OF_MONTH, dayOfMonth);

		Calendar today = Calendar.getInstance();
		if (nextRun.before(today))
		{
			// add one month, because Maintenance was in the past
			nextRun.add(Calendar.MONTH, 1);
		}

		MMaintenance maintenance = new MMaintenance(getCtx(), rs1, get_TrxName());

		while (datePeriodFrom.before(datePeriodEnd) && nextRun.before(datePeriodEnd))
		{
			int forecastWeek = nextRun.get(Calendar.WEEK_OF_YEAR);
			Timestamp dateForcastRun = new Timestamp(nextRun.getTimeInMillis());
			String dateForcastRunAsString = new SimpleDateFormat("dd/MM/yyyy").format(dateForcastRun);

			if (dayOfMonth >= nextRun.getMinimum(Calendar.DAY_OF_MONTH)
					&& dayOfMonth <= nextRun.getMaximum(Calendar.DAY_OF_MONTH))
			{
				if (checkAndCreateForecastDate(rs1, forecastWeek, dateForcastRun,
						"Forecast for Calendar (monthly Fixed): " + dateForcastRunAsString))
					resultMsg.append("<br>" + "----Forecast monthly fixed created for date " + dateForcastRunAsString);
				else
					resultMsg.append("<br>" + "----Forecast monthly fixed not created for date "
							+ dateForcastRunAsString + " (Forecast already exists for this date)");
			}
			else
			{
				resultMsg.append("<br>" + "----Forecast monthly fixed not created for date " + dateForcastRunAsString
						+ ". to of the month (" + dayOfMonth + ")");
			}

			maintenance.setDateLastRun(new Timestamp(nextRun.getTimeInMillis()));
			Calendar tempDate = (Calendar) nextRun.clone();
			tempDate.add(Calendar.MONTH, 1);
			maintenance.setDateNextRun(new Timestamp(tempDate.getTimeInMillis()));
			maintenance.saveEx();

			nextRun.add(Calendar.MONTH, 1); // add one month
			datePeriodFrom.add(Calendar.MONTH, 1); // add one month
		}
		return resultMsg.toString();
	} // forecastByCalendarMonthFix

	private String forecastByCalendarMonthRepetitive(ResultSet rs1, Calendar datePeriodFrom, Calendar datePeriodEnd,
			int frequency, Timestamp dateNextRun)
	{
		StringBuffer resultMsg = new StringBuffer();
		Calendar nextRun = Calendar.getInstance();
		nextRun.setTimeInMillis(dateNextRun.getTime());

		MMaintenance maintenance = new MMaintenance(getCtx(), rs1, get_TrxName());

		while (datePeriodFrom.before(datePeriodEnd) && nextRun.before(datePeriodEnd))
		{
			int forecastWeek = nextRun.get(Calendar.WEEK_OF_YEAR);
			Timestamp dateForcastRun = new Timestamp(nextRun.getTimeInMillis());
			String dateForcastRunAsString = new SimpleDateFormat("dd/MM/yyyy").format(dateForcastRun);

			if (checkAndCreateForecastDate(rs1, forecastWeek, dateForcastRun,
					"Forecast for Calendar (monthly repetitive): " + dateForcastRunAsString))
				resultMsg.append("<br>" + "----Forecast monthly repetitive created for date " + dateForcastRunAsString);
			else
				resultMsg.append("<br>" + "----Forecast monthly repetitive not created for date "
						+ dateForcastRunAsString + " (Forecast already exists for this date)");

			maintenance.setDateLastRun(new Timestamp(nextRun.getTimeInMillis()));
			Calendar tempDate = (Calendar) nextRun.clone();
			tempDate.add(Calendar.MONTH, frequency);
			maintenance.setDateNextRun(new Timestamp(tempDate.getTimeInMillis()));
			maintenance.saveEx();

			nextRun.add(Calendar.MONTH, frequency); // add months as required
			datePeriodFrom.add(Calendar.MONTH, frequency); // add months as
															// required
		}
		return resultMsg.toString();
	} // forecastByCalendarMonthRepetitive

	private String forecastByCalendarWeek(ResultSet rs1, Calendar datePeriodFrom, Calendar datePeriodTo,
			Calendar datePeriodEnd, int numberOfWeeks, Timestamp dateNextRun)
	{
		// calendarType==X_MP_Maintain.CALENDARTYPE_Weekly
		StringBuffer resultMsg = new StringBuffer();
		String dateNextRunAsString = new SimpleDateFormat("dd/MM/yyyy").format(dateNextRun);
		int forecastWeek;

		while (datePeriodFrom.before(datePeriodEnd))
		{
			// find week where maintenance belongs to
			if (dateNextRun.compareTo(datePeriodFrom.getTime()) >= 0
					&& dateNextRun.compareTo(datePeriodTo.getTime()) <= 0)
			{
				forecastWeek = datePeriodFrom.get(Calendar.WEEK_OF_YEAR);

				// do not create forecast if this forecast already exists for
				// this type in this week
				if (checkAndCreateForecastGeneral(rs1, forecastWeek, dateNextRun,
						"Forecast for for (weekly) Calendar: " + dateNextRunAsString, false))
					resultMsg.append("<br>" + "----Forecast created for date " + dateNextRunAsString);
				else
					resultMsg.append("<br>" + "----NO Forecast created for date " + dateNextRunAsString);

				// look for further forecasts within periods determined by
				// Frequency and create them
				// set new cycle
				// dateFrom is the actual measure; dateTo is here just for
				// documentation
				datePeriodFrom.setTime(dateNextRun);
				datePeriodFrom.add(Calendar.DATE, daysPerWeek * numberOfWeeks);
				datePeriodTo.setTime(dateNextRun);
				datePeriodTo.add(Calendar.DATE, daysPerWeek * numberOfWeeks);
				datePeriodTo.add(Calendar.DATE, daysPerWeek);

				// iterate periods
				resultMsg.append("<br>" + "----to iterate period (" + "of " + datePeriodFrom.get(Calendar.DAY_OF_MONTH)
						+ "/" + (datePeriodFrom.get(Calendar.MONTH) + 1) + "/" + datePeriodFrom.get(Calendar.YEAR)
						+ " to " + datePeriodEnd.get(Calendar.DAY_OF_MONTH) + "/"
						+ (datePeriodEnd.get(Calendar.MONTH) + 1) + "/" + datePeriodEnd.get(Calendar.YEAR) + ")");
				while (datePeriodFrom.before(datePeriodEnd))
				{
					forecastWeek = datePeriodFrom.get(Calendar.WEEK_OF_YEAR);
					Timestamp dateForcastRun = new Timestamp(datePeriodFrom.getTimeInMillis());
					String dateForcastRunAsString = new SimpleDateFormat("dd/MM/yyyy").format(dateForcastRun);

					// do not create forecast if this forecast already exists
					if (checkAndCreateForecastGeneral(rs1, forecastWeek, dateForcastRun,
							"Forecast for (weekly) Calendar: " + dateForcastRunAsString, false))
						resultMsg.append("<br>" + "------Forecast created for date " + dateForcastRunAsString);
					else
						resultMsg.append("<br>" + "------NO Forecast created for date " + dateForcastRunAsString);

					datePeriodFrom.add(Calendar.DATE, daysPerWeek * numberOfWeeks);
					datePeriodTo.add(Calendar.DATE, daysPerWeek * numberOfWeeks);
				}

				// updating Preventive Maintenance
				// In this case it is only valid for Calendar
				// When a Preventive Maintenance is calculated, then the
				// nextdate and lastdate are updated.
				// For Meter, it is done at Service Order Generation
				try
				{
					this.commitEx(); // all data must be on DB

				}
				catch (Exception e)
				{
					log.log(Level.SEVERE, e.getMessage(), e);
				}

				updateMaintenance(rs1);

				break;
			}
			datePeriodFrom.add(Calendar.DATE, daysPerWeek);
			datePeriodTo.add(Calendar.DATE, daysPerWeek);
		}

		return resultMsg.toString();
	} // forecastByCalendarWeek

	private String forecastByCalendarYear(ResultSet rs1, Calendar datePeriodFrom, Calendar datePeriodEnd, int dayOfYear,
			Timestamp dateNextRun)
	{
		StringBuffer resultMsg = new StringBuffer();
		Calendar nextRun = Calendar.getInstance();
		nextRun.setTimeInMillis(dateNextRun.getTime());
		nextRun.set(Calendar.DAY_OF_YEAR, dayOfYear);

		Calendar today = Calendar.getInstance();
		if (nextRun.before(today))
			nextRun.add(Calendar.YEAR, 1); // add one year, because Maintenance
											// was in the past

		MMaintenance maintenance = new MMaintenance(getCtx(), rs1, get_TrxName());

		while (datePeriodFrom.before(datePeriodEnd) && nextRun.before(datePeriodEnd))
		{
			int forecastWeek = nextRun.get(Calendar.WEEK_OF_YEAR);
			Timestamp dateForcastRun = new Timestamp(nextRun.getTimeInMillis());
			String dateForcastRunAsString = new SimpleDateFormat("dd/MM/yyyy").format(dateForcastRun);

			if (dayOfYear >= nextRun.getMinimum(Calendar.DAY_OF_YEAR)
					&& dayOfYear <= nextRun.getMaximum(Calendar.DAY_OF_YEAR))
			{

				if (checkAndCreateForecastDate(rs1, forecastWeek, dateForcastRun,
						"Forecast for (annual) Calendar: " + dateForcastRunAsString))
					resultMsg.append("<br>" + "----Forecast annual created for date " + dateForcastRunAsString);
				else
					resultMsg.append("<br>" + "----Forecast annual not created for date " + dateForcastRunAsString
							+ " (Forecast already exists for this date)");
			}
			else
			{
				resultMsg.append("<br>" + "----Forecast annual not created for date " + dateForcastRunAsString
						+ ". to of to day of year (" + dayOfYear + ")");
			}

			maintenance.setDateLastRun(new Timestamp(nextRun.getTimeInMillis()));
			Calendar tempDate = (Calendar) nextRun.clone();
			tempDate.add(Calendar.YEAR, 1);
			maintenance.setDateNextRun(new Timestamp(tempDate.getTimeInMillis()));
			maintenance.saveEx();

			nextRun.add(Calendar.YEAR, 1); // add one year
			datePeriodFrom.add(Calendar.YEAR, 1); // add one year
		}
		return resultMsg.toString();
	} // forecastByCalendarYear

	private String forecastByMeter()
	{
		StringBuffer resultMsg = new StringBuffer();

		// SEARCHING BY METER
		resultMsg.append("<br>" + "To calculate Maintenance for Meter: Beginning");
		StringBuffer byMeter = new StringBuffer();
		// all meter measurements 40 days before and after today
		// the first day and the last day of these measurements are selected
		// also selected are the meter gauge at the beginning and at the end
		byMeter.append(
				"SELECT m.AD_CLIENT_ID, m.AD_ORG_ID, m.AM_Maintenance_ID, m.DESCRIPTION, m.AM_ProgrammingType, A.A_Asset_ID, m.AM_Meter_ID, m.Range, m.NextAM, m.DocumentNo, ");
		byMeter.append(
				" (SELECT MIN(log2.DateTrx) FROM AM_AssetMeter_Log log2 WHERE  me.AM_AssetMeter_ID=log2.AM_AssetMeter_ID AND log2.DateTrx BETWEEN (NOW()-40) AND NOW()) AS FirstDay, ");
		byMeter.append(
				" (SELECT MAX(log2.DateTrx) FROM AM_AssetMeter_Log log2 WHERE  me.AM_AssetMeter_ID=log2.AM_AssetMeter_ID AND log2.DateTrx BETWEEN (NOW()-40) AND NOW()) AS LastDay, ");
		byMeter.append(" Count(1)as Qty, me.AM_AssetMeter_ID, ms.Name ");
		byMeter.append("FROM AM_Maintenance m ");
		byMeter.append("INNER JOIN A_Asset A ON (m.A_Asset_ID=A.A_Asset_ID OR A.A_Asset_Group_ID=m.A_Asset_Group_ID) ");
		byMeter.append("INNER JOIN AM_Meter ms ON (m.AM_Meter_ID=ms.AM_Meter_ID) ");
		byMeter.append(
				"INNER JOIN AM_AssetMeter me ON (ms.AM_Meter_ID=me.AM_Meter_ID AND a.A_Asset_id= me.A_Asset_ID) ");
		byMeter.append("INNER JOIN AM_AssetMeter_Log mlog ON (me.AM_AssetMeter_ID=mlog.AM_AssetMeter_ID) ");
		byMeter.append(
				"WHERE AM_ProgrammingType='M' AND m.DOCSTATUS<>'IT' AND m.ISACTIVE='Y' AND mlog.DateTrx BETWEEN (NOW()-40) AND NOW() ");
		byMeter.append(
				"GROUP BY m.AD_CLIENT_ID, m.AD_ORG_ID, m.AM_Maintenance_ID, m.DESCRIPTION, m.AM_ProgrammingType, m.documentno, A.A_Asset_ID, m.AM_Meter_ID, m.RANGE, m.NextAM, me.AM_AssetMeter_ID, ms.Name ");

		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;
		try
		{
			pstmt1 = DB.prepareStatement(byMeter.toString(), get_TrxName());
			rs1 = pstmt1.executeQuery();
			while (rs1.next())
			{
				resultMsg.append("<br>" + "--Beginning of Forecast for \"" + rs1.getString("documentno") + "\"");
				float lastGauge = 0, firstGauge = 0, average = 0;
				// if the amount of measurements (Qty) >1, try to forecast one
				// PM within the period (i.e. number of weeks) selected
				if (rs1.getInt("Qty") > 1)
				{
					log.info("ReadingLastM");
					Object params[] = new Object[] { rs1.getInt("A_Asset_ID"), rs1.getInt("AM_Meter_ID"),
							rs1.getTimestamp("LastDay") };

					log.info("ReadingFirstM:" + rs1.getInt("A_Asset_ID") + "-" + rs1.getInt("AM_Meter_ID") + "-"
							+ rs1.getTimestamp("LastDay"));
					// meter gauge at last measurement day
					lastGauge = DB.getSQLValueBD(get_TrxName(),
							" SELECT mlog.CurrentAmt FROM AM_AssetMeter_Log mlog"
									+ " Inner join AM_AssetMeter met on (mlog.AM_AssetMeter_ID=met.AM_AssetMeter_ID)"
									+ " WHERE met.A_Asset_ID=? AND met.AM_Meter_ID=? AND mlog.DateTrx=? ",
							params).floatValue();

					log.info("ReadingFirstM:" + rs1.getInt("A_Asset_ID") + "-" + rs1.getInt("AM_Meter_ID") + "-"
							+ rs1.getTimestamp("FIRSTDAY"));

					params = new Object[] { rs1.getInt("A_Asset_ID"), rs1.getInt("A_Asset_ID"),
							rs1.getTimestamp("FIRSTDAY") };
					// meter gauge at first measurement day
					firstGauge = DB.getSQLValueBD(get_TrxName(),
							"SELECT mlog.CurrentAmt  FROM AM_AssetMeter_Log mlog"
									+ " Inner join AM_AssetMeter met on (mlog.AM_AssetMeter_ID=met.AM_AssetMeter_ID)"
									+ " WHERE met.A_Asset_ID=? AND met.AM_Meter_ID=? AND mlog.DateTrx=?",
							params).floatValue();

					// calculate days (in milliseconds)
					float days = Math
							.abs(rs1.getTimestamp("LastDay").getTime() - rs1.getTimestamp("FIRSTDAY").getTime());
					// calculate the average consumption/usage during the
					// measured days (not 100% exact)
					// average = gauge difference/ measured days
					average = (lastGauge - firstGauge) / (days / (60 * 60 * 24 * 1000));

					StringBuffer update = new StringBuffer();
					update.append("UPDATE AM_Maintenance ");
					update.append(" SET AverageUse=" + average);
					update.append(" WHERE AM_Maintenance_ID=" + rs1.getInt("AM_Maintenance"));
					DB.executeUpdate(update.toString(), get_TrxName());

					int dayCount = 0;
					boolean forecastFound = false;
					Calendar forecastDate = Calendar.getInstance();
					// we start with the last time a measure was taken
					forecastDate.setTime(rs1.getTimestamp("LastDay"));
					log.info("LoopMedidor");
					float nextMaintenanceGauge = rs1.getFloat("NextAM");
					float range = rs1.getFloat("RANGE");
					do
					{
						// if the last meter gauge lies within the following
						// band:
						// "next maintenance gauge + range >= last meter gauge
						// >= next maintenance gauge - range"
						// or if the last meter gauge > next meter gauge
						// then: add the day count to the forecast and stop the
						// loop
						// otherwise:
						// increment the last meter gauge by the calculated
						// average
						// increment the day count by one and continue in the
						// loop
						// the loop also stops when the day count surpasses the
						// number of forecast days
						// the loops stops thus either by having found a
						// forecast date
						// or when the days surpass the forecast period
						if ((lastGauge >= (nextMaintenanceGauge - range) && lastGauge <= (nextMaintenanceGauge + range))
								|| (lastGauge >= nextMaintenanceGauge))
						{
							forecastFound = true;
							forecastDate.add(Calendar.DATE, dayCount);
						}
						else
						{
							lastGauge = lastGauge + average;
							dayCount++;
						}
					}
					while (!forecastFound && dayCount <= (daysPerWeek * numberOfWeeks));

					Timestamp dateNextRun = new Timestamp(forecastDate.getTimeInMillis());
					String dateNextRunAsString = new SimpleDateFormat("dd/MM/yyyy").format(dateNextRun);
					// if in the last loop a forecast was found within the
					// forecast period,
					// or the last meter gauge (even if incremented in last
					// loop) is higher than the "next"
					// then try to create the forecast
					if (forecastFound || lastGauge >= nextMaintenanceGauge)
					{
						resultMsg.append("<br>" + "----Forecast for possible date " + dateNextRunAsString);
						log.info("Insertmeter");
						boolean extrapolatedForecast = (dayCount > 0 && lastGauge < nextMaintenanceGauge);
						String descriptionText = "Forecast for meter. Value of the meter \"" + rs1.getString("name")
								+ "\" :" + lastGauge;
						// do not create forecast if this forecast already
						// exists
						if (checkAndCreateForecastGeneral(rs1, forecastDate.get(Calendar.WEEK_OF_YEAR), dateNextRun,
								descriptionText, extrapolatedForecast))
							resultMsg.append("<br>" + "----Forecast created for date " + dateNextRunAsString);
						else
							resultMsg.append("<br>" + "----No forecast created for date " + dateNextRunAsString);
					}

				}
				resultMsg.append("<br>" + "--Finish of forecast for \"" + rs1.getString("documentno") + "\"");
			}

			commitEx();
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, e.getMessage(), e);
		}
		finally
		{
			DB.close(rs1, pstmt1);
			rs1 = null;
			pstmt1 = null;
		}

		resultMsg.append("<br>" + "To calculate Maintenance for Meter: Finish");
		resultMsg.append("<br>");
		return resultMsg.toString();
	} // forecastByMeter

	private boolean checkAndCreateForecastGeneral(ResultSet rsMP, int forecastWeek, Timestamp forecastDate,
			String description, boolean extrapolated)
	{
		boolean forecastCreated = false;
		try
		{
			String programmingType = rsMP.getString("AM_ProgrammingType");
			int week = forecastWeek;
			Timestamp date = forecastDate;
			// if extrapolated meter forecast is in the past, move it to today
			if (programmingType.equals("M"))
			{
				Calendar now = Calendar.getInstance();
				Calendar dateForecast = Calendar.getInstance();
				dateForecast.setTime(forecastDate);
				if (dateForecast.before(now))
				{
					if (extrapolated)
					{ // meter, in the past and extrapolated
						date = new Timestamp(now.getTimeInMillis());
						week = now.get(Calendar.WEEK_OF_YEAR);
						description += ". There is no delay. Extrapolated the measurements to generate the forecast";
					}
					else
					{
						description += ". THERE IS DELAY.";
					}
				}
				else if (extrapolated)
					// meter, in the future and extrapolated
					description += ". There is no delay. Extrapolated the measurements to generate the forecast.";
			}

			// do not create forecast if this forecast already exists for this
			// type in this week
			StringBuffer sql = new StringBuffer(
					"SELECT AM_Maintenance_ID, A_Asset_ID, AM_ProgrammingType, Frequency, DateTrx ");
			sql.append("FROM AM_Prognosis ");
			sql.append(" WHERE AM_Maintenance_ID=" + rsMP.getInt("AM_Maintenance_ID"));
			sql.append(" AND A_Asset_ID= " + rsMP.getInt("A_Asset_ID"));
			sql.append(" AND AM_ProgrammingType=" + programmingType);
			sql.append(" AND Frequency= " + week);
			sql.append(" AND IsActive='Y'");
			sql.append(" ORDER BY DateTrx");

			PreparedStatement pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
			ResultSet rs = pstmt.executeQuery();

			if (!rs.next())
			{
				// Create forecast as there in none other for this type in this
				// week
				createForecast(rsMP, week, date, description);
				forecastCreated = true;
			}

			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, e.getMessage(), e);
		}
		return forecastCreated;
	} // checkAndCreateForecastGeneral

	private boolean checkAndCreateForecastDate(ResultSet rsMP, int forecastWeek, Timestamp forecastDate,
			String description)
	{
		boolean forecastCreated = false;
		try
		{
			String programmingType = rsMP.getString("AM_ProgrammingType");
			Timestamp date = forecastDate;
			// do not create forecast if this forecast already exists for this
			// type in this date
			StringBuffer sql = new StringBuffer(
					"SELECT AM_Maintenance_ID, A_Asset_ID, AM_ProgrammingType, Frequency, DateTrx ");
			sql.append(" FROM AM_Prognosis ");
			sql.append(" WHERE AM_Maintenance_ID=" + rsMP.getInt("AM_Maintenance_ID"));
			sql.append(" AND A_Asset_ID=" + rsMP.getInt("A_Asset_ID"));
			sql.append(" AND AM_ProgrammingType='" + programmingType + "'");
			sql.append(" AND DateTrx=to_date('" + forecastDate + "', 'yyyy-MM-dd')");
			sql.append(" AND IsActive='Y'");
			sql.append(" ORDER BY DateTrx");

			PreparedStatement pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
			ResultSet rs = pstmt.executeQuery();
			if (!rs.next())
			{
				// Create forecast as there in none other for this type in this
				// date
				createForecast(rsMP, forecastWeek, date, description);
				forecastCreated = true;
			}
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, e.getMessage(), e);
		}
		return forecastCreated;
	} // checkAndCreateForecastDate

	private void createForecast(ResultSet rsMP, int forecastWeek, Timestamp forecastDate, String description)
	{
		try
		{
			// Create forecast as there in none other for this type in this week
			X_AM_Prognosis maintForecast = new X_AM_Prognosis(getCtx(), 0, get_TrxName());
			maintForecast.setA_Asset_ID(rsMP.getInt("A_ASSET_ID"));
			maintForecast.setAD_Org_ID(rsMP.getInt("AD_ORG_ID"));
			maintForecast.setAD_PInstance_ID(AD_PInstance_ID);
			maintForecast.setFrequency(forecastWeek);
			maintForecast.setDateTrx(forecastDate);
			maintForecast.setDescription(description);
			maintForecast.setAM_Maintenance_ID(rsMP.getInt("AM_Maintenance_ID"));
			maintForecast.setAM_ProgrammingType(rsMP.getString("AM_ProgrammingType"));
			maintForecast.setIsSelected(false);
			maintForecast.setIsActive(true);
			maintForecast.saveEx();
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, e.getMessage(), e);
		}
	} // createForecast

	/**
	 * Update Preventive Maintenance dates (datenextrun and datelastrun) based
	 * on forecast Only valid for Calendar Maintenance
	 * 
	 * @author Mario Calderon
	 * @param rsMaintenance Forecast as ResultSet
	 */
	private void updateMaintenance(ResultSet rsMaintenance)
	{
		try
		{
			// Update datenextrun and datelastrun within Maintenance
			int maintenance_id = rsMaintenance.getInt("AM_Maintenance_ID");
			int asset_id = rsMaintenance.getInt("A_Asset_ID");
			int weeks = rsMaintenance.getInt("interval");
			X_AM_Maintenance prevMant = new X_AM_Maintenance(Env.getCtx(), maintenance_id, this.get_TrxName());

			// 1: Calculate datenextrun: get highest forecast and add interval
			StringBuffer sql1 = new StringBuffer();
			sql1.append("SELECT MAX(AM_Prognosis_ID) AS forecast");
			sql1.append(" FROM AM_Prognosis ");
			sql1.append(" WHERE AM_Maintenance_ID=" + maintenance_id);
			sql1.append(" AND A_Asset_ID=" + asset_id);
			sql1.append(" AND IsActive='Y'");

			PreparedStatement pstmt1 = DB.prepareStatement(sql1.toString(), get_TrxName());
			ResultSet rs1 = pstmt1.executeQuery();
			if (rs1.next())
			{
				// Calculate next run for Calendar Maintenance: it is one period
				// longer than last forecast
				int forecast_id = rs1.getInt("forecast");
				X_AM_Prognosis forecast = new X_AM_Prognosis(getCtx(), forecast_id, get_TrxName());
				Calendar dateForecast = Calendar.getInstance();
				dateForecast.setTime(forecast.getDateTrx());
				dateForecast.add(Calendar.DATE, daysPerWeek * weeks);
				Timestamp datenextrun = new Timestamp(dateForecast.getTimeInMillis());
				prevMant.setDateNextRun(datenextrun);
				prevMant.saveEx();
			}
			DB.close(rs1, pstmt1);
			rs1 = null;
			pstmt1 = null;

			// 2: Calculate datelastrun: get highest forecast before now
			StringBuffer sql2 = new StringBuffer();
			sql2.append("SELECT MAX(AM_Prognosis_ID) as forecast");
			sql2.append(" FROM AM_Prognosis ");
			sql2.append(" WHERE AM_Maintenance_ID=" + maintenance_id);
			sql2.append(" AND A_Asset_ID=" + asset_id);
			sql2.append(" AND DateTrx < now()");
			sql2.append(" AND IsActive='Y'");

			PreparedStatement pstmt2 = DB.prepareStatement(sql2.toString(), get_TrxName());
			ResultSet rs2 = pstmt2.executeQuery();
			if (rs2.next())
			{
				// set datelastrun for Calendar Maintenance: it is the date of
				// the forecast, if any
				int forecast_id = rs2.getInt("forecast");
				if (forecast_id != 0)
				{
					X_AM_Prognosis forecast = new X_AM_Prognosis(getCtx(), forecast_id, get_TrxName());
					Calendar dateForecast = Calendar.getInstance();
					dateForecast.setTime(forecast.getDateTrx());
					Timestamp datelastrun = new Timestamp(dateForecast.getTimeInMillis());
					prevMant.setDateLastRun(datelastrun);
					prevMant.saveEx();
				}
			}
			DB.close(rs2, pstmt2);
			rs2 = null;
			pstmt2 = null;
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, e.getMessage(), e);
		}
	} // updateMP

}
