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
 * Copyright (C) 2012-2018 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpya.com				  		                 *
 *************************************************************************************/
package org.eam.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.Util;

/**
 * Schedule PO class
 * @author Yamel Senih, ysenih@erpya.com , http://www.erpya.com
 */
public class MAMSchedule extends X_AM_Schedule {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8437469761214582358L;

	public MAMSchedule(Properties ctx, int AM_Schedule_ID, String trxName) {
		super(ctx, AM_Schedule_ID, trxName);
	}
	
	public MAMSchedule(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/**
	 * Set values from maintenance
	 * @param maintenance
	 */
	public void setMaintenance(MAMMaintenance maintenance) {
		if(maintenance == null) {
			return;
		}
		setAM_Maintenance_ID(maintenance.getAM_Maintenance_ID());
		setPriorityRule(maintenance.getPriorityRule());
		//	Set Description
		if(!Util.isEmpty(maintenance.getDescription())) {
			setDescription(maintenance.getDescription());
		}
		//	Set comments
		if(!Util.isEmpty(maintenance.getComments())) {
			setComments(maintenance.getComments());
		}
	}
}
