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
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.util.DB;
import org.compiere.util.Env;

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
public class CalloutAssetAMLog extends CalloutEngine
{

	public String assetLog(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (value == null)
			return "";
		Integer AssetMeter_ID = (Integer) mTab.getValue("AM_AssetMeter_ID");
		if (AssetMeter_ID == null)
			return "";

		Integer Meter_ID = DB.getSQLValue(null,
				"SELECT AM_Meter_ID FROM AM_AssetMeter WHERE AM_AssetMeter_ID=" + AssetMeter_ID);

		Integer Asset_ID = DB.getSQLValue(null,
				"SELECT A_Asset_ID FROM AM_AssetMeter WHERE AM_AssetMeter_ID=" + AssetMeter_ID);
		BigDecimal amt = (BigDecimal) mTab.getValue("Amt");

		int count = DB.getSQLValue(null,
				"SELECT COUNT(1) FROM AM_AssetMeter" + " WHERE AM_Meter_ID=?" + "  AND A_ASSET_ID=?",
				new Object[] { Meter_ID, Asset_ID });

		BigDecimal oldAmt = Env.ZERO;
		if (count <= 0)
		{
			mTab.setValue("CurrentAmt", amt);
		}
		else
		{
			PreparedStatement pstmt = null;
			String sql = "SELECT AMT FROM AM_AssetMeter " + " WHERE IsActive='Y' AND AM_Meter_ID=? AND A_ASSET_ID=? "
					+ "ORDER BY DateTrx DESC";
			try
			{
				pstmt = DB.prepareStatement(sql, null);
				pstmt.setInt(1, Meter_ID);
				pstmt.setInt(2, Asset_ID);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next())
				{
					mTab.setValue("CurrentAmt", amt.add(rs.getBigDecimal(1)));
					oldAmt = rs.getBigDecimal(1);
				}
				DB.close(rs, pstmt);
				pstmt = null;
				rs = null;
			}
			catch (Exception e)
			{
				log.log(Level.SEVERE, sql, e);
			}
		}

		BigDecimal maxAmt = DB.getSQLValueBD(null,
				"SELECT MAX(l.amt) FROM AM_AssetMeter_Log l	WHERE l.AM_AssetMeter_ID=?",
				new Object[] { AssetMeter_ID });

		if (maxAmt == null)
			maxAmt = Env.ZERO;

		amt = amt.add(oldAmt);

		DB.executeUpdate("UPDATE AM_AssetMeter m SET Amt=" + (maxAmt.compareTo(amt) > 0 ? maxAmt : amt)
				+ " WHERE m.AM_AssetMeter_ID=" + AssetMeter_ID.intValue(), null);

		return "";
	} // assetLog

	public String meterSearch(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (value == null)
			return "";
		Integer AssetMeterSearch_ID = (Integer) mTab.getValue("AM_AssetMeterSearch_ID");
		if (AssetMeterSearch_ID == null)
			return "";
		mTab.setValue("AM_AssetMeter_ID", AssetMeterSearch_ID);

		return "";
	} // meterSearch
}
