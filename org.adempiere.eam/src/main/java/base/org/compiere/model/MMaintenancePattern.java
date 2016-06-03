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
import java.util.Properties;

/**
 * Maintenance Pattern
 * @author OFB Consulting http://www.ofbconsulting.com
 * 	<li> Initial Contributor
 * @contributor Mario Calderon, Systemhaus Westfalia, http://www.westfalia-it.com
 * @contributor Adaxa http://www.adaxa.com
 * @contributor Deepak Pansheriya, Loglite Technologies, http://logilite.com
 * @contributor Victor Perez, victor.perez@e-evolution.com, eEvolution http://www.e-evolution.com
 * @contributor Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 * @contributor Sachin Bhimani
 */
public class MMaintenancePattern extends X_AM_MaintenancePattern
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 920285726461219946L;

	public MMaintenancePattern(Properties ctx, int AM_MaintenancePattern_ID, String trxName)
	{
		super(ctx, AM_MaintenancePattern_ID, trxName);
	}

	public MMaintenancePattern(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	/**
	 * get the costs of a Maintenance Pattern
	 * 
	 * @return Costs
	 */
	public BigDecimal getMPCosts()
	{
		X_AM_MaintenancePattern_Task[] tasks = MMaintenancePattern_Task.getMPTasks(this);
		BigDecimal mpCosts = BigDecimal.ZERO;
		for (X_AM_MaintenancePattern_Task task : tasks)
		{
			mpCosts = mpCosts.add(task.getCostAmt());
		}
		return mpCosts;
	} // getMPCosts

	/**
	 * update the costs of a Maintenance Pattern
	 */
	public void updateMPCosts()
	{
		this.setCostAmt(getMPCosts());
		this.saveEx();
	} // updateMPCosts
}
