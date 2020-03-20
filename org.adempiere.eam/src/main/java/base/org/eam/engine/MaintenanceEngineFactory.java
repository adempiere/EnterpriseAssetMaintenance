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
package org.eam.engine;

import java.util.HashMap;
import java.util.Map;

import org.adempiere.exceptions.AdempiereException;
import org.eam.model.MAMMaintenance;

/**
 * Maintenance Type factory using standard maintenance types
 * @author Yamel Senih, ysenih@erpya.com , http://www.erpya.com
 */
public class MaintenanceEngineFactory {
	
	private static final MaintenanceEngineFactory instance = new MaintenanceEngineFactory();
	
	public static MaintenanceEngineFactory getInstance() {
		return instance;
	}
	
	private static final Map<String, Class<? extends MaintenanceEngine>>
	maintenanceEngines = new HashMap<String, Class<? extends MaintenanceEngine>>();
	static {
		maintenanceEngines.put(MAMMaintenance.MAINTENANCETYPE_Calendar_BasedMaintenance, CalendarBasedMaintenance.class);
	}
	
	/**
	 * Private constructor
	 */
	private MaintenanceEngineFactory() {
		
	}

	
	/**
	 * Get Maintenance Engine from Maintenance Type
	 * @param maintenanceType
	 * @return
	 */
	public MaintenanceEngine getEngine(String maintenanceType) {
		Class<? extends MaintenanceEngine> cl = maintenanceEngines.get(maintenanceType);
		if (cl == null) {
			throw new AdempiereException("Unsupported Maintenance Type " + maintenanceType);
		}
		MaintenanceEngine engine;
		try {
			engine = cl.newInstance();
		} catch (Exception e) {
			throw new AdempiereException(e);
		}
		return engine;
	}
	
	/**
	 * Get engine from maintenance definition
	 * @param maintenanceDefinition
	 * @return
	 */
	public MaintenanceEngine getEngine(MAMMaintenance maintenanceDefinition) {
		MaintenanceEngine engine = getEngine(maintenanceDefinition.getMaintenanceType());
		engine.setMaintenanceDefinition(maintenanceDefinition);
		return engine;
	}
}
