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
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;
import org.compiere.util.CCache;

/**
 * Maintenance Pattern
 * @author OFB Consulting http://www.ofbconsulting.com
 * 	<li> Initial Contributor
 * @contributor Mario Calderon, Systemhaus Westfalia, http://www.westfalia-it.com
 * @contributor Adaxa http://www.adaxa.com
 * @contributor Deepak Pansheriya, Loglite Technologies, http://logilite.com
 * @contributor Victor Perez, victor.perez@e-evolution.com, eEvolution http://www.e-evolution.com
 * @contributor Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 * @contributor Sachin Bhimani
 */
public class MAMPattern extends X_AM_Pattern {

	/**
	 * 
	 */
	private static final long serialVersionUID = 920285726461219946L;

	public MAMPattern(Properties ctx, int AM_MaintenancePattern_ID, String trxName) {
		super(ctx, AM_MaintenancePattern_ID, trxName);
	}

	public MAMPattern(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/**	Cache						*/
	private static CCache<Integer, MAMPattern> cache = new CCache<Integer, MAMPattern>(Table_Name, 40, 5);	//	5 minutes

	/**
	 * get the costs of a Maintenance Pattern
	 * 
	 * @return Costs
	 */
	public BigDecimal getCosts() {
		BigDecimal mpCosts = BigDecimal.ZERO;
		for (MAMPatternTask task : getTasks()) {
			mpCosts = mpCosts.add(task.getCostAmt());
		}
		return mpCosts;
	} // getMPCosts

	/**
	 * update the costs of a Maintenance Pattern
	 */
	public void updateCosts() {
		this.setCostAmt(getCosts());
		this.saveEx();
	} // updateMPCosts
	
	/**
	 * Get Tasks from Pattern
	 * @return
	 */
	public List<MAMPatternTask> getTasks() {
		String whereClause = COLUMNNAME_AM_Pattern_ID + "=? ";
		List<MAMPatternTask> list = new Query(getCtx(), I_AM_PatternTask.Table_Name,
				whereClause, get_TrxName())
						.setClient_ID()
						.setParameters(getAM_Pattern_ID())
						.setOnlyActiveRecords(true)
						.list();

		return list;
	} // getJSTaskResources
	
	
	/**
	 * Get from cache
	 * @param ctx
	 * @param patternId
	 * @return
	 */
	public static MAMPattern get (Properties ctx, int patternId) {
		if (patternId <= 0) {
			return null;
		}
		MAMPattern retValue = (MAMPattern) cache.get (patternId);
		if (retValue != null) {
			return retValue;
		}
		retValue = new MAMPattern(ctx, patternId, null);
		if (retValue.get_ID () != 0) {
			cache.put (patternId, retValue);
		}
		return retValue;
	}	//	get
}
