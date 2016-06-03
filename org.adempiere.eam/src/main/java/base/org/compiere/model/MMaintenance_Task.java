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
import java.util.List;
import java.util.Properties;

/**
 * Maintenance Task
 * @author OFB Consulting http://www.ofbconsulting.com
 * 	<li> Initial Contributor
 * @contributor Mario Calderon, Systemhaus Westfalia, http://www.westfalia-it.com
 * @contributor Adaxa http://www.adaxa.com
 * @contributor Deepak Pansheriya, Loglite Technologies, http://logilite.com
 * @contributor Victor Perez, victor.perez@e-evolution.com, eEvolution http://www.e-evolution.com
 * @contributor Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 * @contributor Sachin Bhimani
 */
public class MMaintenance_Task extends X_AM_Maintenance_Task
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 44291014658013915L;

	public MMaintenance_Task(Properties ctx, int AM_Maintenance_Task_ID, String trxName)
	{
		super(ctx, AM_Maintenance_Task_ID, trxName);
	}

	public MMaintenance_Task(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	/**
	 * get the tasks for a job
	 * 
	 * @param Maintenance
	 * @return List with tasks
	 */
	public static X_AM_Maintenance_Task[] getMaintenanceTasks(MMaintenance maintenance)
	{
		String whereClause = MMaintenance.COLUMNNAME_AM_Maintenance_ID + "=? ";
		List<X_AM_Maintenance_Task> list = new Query(maintenance.getCtx(), X_AM_Maintenance_Task.Table_Name,
				whereClause, maintenance.get_TrxName()).setClient_ID().setParameters(maintenance.getAM_Maintenance_ID())
						.setOnlyActiveRecords(true).list();
		X_AM_Maintenance_Task[] tasks = new X_AM_Maintenance_Task[list.size()];
		list.toArray(tasks);
		return tasks;
	} // getMaintenanceTasks

	/**
	 * get the costs of a task
	 * 
	 * @return Costs
	 */
	public BigDecimal getMaintenanceTaskCosts()
	{
		X_AM_Maintenance_Resource[] resources = MMaintenance_Resource.getMaintenanceResources(this);
		BigDecimal taskCosts = BigDecimal.ZERO;
		for (X_AM_Maintenance_Resource resource : resources)
		{
			taskCosts = taskCosts.add(resource.getCostAmt());
		}
		return taskCosts;
	} // getMaintenanceTaskCosts

	/**
	 * update the costs of a task
	 */
	public void updateMaintenanceTaskCosts()
	{
		this.setCostAmt(getMaintenanceTaskCosts());
		this.saveEx();
	} // updateMaintainTaskCosts

}
