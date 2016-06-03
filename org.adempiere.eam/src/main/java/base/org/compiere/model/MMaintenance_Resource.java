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

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

/**
 * Maintenance Resource
 * @author OFB Consulting http://www.ofbconsulting.com
 * 	<li> Initial Contributor
 * @contributor Mario Calderon, Systemhaus Westfalia, http://www.westfalia-it.com
 * @contributor Adaxa http://www.adaxa.com
 * @contributor Deepak Pansheriya, Loglite Technologies, http://logilite.com
 * @contributor Victor Perez, victor.perez@e-evolution.com, eEvolution http://www.e-evolution.com
 * @contributor Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 * @contributor Sachin Bhimani
 */
public class MMaintenance_Resource extends X_AM_Maintenance_Resource
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1978527058177467479L;

	public MMaintenance_Resource(Properties ctx, int AM_Maintenance_Resource_ID, String trxName)
	{
		super(ctx, AM_Maintenance_Resource_ID, trxName);
	}

	public MMaintenance_Resource(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	/**
	 * get the Resources for a given Task
	 * 
	 * @param Task
	 * @return List with Resources
	 */
	public static X_AM_Maintenance_Resource[] getMaintenanceResources(MMaintenance_Task task)
	{
		String whereClause = MMaintenance_Task.COLUMNNAME_AM_Maintenance_Task_ID + "=? ";
		List<X_AM_Maintenance_Resource> list = new Query(task.getCtx(), X_AM_Maintenance_Resource.Table_Name,
				whereClause, task.get_TrxName()).setClient_ID().setParameters(task.getAM_Maintenance_Task_ID())
						.setOnlyActiveRecords(true).list();
		X_AM_Maintenance_Resource[] resources = new X_AM_Maintenance_Resource[list.size()];
		list.toArray(resources);
		return resources;
	} // getMaintenanceResources

}
