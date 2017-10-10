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

import org.compiere.model.MAllocationHdr;
import org.compiere.model.MClient;
import org.compiere.model.MCostDetail;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MMovement;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MPayment;
import org.compiere.model.MRMA;
import org.compiere.model.MRole;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.eevolution.model.MPPOrder;

/**
 * @author OFB Consulting http://www.ofbconsulting.com
 * 	<li> Initial Contributor
 * @contributor Mario Calderon, Systemhaus Westfalia, http://www.westfalia-it.com
 * @contributor Adaxa http://www.adaxa.com
 * @contributor Deepak Pansheriya, Loglite Technologies, http://logilite.com
 * @contributor Victor Perez, victor.perez@e-evolution.com, eEvolution http://www.e-evolution.com
 * @contributor Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 * @contributor Sachin Bhimani
 */
public class MaintenanceValidator implements ModelValidator
{

	/**
	 * Constructor.
	 */
	public MaintenanceValidator()
	{
		super();
	}

	/** Logger */
	private static CLogger	log				= CLogger.getCLogger(MaintenanceValidator.class);
	/** Client */
	private int				m_AD_Client_ID	= -1;
	/** User */
	private int				m_AD_User_ID	= -1;
	/** Role */
	private int				m_AD_Role_ID	= -1;

	/**
	 * Initialize Validation
	 * 
	 * @param engine validation engine
	 * @param client client (non-Javadoc)
	 * @see org.compiere.model.ModelValidator#initialize(org.compiere.model.ModelValidationEngine,
	 *      org.compiere.model.MClient)
	 */
	@Override
	public void initialize(ModelValidationEngine engine, MClient client)
	{
		// client = null for global validator
		if (client != null)
		{
			m_AD_Client_ID = client.getAD_Client_ID();
			log.info(client.toString());
		}
		else
		{
			log.info("Initializing global validator: " + this.toString());
		}

		// We want to be informed when instance is created/changed
		engine.addModelChange(MOrder.Table_Name, this);
		engine.addModelChange(MInvoice.Table_Name, this);
		engine.addModelChange(MInOut.Table_Name, this);
		engine.addModelChange(MInvoiceLine.Table_Name, this);
		engine.addModelChange(MOrderLine.Table_Name, this);
		engine.addModelChange(MCostDetail.Table_Name, this);

		// We want to validate instance
		engine.addDocValidate(MOrder.Table_Name, this);
		engine.addDocValidate(MInvoice.Table_Name, this);
		engine.addDocValidate(MInOut.Table_Name, this);
		engine.addDocValidate(MOrderLine.Table_Name, this);
		engine.addDocValidate(MRMA.Table_Name, this);
		engine.addDocValidate(MPayment.Table_Name, this);
		engine.addDocValidate(MMovement.Table_Name, this);
		engine.addDocValidate(MAllocationHdr.Table_Name, this);
		engine.addDocValidate(MPPOrder.Table_Name, this);

	} // initialize

	public int getAD_Client_ID()
	{
		return m_AD_Client_ID;
	}

	/**
	 * User Login. Called when preferences are set
	 * 
	 * @param AD_Org_ID org
	 * @param AD_Role_ID role
	 * @param AD_User_ID user
	 * @return error message or null
	 */
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		log.info("AD_User_ID=" + AD_User_ID);
		m_AD_User_ID = AD_User_ID;
		m_AD_Role_ID = AD_Role_ID;
		return null;
	}

	/**
	 * Model Change of a monitored Table. Called after
	 * PO.beforeSave/PO.beforeDelete when you called addModelChange for the
	 * table
	 * 
	 * @param po persistent object
	 * @param type TYPE_
	 * @return error message or null
	 * @exception Exception if the recipient wishes the change to be not accept.
	 */
	public String modelChange(PO po, int type) throws Exception
	{
		String error = null;
		if (po.get_TableName().equals(MOrder.Table_Name) && type == TYPE_CHANGE)
		{
		}

		return error;
	} // modelChange

	/**
	 * Validate Document. Called as first step of DocAction.prepareIt when you
	 * called addDocValidate for the table. Note that totals, etc. may not be
	 * correct.
	 * 
	 * @param po persistent object
	 * @param timing see TIMING_ constants
	 * @return error message or null
	 */
	public String docValidate(PO po, int timing)
	{
		String error = null;
		log.info(po.get_TableName() + " Timing: " + timing);

		if (timing == TIMING_AFTER_COMPLETE)
		{
			if (po.get_TableName().equals(MInOut.Table_Name))
			{
				MInOut inout = (MInOut) po;
				error = updateServiceOrder(inout);
			}

		}
		else if (timing == TIMING_BEFORE_PREPARE)
		{
		}
		else if (timing == TIMING_BEFORE_VOID)
		{
		}
		else if (timing == TIMING_BEFORE_REVERSECORRECT)
		{
		}
		else if (timing == TIMING_AFTER_REVERSECORRECT)
		{
		}

		return error;
	}

	/**
	 * String Representation
	 * 
	 * @return info
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer(MaintenanceValidator.class.getName());
		return sb.toString();
	} // toString

	/**
	 * Sample Validator Before Save Properties - to set mandatory properties on
	 * users avoid users changing properties
	 */
	public void beforeSaveProperties()
	{
		// not for SuperUser or role SysAdmin
		if (m_AD_User_ID == 0 // System
				|| m_AD_User_ID == 100 // SuperUser
				|| m_AD_Role_ID == 0 // System Administrator
				|| m_AD_Role_ID == 1000000) // ECO Admin
			return;

		log.info("Setting default Properties");

		MRole.get(Env.getCtx(), m_AD_Role_ID);

	} // beforeSaveProperties

	private String updateServiceOrder(MInOut inout)
	{
		MOrder order = new MOrder(inout.getCtx(), inout.getC_Order_ID(), inout.get_TrxName());
		if (order.get_ValueAsInt(X_AM_ServiceOrder.COLUMNNAME_AM_ServiceOrder_ID) == 0)
			return "";
		MInOutLine[] lines = inout.getLines();
		for (MInOutLine ioline : lines)
		{
			MOrderLine oline = new MOrderLine(inout.getCtx(), ioline.getC_OrderLine_ID(), inout.get_TrxName());
			if (oline.get_ValueAsInt(X_AM_ServiceOrderResource.COLUMNNAME_AM_ServiceOrderResource_ID) != 0)
			{
				X_AM_ServiceOrderResource resource = new X_AM_ServiceOrderResource(inout.getCtx(),
						oline.get_ValueAsInt(X_AM_ServiceOrderResource.COLUMNNAME_AM_ServiceOrderResource_ID),
						inout.get_TrxName());
				resource.setQtyDelivered(resource.getQtyDelivered().add(ioline.getMovementQty()));
				resource.saveEx();
			}
			else
				return "El enlace a la orden de trabajo no esta definido";
		}
		return "";
	}
}
