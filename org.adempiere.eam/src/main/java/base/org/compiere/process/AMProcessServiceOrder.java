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
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.model.X_AM_Maintenance;
import org.compiere.model.X_AM_ServiceOrder;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * Service Order Process
 * @author OFB Consulting http://www.ofbconsulting.com
 * 	<li> Initial Contributor
 * @contributor Mario Calderon, Systemhaus Westfalia, http://www.westfalia-it.com
 * @contributor Adaxa http://www.adaxa.com
 * @contributor Deepak Pansheriya, Loglite Technologies, http://logilite.com
 * @contributor Victor Perez, victor.perez@e-evolution.com, eEvolution http://www.e-evolution.com
 * @contributor Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 * @contributor Sachin Bhimani
 */
public class AMProcessServiceOrder extends SvrProcess
{

	private String	p_DocAction;
	private int		recordID;

	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("DocAction"))
				p_DocAction = (String) para[i].getParameter();
			else
				log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
		}

		recordID = getRecord_ID();
	}

	@Override
	protected String doIt() throws Exception
	{
		String sql = "SELECT COUNT(1) FROM AM_ServiceOrder_Task WHERE STATUS!='CO' AND AM_ServiceOrder_ID=" + recordID;
		X_AM_ServiceOrder serviceOrder = new X_AM_ServiceOrder(Env.getCtx(), recordID, get_TrxName());

		if (DB.getSQLValue(get_TrxName(), sql) > 0 && serviceOrder.getDocStatus().equals("DR"))
			return "Task Not Completed";

		sql = "SELECT DISTINCT AM_Maintenance_ID FROM AM_ServiceOrder_Task WHERE AM_ServiceOrder_ID=?";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, recordID);
			rs = pstmt.executeQuery();

			while (rs.next())
			{
				X_AM_Maintenance maintenance = new X_AM_Maintenance(Env.getCtx(), rs.getInt(1), get_TrxName());

				if (serviceOrder.getDocStatus().equals("CO") && p_DocAction.equals("VO"))
				{
					if (maintenance.getAM_ProgrammingType().equals("C"))
					{
						maintenance.setDateNextRun(new Timestamp(maintenance.getDateLastRun().getTime()
								- (maintenance.getInterval().longValue() * 86400000)));
					}
					else
					{
						maintenance.setNextAM(maintenance.getNextAM().subtract(maintenance.getInterval()));
						maintenance.setLastAM(maintenance.getNextAM().subtract(maintenance.getInterval()));
					}
				}

				if (serviceOrder.getDocStatus().equals("DR"))
					maintenance.setDateLastSO(serviceOrder.getDateTrx());

				maintenance.save();
			}
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

		if (serviceOrder.getDocStatus().equals("DR") && p_DocAction.equals("CO"))
		{
			DB.executeUpdate("UPDATE AM_ServiceOrder_Resource SET Processed='Y' "
					+ " WHERE AM_ServiceOrder_Task_ID IN (SELECT AM_ServiceOrder_Task_ID FROM AM_ServiceOrder_Task WHERE AM_ServiceOrder_ID="
					+ serviceOrder.getAM_ServiceOrder_ID() + ")", get_TrxName());

			DB.executeUpdate("UPDATE AM_ServiceOrder_Task SET Processed='Y' WHERE AM_ServiceOrder_ID="
					+ serviceOrder.getAM_ServiceOrder_ID(), get_TrxName());

			serviceOrder.setDocStatus("CO");
			serviceOrder.setDocAction("--");
			serviceOrder.setProcessed(true);
			serviceOrder.save();

			return "Service Order is Completed";
		}
		else if (serviceOrder.getDocStatus().equals("CO") && p_DocAction.equals("VO"))
		{
			serviceOrder.setDocStatus("VO");
			serviceOrder.setDocAction("--");
			serviceOrder.setProcessed(true);
			serviceOrder.save();

			return "Service Order is Voided";
		}

		return "";
	}
}
