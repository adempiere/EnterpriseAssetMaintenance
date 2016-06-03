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

import java.util.logging.Level;

import org.compiere.model.MMaintenance;
import org.compiere.model.MMaintenance_Task;
import org.compiere.model.X_AM_Maintenance_Task;

/**
 * Calculate Costs of Maintenance from tasks
 * @author OFB Consulting http://www.ofbconsulting.com
 * 	<li> Initial Contributor
 * @contributor Mario Calderon, Systemhaus Westfalia, http://www.westfalia-it.com
 * @contributor Adaxa http://www.adaxa.com
 * @contributor Deepak Pansheriya, Loglite Technologies, http://logilite.com
 * @contributor Victor Perez, victor.perez@e-evolution.com, eEvolution http://www.e-evolution.com
 * @contributor Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 * @contributor Sachin Bhimani
 */
public class AMCalculateCostsMaintenance extends SvrProcess
{

	private int maintain_ID;

	@Override
	protected void prepare()
	{
		maintain_ID = getRecord_ID();
	}

	@Override
	protected String doIt() throws Exception
	{
		try
		{
			MMaintenance maintain = new MMaintenance(getCtx(), maintain_ID, get_TrxName());
			X_AM_Maintenance_Task[] tasks = MMaintenance_Task.getMaintenanceTasks(maintain);
			for (X_AM_Maintenance_Task task : tasks)
			{
				MMaintenance_Task task2 = new MMaintenance_Task(getCtx(), task.getAM_Maintenance_Task_ID(),
						get_TrxName());
				task2.updateMaintenanceTaskCosts();
			}
			// it must be after updating the tasks, because there the task costs
			// are calculated
			maintain.updateMaintenanceCosts();
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "Failed to calculating and updating costs of Maintenance and Tasks", e);
		}

		return "Costs calculated and updated.";
	}

}
