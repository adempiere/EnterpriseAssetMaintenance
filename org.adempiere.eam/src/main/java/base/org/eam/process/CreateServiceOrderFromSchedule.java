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
import org.eam.model.MAMMaintenance;
import org.eam.model.MAMPattern;
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
			int maintenanceId = getSelectionAsInt(scheduleId, "SC_AM_Maintenance_ID");
			int assetId = getSelectionAsInt(scheduleId, "MA_A_Asset_ID");
			int serviceRequestId = getSelectionAsInt(scheduleId, "MF_AM_ServiceRequest_ID");
			int patternId = getSelectionAsInt(scheduleId, "MA_AM_Pattern_ID");
			Timestamp maintenanceDate = getSelectionAsTimestamp(scheduleId, "SC_MaintenanceDate");
//			BigDecimal amount = getSelectionAsBigDecimal(key, "MF_Amt");
//			BigDecimal average = getSelectionAsBigDecimal(key, "MF_Average");
//			BigDecimal range = getSelectionAsBigDecimal(key, "MF_Range");
			//	Create ServiceOrder
			MAMMaintenance maintenance = MAMMaintenance.get(getCtx(), maintenanceId);
			MAMPattern pattern = MAMPattern.get(getCtx(), patternId);
			MAMServiceOrder serviceOrder = new MAMServiceOrder(getCtx(), 0, get_TrxName());
			//	Set Document Type
			if(getDocTypeTargetId() != 0) {
				serviceOrder.setC_DocType_ID(getDocTypeTargetId());
			}
			//	
			serviceOrder.setDateDoc(getDateDoc());
			serviceOrder.setDateStartPlan(maintenanceDate);
			serviceOrder.setA_Asset_ID(assetId);
			serviceOrder.setMaintenance(maintenance);
			serviceOrder.setPattern(pattern);
			//	Validate if is from forecast
			if(serviceRequestId != 0) {
				serviceOrder.setAM_ServiceRequest_ID(serviceRequestId);
			}
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