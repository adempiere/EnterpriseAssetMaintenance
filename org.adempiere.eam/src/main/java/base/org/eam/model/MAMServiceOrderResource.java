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
package org.eam.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.MUOM;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * Service Order (Work Order) Resource
 * @author OFB Consulting http://www.ofbconsulting.com
 * 	<li> Initial Contributor
 * @contributor Mario Calderon, Systemhaus Westfalia, http://www.westfalia-it.com
 * @contributor Adaxa http://www.adaxa.com
 * @contributor Deepak Pansheriya, Loglite Technologies, http://logilite.com
 * @contributor Victor Perez, victor.perez@e-evolution.com, eEvolution http://www.e-evolution.com
 * @contributor Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 * @contributor Sachin Bhimani
 */
public class MAMServiceOrderResource extends X_AM_ServiceOrderResource
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2849852775433799353L;

	public MAMServiceOrderResource(Properties ctx, int AM_ServiceOrder_Resource_ID, String trxName) {
		super(ctx, AM_ServiceOrder_Resource_ID, trxName);
	}

	public MAMServiceOrderResource(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/**
	 * Set resource from Pattern Resource
	 * @param resource
	 */
	public void setPatternResource(MAMPatternResource resource) {
		setCostAmt(resource.getCostAmt());
		setM_BOM_ID(resource.getM_BOM_ID());
		setM_Product_ID(resource.getM_Product_ID());
		setResourceQuantity(resource.getResourceQuantity());
		setResourceType(resource.getResourceType());
		setS_Resource_ID(resource.getS_Resource_ID());
		setC_UOM_ID(resource.getC_UOM_ID());
	}
	
	/**
	 * Set resource from Maintenance Resource
	 * @param resource
	 */
	public void setMaintenanceResource(MAMMaintenanceResource resource) {
		setCostAmt(resource.getCostAmt());
		setM_BOM_ID(resource.getM_BOM_ID());
		setM_Product_ID(resource.getM_Product_ID());
		setResourceQuantity(resource.getResourceQuantity());
		setResourceType(resource.getResourceType());
		setS_Resource_ID(resource.getS_Resource_ID());
		setC_UOM_ID(resource.getC_UOM_ID());
	}
	
	/**
	 * Get quantity remaining for maintenance
	 * @param onlyCompleted only completed internal use
	 * @return
	 */
	public BigDecimal getRemainingQuantity(boolean onlyCompleted) {
		String whereClauseForCompleted = " AND EXISTS(SELECT 1 FROM M_Inventory i WHERE i.M_Inventory_ID = il.M_Inventory_ID AND i.DocStatus IN('CO', 'CL'))";
		BigDecimal resourceQuantity = getResourceQuantity();
		BigDecimal quantityInternalUse = DB.getSQLValueBD(get_TrxName(), "SELECT SUM(il.QtyInternalUse) "
				+ "FROM M_InventoryLine il "
				+ "WHERE il.AM_ServiceOrderResource_ID = ?" + (onlyCompleted? whereClauseForCompleted: ""), getAM_ServiceOrderResource_ID());
		//	Get current
		if(quantityInternalUse == null) {
			quantityInternalUse = Env.ZERO;
		}
		if(resourceQuantity == null) {
			resourceQuantity = Env.ZERO;
		}
		//	Return remaining
		return resourceQuantity.subtract(quantityInternalUse);
	}
	
	/**
	 * Get remaining quantity for service order
	 * @return
	 */
	public BigDecimal getRemainingQuantity() {
		return getRemainingQuantity(false);
	}
	
	/**
	 * Return true if resource is completely delivered
	 * @return
	 */
	public boolean isCompletedResource() {
		MUOM uom = MUOM.get(getCtx(), getC_UOM_ID());
		return getRemainingQuantity(true).setScale(uom.getStdPrecision(), BigDecimal.ROUND_HALF_UP).equals(Env.ZERO);
	}

	@Override
	public String toString() {
		return "MAMServiceOrderResource [getAM_ServiceOrderResource_ID()=" + getAM_ServiceOrderResource_ID()
				+ ", getC_UOM_ID()=" + getC_UOM_ID() + ", getM_Product_ID()=" + getM_Product_ID() + ", getQtyPlan()="
				+ getQtyPlan() + ", getResourceQuantity()=" + getResourceQuantity() + ", getResourceType()="
				+ getResourceType() + "]";
	}
}
