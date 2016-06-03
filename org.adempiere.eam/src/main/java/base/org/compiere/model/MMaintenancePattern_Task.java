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
 * Maintenance Pattern Task
 * @author OFB Consulting http://www.ofbconsulting.com
 * 	<li> Initial Contributor
 * @contributor Mario Calderon, Systemhaus Westfalia, http://www.westfalia-it.com
 * @contributor Adaxa http://www.adaxa.com
 * @contributor Deepak Pansheriya, Loglite Technologies, http://logilite.com
 * @contributor Victor Perez, victor.perez@e-evolution.com, eEvolution http://www.e-evolution.com
 * @contributor Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 * @contributor Sachin Bhimani
 */
public class MMaintenancePattern_Task extends X_AM_MaintenancePattern_Task
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4608840149126763533L;

	public MMaintenancePattern_Task(Properties ctx, int AM_Maintenance_Task_ID, String trxName)
	{
		super(ctx, AM_Maintenance_Task_ID, trxName);
	}

	public MMaintenancePattern_Task(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	/**
	 * get the tasks for a maintenance pattern
	 * 
	 * @param Maintenance pattern
	 * @return List with tasks
	 */
	public static X_AM_MaintenancePattern_Task[] getMPTasks(MMaintenancePattern maintPattern)
	{
		String whereClause = MMaintenancePattern.COLUMNNAME_AM_MaintenancePattern_ID + "=? ";

		List<X_AM_MaintenancePattern_Task> list = new Query(maintPattern.getCtx(),
				X_AM_MaintenancePattern_Task.Table_Name, whereClause, maintPattern.get_TrxName()).setClient_ID()
						.setParameters(maintPattern.getAM_MaintenancePattern_ID()).setOnlyActiveRecords(true).list();

		X_AM_MaintenancePattern_Task[] tasks = new X_AM_MaintenancePattern_Task[list.size()];
		list.toArray(tasks);
		return tasks;
	} // getJobTasks

	/**
	 * get the costs of a task
	 * 
	 * @return Costs
	 */
	public BigDecimal getMPTaskCosts()
	{
		X_AM_MaintPattern_Resource[] resources = MMaintPattern_Resource.getMPTaskResources(this);
		BigDecimal taskCosts = BigDecimal.ZERO;
		for (X_AM_MaintPattern_Resource resource : resources)
		{
			taskCosts = taskCosts.add(resource.getCostAmt());
		}
		return taskCosts;
	} // getJobTaskCosts

	/**
	 * update the costs of a task
	 */
	public void updateMPTaskCosts()
	{
		this.setCostAmt(getMPTaskCosts());
		this.saveEx();
	} // updateJobTaskCosts
}
