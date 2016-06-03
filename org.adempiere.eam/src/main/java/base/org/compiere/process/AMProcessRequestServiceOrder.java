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

import java.util.logging.Level;

import org.compiere.model.X_AM_ServiceOrder_Request;
import org.compiere.util.Env;

/**
 * Process Request Service Order
 * @author OFB Consulting http://www.ofbconsulting.com
 * 	<li> Initial Contributor
 * @contributor Mario Calderon, Systemhaus Westfalia, http://www.westfalia-it.com
 * @contributor Adaxa http://www.adaxa.com
 * @contributor Deepak Pansheriya, Loglite Technologies, http://logilite.com
 * @contributor Victor Perez, victor.perez@e-evolution.com, eEvolution http://www.e-evolution.com
 * @contributor Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 * @contributor Sachin Bhimani
 */
public class AMProcessRequestServiceOrder extends SvrProcess
{
	private String	p_DocAction;
	private int		Record_ID;

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

		Record_ID = getRecord_ID();
	}

	@Override
	protected String doIt() throws Exception
	{
		X_AM_ServiceOrder_Request soRequest = new X_AM_ServiceOrder_Request(Env.getCtx(), Record_ID, get_TrxName());

		if (p_DocAction.equals("CO") && !soRequest.isProcessed())
		{
			soRequest.setProcessed(true);
			soRequest.save();

			return "Confirmed";
		}

		if (p_DocAction.equals("RA") && soRequest.isProcessed() && soRequest.getDocStatus().equals("WC"))
		{
			soRequest.setProcessed(false);
			soRequest.save();

			return "Reactivated for Edition";
		}

		return "It is not possible to fulfill the action";
	}

}
