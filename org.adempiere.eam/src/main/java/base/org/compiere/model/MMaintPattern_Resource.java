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
 * Maintenance: Maintenance Pattern Resource
 * @author OFB Consulting http://www.ofbconsulting.com
 * 	<li> Initial Contributor
 * @contributor Mario Calderon, Systemhaus Westfalia, http://www.westfalia-it.com
 * @contributor Adaxa http://www.adaxa.com
 * @contributor Deepak Pansheriya, Loglite Technologies, http://logilite.com
 * @contributor Victor Perez, victor.perez@e-evolution.com, eEvolution http://www.e-evolution.com
 * @contributor Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 * @contributor Sachin Bhimani
 */
public class MMaintPattern_Resource extends X_AM_MaintPattern_Resource
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3920069528186958423L;

	public MMaintPattern_Resource(Properties ctx, int AM_MaintPattern_Resource_ID, String trxName)
	{
		super(ctx, AM_MaintPattern_Resource_ID, trxName);
	}

	public MMaintPattern_Resource(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	/**
	 * get the Resources for a given maintenace pattern task
	 * 
	 * @param Task
	 * @return List with Resources
	 */
	public static X_AM_MaintPattern_Resource[] getMPTaskResources(MMaintenancePattern_Task task)
	{
		String whereClause = MMaintPattern_Resource.COLUMNNAME_AM_MaintenancePattern_Task_ID + "=? ";

		List<X_AM_MaintPattern_Resource> list = new Query(task.getCtx(), X_AM_MaintPattern_Resource.Table_Name,
				whereClause, task.get_TrxName()).setClient_ID().setParameters(task.getAM_MaintenancePattern_Task_ID())
						.setOnlyActiveRecords(true).list();

		X_AM_MaintPattern_Resource[] resources = new X_AM_MaintPattern_Resource[list.size()];
		list.toArray(resources);
		return resources;
	} // getJSTaskResources
}
