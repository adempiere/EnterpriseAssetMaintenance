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
package org.adempiere.webui.apps.form;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Vector;
import java.util.logging.Level;

import org.adempiere.webui.component.Button;
import org.adempiere.webui.component.Checkbox;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.GridFactory;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.ListModelTable;
import org.adempiere.webui.component.ListboxFactory;
import org.adempiere.webui.component.Panel;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.component.WListbox;
import org.adempiere.webui.editor.WDateEditor;
import org.adempiere.webui.editor.WSearchEditor;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.event.ValueChangeListener;
import org.adempiere.webui.event.WTableModelEvent;
import org.adempiere.webui.event.WTableModelListener;
import org.adempiere.webui.panel.ADForm;
import org.adempiere.webui.panel.CustomForm;
import org.adempiere.webui.panel.IFormController;
import org.adempiere.webui.panel.StatusBarPanel;
import org.compiere.model.MColumn;
import org.compiere.model.MDocType;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.X_AM_MaintenancePattern;
import org.compiere.model.X_AM_ServiceOrder;
import org.compiere.model.X_AM_ServiceOrder_Resource;
import org.compiere.model.X_AM_ServiceOrder_Task;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.compiere.util.Trx;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.North;
import org.zkoss.zkex.zul.South;
import org.zkoss.zul.Separator;

/**
 * @author OFB Consulting http://www.ofbconsulting.com
 * 	<li> Initial Contributor
 * @contributor Mario Calderon, Systemhaus Westfalia, http://www.westfalia-it.com
 * @contributor Adaxa http://www.adaxa.com
 * @contributor Deepak Pansheriya, Loglite Technologies, http://logilite.com
 * @contributor Victor Perez, victor.perez@e-evolution.com, eEvolution http://www.e-evolution.com
 * @contributor Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 */
public class WAMRequestServiceOrder implements IFormController, EventListener, WTableModelListener, ValueChangeListener
{

	private CustomForm		form				= new CustomForm();

	/** Window No */
	private int				m_WindowNo			= 0;

	/** Logger */
	private static CLogger	log					= CLogger.getCLogger(WAMRequestServiceOrder.class);

	private boolean			m_calculating		= false;
	private int				m_C_Currency_ID		= 0;

	//
	private Panel			parameterPanel		= new Panel();
	private Grid			parameterLayout		= GridFactory.newGridLayout();
	private Borderlayout	mainLayout			= new Borderlayout();
	private Panel			allocationPanel		= new Panel();
	private WListbox		requestTable		= ListboxFactory.newDataTable();
	private int				m_A_Asset_ID		= 0;
	private int				m_MP_ID			= 0;

	private Panel			requestPanel		= new Panel();
	private Label			requestLabel		= new Label();
	private Borderlayout	requestLayout		= new Borderlayout();
	private Label			requestInfo			= new Label();
	private Grid			allocationLayout	= GridFactory.newGridLayout();
	private Button			ProcessButton		= new Button();
	private Button			searchButton		= new Button();
	private Button			VoidButton			= new Button();
	private Button			addButton			= new Button();
	private Button			EnableButton		= new Button();
	private Button			ChangeButton		= new Button();
	private Label			assetLabel			= new Label();
	private Checkbox		selectall			= new Checkbox();
	private Label			allocCurrencyLabel	= new Label();
	private StatusBarPanel	statusBar			= new StatusBarPanel();
	private Label			dateLabel			= new Label();
	private WDateEditor		dateField			= new WDateEditor();
	private WSearchEditor	assetSearch			= null;
	private Label			jobLabel			= new Label();
	private WSearchEditor	jobSearch			= null;
	private Button			jobButton			= new Button();

	private Panel			southPanel			= new Panel();

