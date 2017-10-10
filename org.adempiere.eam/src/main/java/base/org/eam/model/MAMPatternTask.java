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
public class MAMPatternTask extends X_AM_PatternTask {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4608840149126763533L;

	public MAMPatternTask(Properties ctx, int AM_Maintenance_Task_ID, String trxName) {
		super(ctx, AM_Maintenance_Task_ID, trxName);
	}

	public MAMPatternTask(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/**
	 * get the costs of a task
	 * 
	 * @return Costs
	 */
	public BigDecimal getTaskCosts() {
		BigDecimal taskCosts = BigDecimal.ZERO;
		for (MAMPatternResource resource : getResources()) {
			taskCosts = taskCosts.add(resource.getCostAmt());
		}
		return taskCosts;
	} // getJobTaskCosts
	
	/**
	 * get the Resources for a given maintenace pattern task
	 * 
	 * @param Task
	 * @return List with Resources
	 */
	public List<MAMPatternResource> getResources() {
		String whereClause = COLUMNNAME_AM_PatternTask_ID + "=? ";

		List<MAMPatternResource> list = new Query(getCtx(), I_AM_PatternResource.Table_Name,
				whereClause, get_TrxName())
						.setClient_ID()
						.setParameters(getAM_PatternTask_ID())
						.setOnlyActiveRecords(true)
						.list();

		return list;
	} // getJSTaskResources

	/**
	 * update the costs of a task
	 */
	public void updateTaskCosts() {
		this.setCostAmt(getTaskCosts());
		this.saveEx();
	} // updateJobTaskCosts
	
	@Override
	public String toString() {
		return "MAMPatternTask [getAM_PatternTask_ID()=" + getAM_PatternTask_ID() + ", getName()=" + getName() + "]";
	}
}
