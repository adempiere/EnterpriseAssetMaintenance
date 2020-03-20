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

package org.eam.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.process.SvrProcess;

/** Generated Process for (Create Schedule for Preventive Maintenance)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.3
 */
public abstract class CreateScheduleForMaintenanceAbstract extends SvrProcess {
	/** Process Value 	*/
	private static final String VALUE_FOR_PROCESS = "CreateScheduleForMaintenance";
	/** Process Name 	*/
	private static final String NAME_FOR_PROCESS = "Create Schedule for Preventive Maintenance";
	/** Process Id 	*/
	private static final int ID_FOR_PROCESS = 54370;
	/**	Parameter Name for Start Date	*/
	public static final String STARTDATE = "StartDate";
	/**	Parameter Name for Time Unit	*/
	public static final String TIMEUNIT = "TimeUnit";
	/**	Parameter Name for Interval	*/
	public static final String INTERVAL = "Interval";
	/**	Parameter Name for Maintenance Type	*/
	public static final String MAINTENANCETYPE = "MaintenanceType";
	/**	Parameter Value for Start Date	*/
	private Timestamp startDate;
	/**	Parameter Value for Time Unit	*/
	private String timeUnit;
	/**	Parameter Value for Interval	*/
	private BigDecimal interval;
	/**	Parameter Value for Maintenance Type	*/
	private String maintenanceType;

	@Override
	protected void prepare() {
		startDate = getParameterAsTimestamp(STARTDATE);
		timeUnit = getParameterAsString(TIMEUNIT);
		interval = getParameterAsBigDecimal(INTERVAL);
		maintenanceType = getParameterAsString(MAINTENANCETYPE);
	}

	/**	 Getter Parameter Value for Start Date	*/
	protected Timestamp getStartDate() {
		return startDate;
	}

	/**	 Setter Parameter Value for Start Date	*/
	protected void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	/**	 Getter Parameter Value for Time Unit	*/
	protected String getTimeUnit() {
		return timeUnit;
	}

	/**	 Setter Parameter Value for Time Unit	*/
	protected void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}

	/**	 Getter Parameter Value for Interval	*/
	protected BigDecimal getInterval() {
		return interval;
	}

	/**	 Setter Parameter Value for Interval	*/
	protected void setInterval(BigDecimal interval) {
		this.interval = interval;
	}

	/**	 Getter Parameter Value for Maintenance Type	*/
	protected String getMaintenanceType() {
		return maintenanceType;
	}

	/**	 Setter Parameter Value for Maintenance Type	*/
	protected void setMaintenanceType(String maintenanceType) {
		this.maintenanceType = maintenanceType;
	}

	/**	 Getter Parameter Value for Process ID	*/
	public static final int getProcessId() {
		return ID_FOR_PROCESS;
	}

	/**	 Getter Parameter Value for Process Value	*/
	public static final String getProcessValue() {
		return VALUE_FOR_PROCESS;
	}

	/**	 Getter Parameter Value for Process Name	*/
	public static final String getProcessName() {
		return NAME_FOR_PROCESS;
	}
}