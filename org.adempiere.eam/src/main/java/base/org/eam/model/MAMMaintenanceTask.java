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
public class MAMMaintenanceTask extends X_AM_MaintenanceTask
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 44291014658013915L;

	public MAMMaintenanceTask(Properties ctx, int AM_Maintenance_Task_ID, String trxName) {
		super(ctx, AM_Maintenance_Task_ID, trxName);
	}

	public MAMMaintenanceTask(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/**
	 * update the costs of a task
	 */
	public void updateMaintenanceTaskCosts()
	{
//		this.setCostAmt(getMaintenanceTaskCosts());
//		this.saveEx();
	} // updateMaintainTaskCosts
	
	/**
	 * Set Pattern Task
	 * @param patternTask
	 */
	public void setPatternTask(MAMPatternTask patternTask) {
		setC_UOM_ID(patternTask.getC_UOM_ID());
		setDuration(patternTask.getDuration());
		setCostAmt(patternTask.getCostAmt());
		setName(patternTask.getName());
		setLine(patternTask.getLine());
		setDescription(patternTask.getDescription());
	}
	
	/**
	 * get the Resources for a given maintenace task
	 * 
	 * @param Task
	 * @return List with Resources
	 */
	public List<MAMMaintenanceResource> getResources() {
		String whereClause = COLUMNNAME_AM_MaintenanceTask_ID + "=?";
		List<MAMMaintenanceResource> list = new Query(getCtx(), I_AM_MaintenanceResource.Table_Name,
				whereClause, get_TrxName())
						.setClient_ID()
						.setParameters(getAM_MaintenanceTask_ID())
						.setOnlyActiveRecords(true)
						.list();

		return list;
	} // getJSTaskResources
	
	@Override
	public String toString() {
		return "MAMMaintenanceTask [getAM_MaintenanceTask_ID()=" + getAM_MaintenanceTask_ID() + ", getName()="
				+ getName() + "]";
	}
}