	/**
	 * Initialize Panel
	 * 
	 * @param WindowNo window
	 * @param frame frame
	 */
	public WAMRequestServiceOrder()
	{
		log.info("Currency=" + m_C_Currency_ID);
		try
		{
			dynInit();
			zkInit();
			southPanel.appendChild(new Separator());
			southPanel.appendChild(statusBar);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "", e);
		}
	} // init

	/**
	 * Static Init
	 * 
	 * @throws Exception
	 */
	private void zkInit() throws Exception
	{
		form.appendChild(mainLayout);
		mainLayout.setWidth("99%");
		mainLayout.setHeight("100%");

		dateLabel.setText(Msg.getMsg(Env.getCtx(), "Date"));

		//
		parameterPanel.appendChild(parameterLayout);
		allocationPanel.appendChild(allocationLayout);
		requestLabel.setText("Request List");
		requestPanel.appendChild(requestLayout);

		requestInfo.setText(".");

		ProcessButton.setLabel(Msg.getMsg(Env.getCtx(), "Generate Service Order"));
		ProcessButton.addActionListener(this);
		VoidButton.setLabel(Msg.getMsg(Env.getCtx(), "Void Request"));
		VoidButton.addActionListener(this);
		searchButton.setLabel(Msg.getMsg(Env.getCtx(), "Search"));
		searchButton.addActionListener(this);
		addButton.setLabel(Msg.getMsg(Env.getCtx(), "Select"));
		addButton.addActionListener(this);
		EnableButton.setLabel(Msg.getMsg(Env.getCtx(), "Enable Editing"));
		EnableButton.addActionListener(this);
		ChangeButton.setLabel(Msg.getMsg(Env.getCtx(), "set Date"));
		ChangeButton.addActionListener(this);
		jobButton.setLabel("set Standard");
		jobButton.addActionListener(this);
		jobLabel.setText("Standard");

		assetLabel.setText(Msg.translate(Env.getCtx(), "A_Asset_ID"));
		selectall.setText(Msg.getMsg(Env.getCtx(), "Sellect all"));
		selectall.addActionListener(this);
		allocCurrencyLabel.setText(".");

		North north = new North();
		north.setStyle("border: none");
		mainLayout.appendChild(north);
		north.appendChild(parameterPanel);
		Rows rows = null;
		Row row = null;

		parameterLayout.setWidth("800px");
		rows = parameterLayout.newRows();
		row = rows.newRow();
		row.appendChild(dateLabel.rightAlign());
		row.appendChild(dateField.getComponent());
		row.appendChild(ChangeButton);
		row.appendChild(assetLabel.rightAlign());
		row.appendChild(assetSearch.getComponent());
		row.appendChild(selectall);
		row.appendChild(jobLabel.rightAlign());
		row.appendChild(jobSearch.getComponent());
		row.appendChild(jobButton);

		South south = new South();
		south.setStyle("border: none");
		mainLayout.appendChild(south);
		south.appendChild(southPanel);
		southPanel.appendChild(allocationPanel);
		allocationPanel.appendChild(allocationLayout);
		allocationLayout.setWidth("400px");
		rows = allocationLayout.newRows();
		row = rows.newRow();
		row.appendChild(VoidButton);
		row.appendChild(ProcessButton);

		requestPanel.appendChild(requestLayout);
		requestPanel.setWidth("100%");
		requestPanel.setHeight("100%");
		requestLayout.setWidth("100%");
		requestLayout.setHeight("100%");
		requestLayout.setStyle("border: none");

		north = new North();
		north.setStyle("border: none");
		requestLayout.appendChild(north);
		north.appendChild(requestLabel);
		south = new South();
		south.setStyle("border: none");
		requestLayout.appendChild(south);
		south.appendChild(requestInfo.rightAlign());
		Center center = new Center();
		requestLayout.appendChild(center);
		center.appendChild(requestTable);
		requestTable.setWidth("99%");
		requestTable.setHeight("99%");
		center.setStyle("border: none");
		//
		center = new Center();
		center.setFlex(true);
		mainLayout.appendChild(center);
		center.appendChild(requestPanel);// infoPanel
		/*
		 * infoPanel.setStyle("border: none"); infoPanel.setWidth("100%");
		 * infoPanel.setHeight("100%"); north = new North();
		 * north.setStyle("border: none"); north.setHeight("90%");
		 * infoPanel.appendChild(north); north.appendChild(requestPanel);
		 * north.setSplittable(true);
		 */

	} // jbInit

	/**
	 * Dynamic Init (prepare dynamic fields)
	 * 
	 * @throws Exception if Lookups cannot be initialized
	 */
	private void dynInit() throws Exception
	{

		int AD_Column_ID = MColumn.getColumn_ID("AM_Maintenance", "A_Asset_ID");
		MLookup lookupBP = MLookupFactory.get(Env.getCtx(), m_WindowNo, 0, AD_Column_ID, DisplayType.Search);
		assetSearch = new WSearchEditor("A_Asset_ID", true, false, true, lookupBP);
		assetSearch.addValueChangeListener(this);

		AD_Column_ID = MColumn.getColumn_ID("AM_Maintenance", "AM_MaintenancePattern_ID");
		MLookup lookupjob = MLookupFactory.get(Env.getCtx(), m_WindowNo, 0, AD_Column_ID, DisplayType.Search);
		jobSearch = new WSearchEditor("AM_MaintenancePattern_ID", true, false, true, lookupjob);
		jobSearch.addValueChangeListener(this);

		dateField.setValue(Env.getContextAsDate(Env.getCtx(), "#Date"));
		// Translation
		statusBar
				.setStatusLine("Select maintenance, change the predicted date, or directly generate the Service Order ");
		statusBar.setStatusDB("");
		loadMPs();

	} // dynInit

	/**
	 * Load Business Partner Info - Payments - Invoices
	 */
	private void loadMPs()
	{
		// log.config("Instance_ID=" +Instance_ID);
		// Need to have both values

		/********************************
		 * Load request table 1-TrxDate, 2-DocumentNo, (3-Currency, 4-PayAmt,)
		 * 5-ConvAmt, 6-ConvOpen, 7-Allocated
		 */
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		StringBuffer sql = new StringBuffer(
				"SELECT rt.DATEDOC,rt.DATEREQUIRED,rt.DOCUMENTNO,rt.AM_ServiceOrder_Request_ID, rt.AD_Org_ID, rt.AD_User_ID, "
						+ "rt.A_Asset_ID, rt.AM_MaintenancePattern_ID, rt.Description, u.name, a.name as AssetName, jo.name as jobName,rl.name as PriorityRule, rt.ServiceOrder_Request_Type "
						+ "FROM MP_OT_REQUEST rt "
						+ "INNER JOIN AD_User u on (rt.AD_User_ID=u.AD_User_ID) "
						+ "INNER JOIN A_Asset a on (rt.A_Asset_ID=a.A_Asset_ID) "
						+ "LEFT OUTER JOIN AM_MaintenancePattern jo on (rt.AM_MaintenancePattern_ID=jo.AM_MaintenancePattern_ID) "
						+ "INNER JOIN AD_Ref_List rl on (rt.PriorityRule=rl.value and rl. AD_Reference_ID=154) "
						+ "WHERE rt.DocStatus='WC' and rt.processed='N'");

		log.config("request=" + sql.toString());
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), null);
			String type = new String();
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				Vector<Object> line = new Vector<Object>();
				line.add(new Boolean(false));
				if (rs.getString(14).equals("RV"))
					type = "Revision";
				if (rs.getString(14).equals("RP"))
					type = "Repair";
				if (rs.getString(14).equals("CN"))
					type = "Complete Levels";
				line.add(type);
				line.add(rs.getTimestamp(2));
				KeyNamePair pp = new KeyNamePair(rs.getInt(4), rs.getString(3));
				line.add(pp);
				line.add(rs.getTimestamp(1));
				KeyNamePair pp2 = new KeyNamePair(rs.getInt(6), rs.getString(10));
				line.add(pp2);
				KeyNamePair pp3 = new KeyNamePair(rs.getInt(7), rs.getString(11));
				line.add(pp3);
				line.add(rs.getString(9));
				line.add(rs.getString(13));
				KeyNamePair pp4 = new KeyNamePair(rs.getInt(8), rs.getString(12));
				line.add(pp4);
				data.add(line);
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql.toString(), e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		requestTable.clear();
		requestTable.getModel().removeTableModelListener(this);

		Vector<String> columnNames = new Vector<String>();
		columnNames.add(Msg.getMsg(Env.getCtx(), "Selected"));
		columnNames.add(Msg.getMsg(Env.getCtx(), "Type"));
		columnNames.add(Msg.getMsg(Env.getCtx(), "Date Programed"));
		columnNames.add(Msg.getMsg(Env.getCtx(), "Request No"));
		columnNames.add(Msg.getMsg(Env.getCtx(), "Date Request"));
		columnNames.add(Msg.getMsg(Env.getCtx(), "AD_User_ID"));
		columnNames.add(Msg.getMsg(Env.getCtx(), "Asset"));
		columnNames.add("Description");
		columnNames.add(Msg.getMsg(Env.getCtx(), "Priority"));
		columnNames.add(Msg.getMsg(Env.getCtx(), "Maintenance Pattern"));

		ListModelTable modelP = new ListModelTable(data);
		modelP.addTableModelListener(this);
		requestTable.setData(modelP, columnNames);

		int i = 0;
		requestTable.setColumnClass(i++, Boolean.class, false);
		requestTable.setColumnClass(i++, String.class, true);

		requestTable.setColumnClass(i++, Timestamp.class, false);

		requestTable.setColumnClass(i++, String.class, true);
		requestTable.setColumnClass(i++, Timestamp.class, true);
		requestTable.setColumnClass(i++, String.class, true);
		requestTable.setColumnClass(i++, String.class, true);
		requestTable.setColumnClass(i++, String.class, true);
		requestTable.setColumnClass(i++, String.class, true);
		requestTable.setColumnClass(i++, String.class, true);

		// Table UI
		requestTable.autoSize();

		requestTable.setColumnReadOnly(1, false);

		calculate();
	} // loadBPartner

	/**************************************************************************
	 * Action Listener. - MultiCurrency - Allocate
	 * 
	 * @param e event
	 */
	public void onEvent(Event e)
	{
		log.config("");
		if (e.getTarget().equals(ProcessButton))
		{
			saveData();
		}
		else if (e.getTarget().equals(jobButton))
		{
			ListModelTable request = requestTable.getModel();
			int rows = request.getRowCount();
			X_AM_MaintenancePattern job = new X_AM_MaintenancePattern(Env.getCtx(), m_MP_ID, null);
			for (int i = 0; i < rows; i++)
			{
				if (((Boolean) request.getValueAt(i, 0)).booleanValue())
				{
					KeyNamePair pp = new KeyNamePair(job.getAM_MaintenancePattern_ID(), job.getName());
					request.setValueAt(pp, i, 9);
				}
			}

		}
		else if (e.getTarget().equals(VoidButton))
		{
			voidData();
		}
		else if (e.getTarget().equals(ChangeButton))
		{
			ProcessButton.setEnabled(true);
			ListModelTable request = requestTable.getModel();
			int rows = request.getRowCount();
			Timestamp DateTrx = (Timestamp) dateField.getValue();
			for (int i = 0; i < rows; i++)
			{
				if (((Boolean) request.getValueAt(i, 0)).booleanValue())
				{
					request.setValueAt(DateTrx, i, 2);
				}
			}
		}
		else if (e.getTarget().equals(selectall))
		{
			ListModelTable request = requestTable.getModel();
			int rows = request.getRowCount();
			for (int i = 0; i < rows; i++)
			{
				if (m_A_Asset_ID == 0)
					request.setValueAt(selectall.isSelected(), i, 0);
				else
				{
					KeyNamePair pp = (KeyNamePair) request.getValueAt(i, 6);// Asset
					int Asset_ID = pp.getKey();
					if (Asset_ID == m_A_Asset_ID)
						request.setValueAt(selectall.isSelected(), i, 0);
				}

			}
		}
	} // actionPerformed

	/**
	 * Table Model Listener. - Recalculate Totals
	 * 
	 * @param e event
	 */
	public void tableChanged(WTableModelEvent e)
	{
		boolean isUpdate = (e.getType() == WTableModelEvent.CONTENTS_CHANGED);
		// Not a table update
		if (!isUpdate)
		{
			calculate();
			return;
		}

		/**
		 * Setting defaults
		 */
		if (m_calculating) // Avoid recursive calls
			return;
		m_calculating = true;

		m_calculating = false;
		calculate();
	} // tableChanged

	/**
	 * Calculate Allocation info
	 */
	private void calculate()
	{
		log.config("");
		//
		DecimalFormat format = DisplayType.getNumberFormat(DisplayType.Amount);
		Timestamp allocDate = null;

	} // calculate

	/**
	 * Value Change Listener. - Business Partner - Currency - Date
	 * 
	 * @param e event
	 */
	public void valueChange(ValueChangeEvent e)
	{
		String name = e.getPropertyName();
		Object value = e.getNewValue();
		log.config(name + "=" + value);
		if (name.equals("A_Asset_ID"))
		{
			assetSearch.setValue(value);
			if (value != null)
				m_A_Asset_ID = ((Integer) value).intValue();

		}
		else if (name.equals("AM_MaintenancePattern_ID"))
		{
			jobSearch.setValue(value);
			if (value != null)
				m_MP_ID = ((Integer) value).intValue();
		}

	} // vetoableChange

	/**************************************************************************
	 * Save Data
	 */
	private void saveData()
	{

		ListModelTable request = requestTable.getModel();
		int rows = request.getRowCount();
		Timestamp DateTrx = (Timestamp) dateField.getValue();
		Trx trx = Trx.get(Trx.createTrxName("AL"), true);
		for (int i = 0; i < rows; i++)
		{
			if (((Boolean) request.getValueAt(i, 0)).booleanValue())
			{
				KeyNamePair pp = (KeyNamePair) request.getValueAt(i, 3);
				int Req_ID = pp.getKey();
				pp = (KeyNamePair) request.getValueAt(i, 6);
				int Asset_ID = pp.getKey();
				Timestamp Datetrx = (Timestamp) request.getValueAt(i, 2);
				String description = (String) request.getValueAt(i, 7);

				int mpID = 0;
				if (request.getValueAt(i, 9) != null)
				{
					pp = (KeyNamePair) request.getValueAt(i, 9);
					mpID = pp.getKey();
				}

				if (!createSO(mpID, Datetrx, description, Asset_ID, Req_ID))
				{
					log.log(Level.SEVERE, "Service Order not created #" + i);
					continue;
				}

				String sql = "Update AM_ServiceOrder_Request SET DocStatus='AP',AM_MaintenancePattern_ID=" + mpID
						+ " where AM_ServiceOrder_Request_ID=" + Req_ID;

				DB.executeUpdate(sql, null);

			}
		}
		loadMPs();
	} // saveData

	/**************************************************************************
	 * Void Data
	 */
	private void voidData()
	{

		ListModelTable request = requestTable.getModel();
		int rows = request.getRowCount();
		Trx trx = Trx.get(Trx.createTrxName("AL"), true);
		for (int i = 0; i < rows; i++)
		{
			if (((Boolean) request.getValueAt(i, 0)).booleanValue())
			{
				KeyNamePair pp = (KeyNamePair) request.getValueAt(i, 3);// Req
				int Req_ID = pp.getKey();

				String sql = "UPDATE AM_ServiceOrder_Request SET DocStatus='VO'" + " WHERE AM_ServiceOrder_Request_ID="
						+ Req_ID;

				DB.executeUpdate(sql, null);
			}
		}
		loadMPs();
	} // voidDate

	public boolean createSO(int Job_ID, Timestamp Datetrx, String description, int Asset_ID, int Req_ID)
	{

		X_AM_ServiceOrder newSO = new X_AM_ServiceOrder(Env.getCtx(), 0, null);
		newSO.setDateTrx(Datetrx);
		newSO.setDescription(description);
		newSO.setA_Asset_ID(Asset_ID);
		newSO.setAM_MaintenancePattern_ID(Job_ID);
		newSO.setDocStatus("DR");
		newSO.setDocAction("CO");
		newSO.setAM_ServiceOrder_Request_ID(Req_ID);
		newSO.setC_DocType_ID(MDocType.getOfDocBaseType(Env.getCtx(), "OTC")[0].getC_DocType_ID());
		if (!newSO.save())// creada nueva OT
			return false;

		if (!createSOTaskDetail(Job_ID, newSO))
			return false;

		return true;
	}

	public void lookChilds(int MP_ID, X_AM_ServiceOrder SO)
	{

		String sql = "SELECT AM_Maintenance_ID from AM_Maintenance WHERE isChild='Y' and AM_MaintenanceParent_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, MP_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				createSOTaskDetail(rs.getInt(1), SO);
				lookChilds(rs.getInt(1), SO);
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

	}

	public boolean createSOTaskDetail(int Job_ID, X_AM_ServiceOrder OT)
	{

		String sql = "SELECT * FROM AM_MaintenancePattern_Task WHERE AM_MaintenancePattern_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, Job_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				X_AM_ServiceOrder_Task task = new X_AM_ServiceOrder_Task(Env.getCtx(), 0, null);
				// ta.setMP_Maintain_ID(MP_ID);
				task.setAD_Org_ID(rs.getInt("AD_Org_ID"));
				task.setAM_ServiceOrder_ID(OT.getAM_ServiceOrder_ID());
				task.setDescription(rs.getString("Description"));
				task.setDuration(rs.getBigDecimal("Duration").intValue());
				task.setC_UOM_ID(rs.getInt("C_UOM_ID"));
				task.setStatus(X_AM_ServiceOrder_Task.STATUS_NotStarted);
				task.saveEx();

				createSOResourceDetail(rs.getInt("AM_MaintenancePattern_Task_ID"), task.getAM_ServiceOrder_Task_ID());
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return true;

	}

	public boolean createSOResourceDetail(int oldTask_ID, int newTask_ID)
	{

		String sql = "SELECT * FROM AM_MaintPattern_Resource WHERE AM_MaintenancePattern_Task_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, oldTask_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				X_AM_ServiceOrder_Resource re = new X_AM_ServiceOrder_Resource(Env.getCtx(), 0, null);
				re.setAD_Org_ID(rs.getInt("AD_Org_ID"));
				re.setAM_ServiceOrder_Task_ID(newTask_ID);
				re.setCostAmt(rs.getBigDecimal("CostAmt"));
				re.setS_Resource_ID(rs.getInt("S_Resource_ID"));
				re.setM_BOM_ID(rs.getInt("M_BOM_ID"));
				re.setResourceQty(rs.getBigDecimal("RESOURCEQTY"));
				re.setResourceType(rs.getString("RESOURCETYPE"));
				re.set_ValueOfColumn("M_Product_ID", rs.getInt("M_Product_ID"));
				re.saveEx();
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return true;

	}

	/**
	 * Called by org.adempiere.webui.panel.ADForm.openForm(int)
	 * 
	 * @return
	 */
	public ADForm getForm()
	{
		return form;
	}

} // VAllocation
