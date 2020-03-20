/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * Copyright (C) 2003-2020 E.R.P. Consultores y Asociados, C.A.               *
 * All Rights Reserved.                                                       *
 * Contributor(s): Yamel Senih www.erpya.com                                  *
 *****************************************************************************/
package org.eam.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.compiere.model.Query;
import org.compiere.util.CCache;
import org.compiere.util.Env;

/**
 * Reason definition, it can be used for identify recurrence for maintenance
 * @author Yamel Senih, ysenih@erpya.com, ERPCyA http://www.erpya.com
 */
public class MAMReason extends X_AM_Reason {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5831361443495335312L;
	/**	Static Cache */
	private static CCache<Integer, MAMReason> reasonCacheIds = new CCache<Integer, MAMReason>(Table_Name, 30);
	
	/**
	 * 
	 * *** Constructor ***
	 * @param ctx
	 * @param reasonId
	 * @param trxName
	 */
	public MAMReason(Properties ctx, int reasonId, String trxName) {
		super(ctx, reasonId, trxName);
	}

	/**
	 * 
	 * *** Constructor ***
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MAMReason(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/**
	 * Get/Load Setting [CACHED]
	 * @param ctx context
	 * @param farmId
	 * @param trxName
	 * @return activity or null
	 */
	public static MAMReason getById(Properties ctx, int farmId, String trxName) {
		if (farmId <= 0) {
			return null;
		}

		MAMReason reason = reasonCacheIds.get(farmId);
		if (reason != null && reason.get_ID() > 0)
			return reason;

		reason = new Query(ctx , Table_Name , COLUMNNAME_AM_Reason_ID + "=?" , trxName)
				.setClient_ID()
				.setParameters(farmId)
				.first();
		if (reason != null && reason.get_ID() > 0) {
			reasonCacheIds.put(reason.get_ID(), reason);
		}
		return reason;
	}

	/**
	 * Get All Setting
	 * @param ctx
	 * @param resetCache
	 * @param trxName
	 * @return
	 */
	public static List<MAMReason> getAll(Properties ctx, boolean resetCache, String trxName) {
		List<MAMReason> reasonList;
		if (resetCache || reasonCacheIds.size() > 0){
			reasonList = new Query(Env.getCtx(), Table_Name, null , trxName)
					.setClient_ID()
					.setOrderBy(COLUMNNAME_Name)
					.list();
			reasonList.stream().forEach(setting -> {
				reasonCacheIds.put(setting.getAM_Reason_ID(), setting);
			});
			return reasonList;
		}
		reasonList = reasonCacheIds.entrySet().stream()
				.map(type -> type.getValue())
				.collect(Collectors.toList());
		return  reasonList;
	}
}
