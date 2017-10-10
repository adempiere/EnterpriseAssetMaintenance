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
package org.eam.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * Service Order (Work Order) Task
 * @author OFB Consulting http://www.ofbconsulting.com
 * 	<li> Initial Contributor
 * @contributor Mario Calderon, Systemhaus Westfalia, http://www.westfalia-it.com
 * @contributor Adaxa http://www.adaxa.com
 * @contributor Deepak Pansheriya, Loglite Technologies, http://logilite.com
 * @contributor Victor Perez, victor.perez@e-evolution.com, eEvolution http://www.e-evolution.com
 * @contributor Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 * @contributor Sachin Bhimani
 */
public class MAMServiceOrderTask extends X_AM_ServiceOrderTask
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8307092659032491805L;

	public MAMServiceOrderTask(Properties ctx, int AM_ServiceOrder_Task_ID, String trxName)
	{
		super(ctx, AM_ServiceOrder_Task_ID, trxName);
	}

	public MAMServiceOrderTask(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}
	
	/**
	 * get the costs of a task
	 * 
	 * @return Costs
	 */
	public BigDecimal getCosts() {
		BigDecimal taskCosts = BigDecimal.ZERO;
		for (MAMServiceOrderResource resource : getResources()) {
			taskCosts = taskCosts.add(resource.getCostAmt());
		}
		return taskCosts;
	} // getSOTaskCosts

	/**
	 * update the costs of a task
	 */
	public void updateCosts() {
		setCostAmt(getCosts());
		saveEx();
	} // updateSOTaskCosts
	
	/**
	 * get the Resources for a given service order resources
	 * 
	 * @param Task
	 * @return List with Resources
	 */
	public List<MAMServiceOrderResource> getResources() {
		String whereClause = COLUMNNAME_AM_ServiceOrderTask_ID + "=? ";
		
		List<MAMServiceOrderResource> list = new Query(getCtx(), I_AM_ServiceOrderResource.Table_Name,
				whereClause, get_TrxName())
						.setClient_ID()
						.setParameters(getAM_ServiceOrderTask_ID())
						.setOnlyActiveRecords(true)
						.list();

		return list;
	} // getJSTaskResources
	
	/**
	 * Set task from Pattern Task
	 * @param task
	 */
	public void setPatternTask(MAMPatternTask task) {
		setC_UOM_ID(task.getC_UOM_ID());
		setDuration(task.getDuration());
		setCostAmt(task.getCostAmt());
		setName(task.getName());
		setLine(task.getLine());
		setDescription(task.getDescription());
	}

	/**
	 * Set task from Maintenance Task
	 * @param task
	 */
	public void setMaintenanceTask(MAMMaintenanceTask task) {
		setC_UOM_ID(task.getC_UOM_ID());
		setDuration(task.getDuration());
		setCostAmt(task.getCostAmt());
		setName(task.getName());
		setLine(task.getLine());
		setDescription(task.getDescription());
		setStatus(STATUS_NotStarted);
	}
	
	@Override
	public String toString() {
		return "MAMServiceOrderTask [getAM_ServiceOrderTask_ID()=" + getAM_ServiceOrderTask_ID() + ", getName()="
				+ getName() + "]";
	}
}
