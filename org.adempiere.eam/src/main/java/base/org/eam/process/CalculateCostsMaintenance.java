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
package org.eam.process;

import org.compiere.process.SvrProcess;
import org.eam.model.MAMMaintenance;
import org.eam.model.MAMMaintenanceTask;

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
public class CalculateCostsMaintenance extends SvrProcess
{

	private int maintain_ID;

	@Override
	protected void prepare()
	{
		maintain_ID = getRecord_ID();
	}

	@Override
	protected String doIt() throws Exception {
		MAMMaintenance maintain = new MAMMaintenance(getCtx(), maintain_ID, get_TrxName());
		for (MAMMaintenanceTask task : maintain.getTasks()) {
			task.updateMaintenanceTaskCosts();
		}
		// it must be after updating the tasks, because there the task costs
		// are calculated
		maintain.updateMaintenanceCosts();
		return "OK";
	}

}
