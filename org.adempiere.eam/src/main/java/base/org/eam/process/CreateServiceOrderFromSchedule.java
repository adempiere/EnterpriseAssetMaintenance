/*************************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                              *
 * This program is free software; you can redistribute it and/or modify it    		 *
 * under the terms version 2 or later of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope   		 *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied 		 *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           		 *
 * See the GNU General Public License for more details.                       		 *
 * You should have received a copy of the GNU General Public License along    		 *
 * with this program; if not, write to the Free Software Foundation, Inc.,    		 *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     		 *
 * For the text or an alternative of this public license, you may reach us    		 *
 * Copyright (C) 2012-2020 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpya.com				  		                 *
 *************************************************************************************/

package org.eam.process;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.adempiere.exceptions.AdempiereException;
import org.eam.model.MAMSchedule;
import org.eam.model.MAMServiceOrder;

/** 
 * Create Service Order from Schedule
 * 	@author Yamel Senih, ysenih@erpya.com , http://www.erpya.com
 */
public class CreateServiceOrderFromSchedule extends CreateServiceOrderFromScheduleAbstract {
	
	/** Service Order Cache : (AM_ServiceOrder_ID) -> MAMServiceOrder */
	private ArrayList<MAMServiceOrder> serviceOrderList = new ArrayList<MAMServiceOrder>();
	
	@Override
	protected String doIt() throws Exception {
		getSelectionKeys().stream().forEach(scheduleId -> {
			Timestamp maintenanceDate = getSelectionAsTimestamp(scheduleId, "SC_MaintenanceDate");
			MAMSchedule schedule = new MAMSchedule(getCtx(), scheduleId, get_TrxName());
			MAMServiceOrder serviceOrder = new MAMServiceOrder(getCtx(), 0, get_TrxName());
			//	Set Document Type
			if(getDocTypeTargetId() != 0) {
				serviceOrder.setC_DocType_ID(getDocTypeTargetId());
			}
			//	
			serviceOrder.setSchedule(schedule);
			serviceOrder.setDateDoc(getDateDoc());
			serviceOrder.setDateStartPlan(maintenanceDate);
			serviceOrder.setDocAction(getDocAction());
			serviceOrder.saveEx();
			//	Add to list
			serviceOrderList.add(serviceOrder);
		});
		//	Complete All
		completeServiceOrders();
		//	Return
		StringBuffer msg = new StringBuffer("@Created@ (")
					.append(serviceOrderList.size()).append(")");
		//	
		StringBuffer detail = new StringBuffer();
		//	Return
		for(MAMServiceOrder serviceOrder : serviceOrderList) {
			if(detail.length() > 0)
				detail.append(", ");
			//	
			detail.append(serviceOrder.getDocumentNo());
		}
		//	
		if(detail.length() > 0) {
			msg.append("[").append(detail).append("]");
		}
		//	
		return msg.toString();
	}
	
	/**
	 * Complete all Service Orders
	 */
	private void completeServiceOrders() {
		for(MAMServiceOrder serviceOrder : serviceOrderList) {
			serviceOrder.processIt(getDocAction());
			serviceOrder.saveEx();
			//	Validate Complete Document Status
			if(!serviceOrder.getDocStatus().equals(serviceOrder.getDocStatus())) {
				throw new AdempiereException(serviceOrder.getProcessMsg());
			}
		}
	}
	
}