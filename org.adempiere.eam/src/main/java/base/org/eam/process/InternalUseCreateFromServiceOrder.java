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

package org.eam.process;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MInventory;
import org.compiere.model.MInventoryLine;
import org.compiere.model.MLocator;
import org.compiere.model.MProduct;
import org.compiere.model.MUOM;
import org.compiere.model.MUOMConversion;
import org.compiere.model.MWarehouse;
import org.compiere.util.Msg;
import org.eam.model.I_AM_ServiceOrder;
import org.eam.model.I_AM_ServiceOrderResource;
import org.eam.model.MAMServiceOrderResource;

/**
 * Create Lines for Inventory Internal Use from Service Order
 * @author Yamel Senih, ysenih@erpya.com , http://www.erpya.com
 */
public class InternalUseCreateFromServiceOrder extends InternalUseCreateFromServiceOrderAbstract {
	
	/**	Created Counter	*/
	private AtomicInteger created;
	
	/**
	 * Get a valid locator
	 * @param locatorId
	 * @return
	 */
	private int getValidLocator(MLocator defaultLocator) {
		int locatorId = 0;
		// If a locator is specified on the product, choose that otherwise default locator
		if(getLocatorId() != 0)
			locatorId = getLocatorId();
		//	Validate Locator
		if(locatorId != 0) {
			MLocator locator = MLocator.get(getCtx(), locatorId);
			//	Set from default if it is distinct
			if(locator == null) {
				locatorId = 0;
			} else if(defaultLocator != null 
					&& locator.getM_Warehouse_ID() != defaultLocator.getM_Warehouse_ID()) {
				locatorId = 0;
			}
		}
		//	Valid locator
		if(locatorId == 0) {
			if(defaultLocator != null)
				locatorId = defaultLocator.getM_Locator_ID();
			else
				throw new AdempiereException("@M_Locator_ID@ @NotFound@");
		}
		//	Return
		return locatorId;
	}
	
	@Override
	protected String doIt() throws Exception {
		created = new AtomicInteger();
		//	Get Default Locator
		MInventory inventory = new MInventory(getCtx(), getRecord_ID(), get_TrxName());
		AtomicInteger serviceOrderId = new AtomicInteger();
		MLocator defaultLocator = MLocator.getDefault((MWarehouse) inventory.getM_Warehouse());
		getSelectionKeys().forEach(serviceOrderResourceId -> {
			MAMServiceOrderResource serviceOrderResource = new MAMServiceOrderResource(getCtx(), serviceOrderResourceId, get_TrxName());
			BigDecimal quantityToDeliver = getSelectionAsBigDecimal(serviceOrderResourceId, "SOR_QtyToDeliver");
			MProduct product = MProduct.get(getCtx(), serviceOrderResource.getM_Product_ID());
			//	Validate quantity
			if(quantityToDeliver.compareTo(serviceOrderResource.getRemainingQuantity()) > 0) {
				throw new AdempiereException("@QtyToDeliver@ > @ResourceQuantity@");
			}
			String documentNo = getSelectionAsString(serviceOrderResourceId, "SO_DocumentNo");
			String taskName = getSelectionAsString(serviceOrderResourceId, "SOT_Name");
			//	Create
			// If a locator is specified on the product, choose that otherwise default locator
			int locatorId = getValidLocator(defaultLocator);
			MInventoryLine inventoryLine = new MInventoryLine(getCtx(), 0, get_TrxName());
			inventoryLine.setM_Inventory_ID(inventory.getM_Inventory_ID());
			inventoryLine.setM_Product_ID(serviceOrderResource.getM_Product_ID());
			inventoryLine.setM_Locator_ID(locatorId);
			inventoryLine.addDescription(Msg.parseTranslation(getCtx(), "@RequiredFromMaintenance@ " + documentNo + " - " + taskName));
			//	Convert to uom
			int precision = product.getUOMPrecision();
			if(product.getC_UOM_ID() != serviceOrderResource.getC_UOM_ID()) {
				quantityToDeliver = MUOMConversion.convertProductFrom(getCtx(), product.getM_Product_ID(), serviceOrderResource.getC_UOM_ID(), quantityToDeliver);
				if(quantityToDeliver == null) {
					MUOM maintenanceUOM = MUOM.get(getCtx(), serviceOrderResource.getC_UOM_ID());
					MUOM productUOM = MUOM.get(getCtx(), product.getC_UOM_ID());
					throw new AdempiereException("@C_ConversionRate_ID@ @NotFound@ @M_Product_ID@ " + product.getValue() + " - " + product.getName() 
					+ "[@C_UOM_ID@ " + maintenanceUOM.getUOMSymbol() 
					+ " @C_UOM_To_ID@ " + productUOM.getUOMSymbol() + "]");
				}
			}
			quantityToDeliver = quantityToDeliver.setScale(precision, BigDecimal.ROUND_HALF_UP);
			inventoryLine.setQtyInternalUse(quantityToDeliver);
			inventoryLine.setC_Charge_ID(getChargeId());
			//	Set reference
			inventoryLine.set_ValueOfColumn(I_AM_ServiceOrderResource.COLUMNNAME_AM_ServiceOrderResource_ID, serviceOrderResource.getAM_ServiceOrderResource_ID());
			inventoryLine.saveEx();
			serviceOrderId.set(serviceOrderResource.getAM_ServiceOrderTask().getAM_ServiceOrder_ID());
			created.getAndIncrement();
		});
		//	Set Service Order
		if(serviceOrderId.get() > 0) {
			inventory.set_ValueOfColumn(I_AM_ServiceOrder.COLUMNNAME_AM_ServiceOrder_ID, serviceOrderId.get());
			inventory.saveEx();
		}
		return "@Created@ " + created;
	}
}