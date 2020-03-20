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

import org.compiere.util.Env;


/**
 * Meter class
 * @contributor Mario Calderon, Systemhaus Westfalia, http://www.westfalia-it.com
 * @contributor Adaxa http://www.adaxa.com
 * @contributor Deepak Pansheriya, Loglite Technologies, http://logilite.com
 * @contributor Victor Perez, victor.perez@e-evolution.com, eEvolution http://www.e-evolution.com
 * @contributor Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 */
public class MAMAssetMeterLog extends X_AM_AssetMeterLog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 920285726461219946L;

	public MAMAssetMeterLog(Properties ctx, int AM_Meter_ID, String trxName) {
		super(ctx, AM_Meter_ID, trxName);
	}

	public MAMAssetMeterLog(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		MAMAssetMeter assetMeter = new MAMAssetMeter(getCtx(), getAM_AssetMeter_ID(), get_TrxName());
		BigDecimal currentMeasuring = assetMeter.getAmt();
		if(currentMeasuring == null) {
			currentMeasuring = Env.ZERO;
		}
		//	Set new current amount
		currentMeasuring = currentMeasuring.add(getMeasuringLog());
		//	set to asset meter
		assetMeter.setAmt(currentMeasuring);
		assetMeter.setDateTrx(getDateTrx());
		assetMeter.saveEx();
		//	Set to current record
		setCurrentMeasuring(currentMeasuring);
		return super.beforeSave(newRecord);
	}
}
