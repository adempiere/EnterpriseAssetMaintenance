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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import org.compiere.model.X_AM_Maintenance;
import org.compiere.model.X_AM_MaintenancePattern;
import org.compiere.model.X_AM_Maintenance_Resource;
import org.compiere.model.X_AM_Maintenance_Task;
import org.compiere.util.DB;

/**
 * Copy from Maintenance Pattern
 * @author OFB Consulting http://www.ofbconsulting.com
 * 	<li> Initial Contributor
 * @contributor Mario Calderon, Systemhaus Westfalia, http://www.westfalia-it.com
 * @contributor Adaxa http://www.adaxa.com
 * @contributor Deepak Pansheriya, Loglite Technologies, http://logilite.com
 * @contributor Victor Perez, victor.perez@e-evolution.com, eEvolution http://www.e-evolution.com
 * @contributor Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 * @contributor Sachin Bhimani
 */
public class AMCopyFromMP extends SvrProcess
{
	private int	mpID;
	private int	recordID;

	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("MP_JobStandar_ID"))
				mpID = para[i].getParameterAsInt();
			else
				log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
		}

		recordID = getRecord_ID();
	} // prepare

	protected String doIt() throws Exception
	{
		String sql = "SELECT * FROM AM_MaintenancePattern_Task WHERE AM_MaintenancePattern_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, mpID);
			rs = pstmt.executeQuery();

			while (rs.next())
			{
				X_AM_Maintenance_Task task = new X_AM_Maintenance_Task(getCtx(), 0, get_TrxName());
				task.setAM_Maintenance_ID(recordID);
				task.setDescription(rs.getString("Description"));
				task.setC_UOM_ID(rs.getInt("C_UOM_ID"));
				task.setDuration(rs.getInt("Duration"));
				task.setCostAmt(rs.getBigDecimal("CostAmt"));
				task.setName(rs.getString("Name"));
				task.setLine(rs.getInt("Line"));
				task.saveEx();

				PreparedStatement pstmt2 = null;
				ResultSet rs2 = null;
				pstmt2 = DB.prepareStatement(
						"SELECT * FROM AM_MaintPattern_Resource WHERE AM_MaintenancePattern_Task_ID=?", get_TrxName());
				pstmt2.setInt(1, rs.getInt("AM_MaintenancePattern_Task_ID"));
				rs2 = pstmt2.executeQuery();

				while (rs2.next())
				{
					X_AM_Maintenance_Resource re = new X_AM_Maintenance_Resource(getCtx(), 0, get_TrxName());
					re.setAM_Maintenance_Task_ID(task.getAM_Maintenance_Task_ID());
					re.setCostAmt(rs2.getBigDecimal("CostAmt"));
					re.setM_BOM_ID(rs2.getInt("M_BOM_ID"));
					re.setM_Product_ID(rs2.getInt("M_Product_ID"));
					re.setResourceQty(rs2.getBigDecimal("ResourceQty"));
					re.setResourceType(rs2.getString("ResourceType"));
					re.setS_Resource_ID(rs2.getInt("S_Resource_ID"));
					re.setC_UOM_ID(rs2.getInt("C_UOM_ID")); // SHW
					re.saveEx();
				}

				DB.close(rs2, pstmt2);
				rs2 = null;
				pstmt2 = null;
			}

			X_AM_Maintenance ma = new X_AM_Maintenance(getCtx(), recordID, get_TrxName());
			ma.setAM_MaintenanceParent_ID(mpID);
			// copy Active and Active Group from Maintenance Pattern
			X_AM_MaintenancePattern stdJob = new X_AM_MaintenancePattern(getCtx(), mpID, get_TrxName());
			ma.setA_Asset_ID(stdJob.getA_Asset_ID());
			ma.setA_Asset_Group_ID(stdJob.getA_Asset_Group_ID());
			// copy Maintenance Area and Costs from Maintenance Pattern
			ma.setCostAmt(stdJob.getCostAmt());
			ma.setMaintenanceArea(stdJob.getMaintenanceArea());
			ma.saveEx();
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return "Copied";
	} // doIt
}
