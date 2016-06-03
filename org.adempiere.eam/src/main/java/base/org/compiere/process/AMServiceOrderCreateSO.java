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

import java.math.BigDecimal;
import java.util.logging.Level;

import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MOrg;
import org.compiere.model.MServiceOrder;
import org.compiere.model.MServiceOrder_Resource;
import org.compiere.model.MServiceOrder_Task;
import org.compiere.model.MUOMConversion;
import org.compiere.model.Query;
import org.compiere.model.X_AM_ServiceOrder;
import org.compiere.model.X_AM_ServiceOrder_Resource;
import org.compiere.model.X_AM_ServiceOrder_Task;
import org.compiere.util.Env;

/**
 * Service Order to create Sales Order
 * @author OFB Consulting http://www.ofbconsulting.com
 * 	<li> Initial Contributor
 * @contributor Mario Calderon, Systemhaus Westfalia, http://www.westfalia-it.com
 * @contributor Adaxa http://www.adaxa.com
 * @contributor Deepak Pansheriya, Loglite Technologies, http://logilite.com
 * @contributor Victor Perez, victor.perez@e-evolution.com, eEvolution http://www.e-evolution.com
 * @contributor Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 * @contributor Sachin Bhimani
 */
public class AMServiceOrderCreateSO extends SvrProcess
{
	private int				p_doctype_ID;
	private int				p_warehouse_ID;
	private int				recordID;

	private MOrder			salesorder	= null;
	private MServiceOrder	workorder	= null;

	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("C_DocType_ID"))
				p_doctype_ID = para[i].getParameterAsInt();
			else if (name.equals("M_Warehouse_ID"))
				p_warehouse_ID = para[i].getParameterAsInt();
			else
				log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
		}

		recordID = getRecord_ID();
		workorder = new MServiceOrder(getCtx(), recordID, get_TrxName());
	} // prepare

	@Override
	protected String doIt() throws Exception
	{
		String whereClause = MServiceOrder.COLUMNNAME_AM_ServiceOrder_ID + "=? ";
		int[] list = new Query(getCtx(), X_AM_ServiceOrder_Task.Table_Name, whereClause, get_TrxName()).setClient_ID()
				.setParameters(recordID).setOnlyActiveRecords(true).getIDs();

		for (final int task_id : list)
		{
			MServiceOrder_Task soTask = new MServiceOrder_Task(getCtx(), task_id, get_TrxName());
			X_AM_ServiceOrder_Resource[] resources = MServiceOrder_Resource.getSOTaskResources(soTask);
			for (X_AM_ServiceOrder_Resource resource : resources)
			{
				if (!resource.getResourceType().equals("CO"))
					continue;
				if (salesorder == null)
				{
					createSalesOrder();
				}
				if (resource.getResourceQty().subtract(resource.getQtyReserved()).equals(Env.ZERO))
					continue;
				createSalesOrderLine(resource);
			}
		}

		if (salesorder.processIt(MServiceOrder.DOCACTION_Complete))
		{
			salesorder.setDocStatus(MServiceOrder.DOCSTATUS_Completed);
			salesorder.setDocAction(MServiceOrder.DOCACTION_Close);
			salesorder.setProcessed(true);
			salesorder.saveEx();

			return "Requirement of material generated " + salesorder.getDocumentNo();
		}

		return "";
	} // doIt

	private void createSalesOrder()
	{
		salesorder = new MOrder(getCtx(), 0, get_TrxName());
		salesorder.setClientOrg(workorder.getAD_Client_ID(), workorder.getAD_Org_ID());
		salesorder.setIsSOTrx(true);
		MOrg org = new MOrg(getCtx(), Env.getAD_Org_ID(getCtx()), get_TrxName());
		salesorder.setC_BPartner_ID(org.getLinkedC_BPartner_ID(get_TrxName()));
		salesorder.setC_DocTypeTarget_ID(p_doctype_ID);
		salesorder.setM_Warehouse_ID(p_warehouse_ID);
		salesorder.set_ValueOfColumn(X_AM_ServiceOrder.COLUMNNAME_AM_ServiceOrder_ID, recordID);
		salesorder.setC_Activity_ID(workorder.get_ValueAsInt(MOrder.COLUMNNAME_C_Activity_ID));
		salesorder.setUser1_ID(workorder.get_ValueAsInt(MOrder.COLUMNNAME_User1_ID));
		salesorder.saveEx();
	}

	private void createSalesOrderLine(X_AM_ServiceOrder_Resource resource)
	{
		MOrderLine line = new MOrderLine(salesorder);
		BigDecimal qtyrequired = resource.getResourceQty().subtract(resource.getQtyReserved());
		line.setM_Product_ID(resource.getM_Product_ID());
		line.setC_UOM_ID(resource.getC_UOM_ID());
		line.setQtyEntered(qtyrequired);
		BigDecimal QtyOrdered = MUOMConversion.convertProductFrom(getCtx(), resource.getM_Product_ID(),
				resource.getC_UOM_ID(), qtyrequired);
		line.setQtyOrdered(QtyOrdered);
		line.setPrice(resource.getCostAmt());
		line.set_ValueOfColumn(X_AM_ServiceOrder_Resource.COLUMNNAME_AM_ServiceOrder_Resource_ID,
				resource.getAM_ServiceOrder_Resource_ID());
		line.set_ValueOfColumn(X_AM_ServiceOrder_Resource.COLUMNNAME_AM_ServiceOrder_Task_ID,
				resource.getAM_ServiceOrder_Task_ID());
		line.setLineNetAmt();
		line.setC_Activity_ID(workorder.get_ValueAsInt(MOrder.COLUMNNAME_C_Activity_ID));
		line.setUser1_ID(workorder.get_ValueAsInt(MOrder.COLUMNNAME_User1_ID));
		line.saveEx();
	}

}
