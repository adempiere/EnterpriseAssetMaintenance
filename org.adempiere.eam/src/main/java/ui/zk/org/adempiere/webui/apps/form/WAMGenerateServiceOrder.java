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

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
import org.compiere.model.X_AM_Maintenance;
import org.compiere.model.X_AM_ServiceOrder;
import org.compiere.model.X_AM_ServiceOrder_Resource;
import org.compiere.model.X_AM_ServiceOrder_Task;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.compiere.util.Util;
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
public class WAMGenerateServiceOrder implements IFormController, EventListener, WTableModelListener,
		ValueChangeListener
{
	private CustomForm		form				= new CustomForm();

	/** Window No */
	private int				m_WindowNo			= 0;

	/** Logger */
	private static CLogger	log					= CLogger.getCLogger(WAMGenerateServiceOrder.class);

	private boolean			m_calculating		= false;
	private int				m_C_Currency_ID		= 0;

	private Panel			parameterPanel		= new Panel();
	private Grid			parameterLayout		= GridFactory.newGridLayout();
	private Borderlayout	mainLayout			= new Borderlayout();
	private Panel			allocationPanel		= new Panel();
	private WListbox		prognosisTable		= ListboxFactory.newDataTable();
	private int				m_A_Asset_ID		= 0;

	private Panel			prognosisPanel		= new Panel();
	private Panel			selectedPanel		= new Panel();
	private Label			prognosisLabel		= new Label();
	private Borderlayout	prognosisLayout		= new Borderlayout();
	private Borderlayout	selectedLayout		= new Borderlayout();
	private Label			prognosisInfo		= new Label();
	private Label			invoiceInfo			= new Label();
	private Grid			allocationLayout	= GridFactory.newGridLayout();
	private Button			ProcessButton		= new Button();
	private Button			searchButton		= new Button();
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

	private Panel			southPanel			= new Panel();

	/**
	 * Initialize Panel
	 * 
	 * @param WindowNo window
	 * @param frame frame
	 */
	public WAMGenerateServiceOrder()
	{
		Env.setContext(Env.getCtx(), m_WindowNo, "IsSOTrx", "Y");

		m_C_Currency_ID = Env.getContextAsInt(Env.getCtx(), "$C_Currency_ID");

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
		prognosisLabel.setText("List of Mps");
		prognosisPanel.appendChild(prognosisLayout);
		selectedPanel.appendChild(selectedLayout);

		invoiceInfo.setText(".");

		prognosisInfo.setText(".");

		ProcessButton.setLabel(Msg.getMsg(Env.getCtx(), "Generate Service Order"));
		ProcessButton.addActionListener(this);
		searchButton.setLabel(Msg.getMsg(Env.getCtx(), "Search"));
		searchButton.addActionListener(this);
		addButton.setLabel(Msg.getMsg(Env.getCtx(), "Add to Selection"));
		addButton.addActionListener(this);
		EnableButton.setLabel(Msg.getMsg(Env.getCtx(), "Enable Editing"));
		EnableButton.addActionListener(this);
		ChangeButton.setLabel(Msg.getMsg(Env.getCtx(), "Set Date"));
		ChangeButton.addActionListener(this);

		assetLabel.setText(Msg.translate(Env.getCtx(), "A_Asset_ID"));
		selectall.setText(Msg.getMsg(Env.getCtx(), "Select All"));
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
		row.appendChild(selectall);
		row.appendChild(assetLabel.rightAlign());
		row.appendChild(assetSearch.getComponent());

		South south = new South();
		south.setStyle("border: none");
		mainLayout.appendChild(south);
		south.appendChild(southPanel);
		southPanel.appendChild(allocationPanel);
		allocationPanel.appendChild(allocationLayout);
		allocationLayout.setWidth("400px");
		rows = allocationLayout.newRows();
		row = rows.newRow();
		row.appendChild(ProcessButton);

		prognosisPanel.appendChild(prognosisLayout);
		prognosisPanel.setWidth("100%");
		prognosisPanel.setHeight("100%");
		prognosisLayout.setWidth("100%");
		prognosisLayout.setHeight("100%");
		prognosisLayout.setStyle("border: none");

		north = new North();
		north.setStyle("border: none");
		prognosisLayout.appendChild(north);
		north.appendChild(prognosisLabel);
		south = new South();
		south.setStyle("border: none");
		prognosisLayout.appendChild(south);
		south.appendChild(prognosisInfo.rightAlign());
		Center center = new Center();
		prognosisLayout.appendChild(center);
		center.appendChild(prognosisTable);
		prognosisTable.setWidth("99%");
		prognosisTable.setHeight("99%");
		center.setStyle("border: none");

		//
		center = new Center();
		center.setFlex(true);
		mainLayout.appendChild(center);
		center.appendChild(prognosisPanel);

		/*
		 * infoPanel.setStyle("border: none"); infoPanel.setWidth("100%");
		 * infoPanel.setHeight("100%"); north = new North();
		 * north.setStyle("border: none"); north.setHeight("49%");
		 * infoPanel.appendChild(north); north.appendChild(prognosisPanel);
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

		// Asset
		int AD_Column_ID = MColumn.getColumn_ID("AM_Maintenance", "A_Asset_ID"); // AM_Maintenance.A_Asset_ID
		MLookup lookupBP = MLookupFactory.get(Env.getCtx(), m_WindowNo, 0, AD_Column_ID, DisplayType.Search);
		assetSearch = new WSearchEditor("A_Asset_ID", true, false, true, lookupBP);
		assetSearch.addValueChangeListener(this);

		// Date set to Login Date
		dateField.setValue(Env.getContextAsDate(Env.getCtx(), "#Date"));
		// Translation
		statusBar
				.setStatusLine("Select the maintenance, change the predicted date, or directly generate the Service Order ");
		statusBar.setStatusDB("");
		loadMPs();

	} // dynInit

	/**
	 * Load Business Partner Info - Payments - Invoices
	 * 
	 * @throws SQLException
	 */
	private void loadMPs() throws SQLException
	{
		/********************************
		 * Load Prognosis table 1-TrxDate, 2-DocumentNo, (3-Currency, 4-PayAmt,)
		 * 5-ConvAmt, 6-ConvOpen, 7-Allocated
		 */
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		StringBuffer sql = new StringBuffer("SELECT 	p.AD_PInstance_ID, p.AD_Client_ID, p.AD_Org_ID,"
				+ "	p.A_Asset_ID, p.AM_Maintenance_ID, p.Description, p.AM_ProgrammingType, p.Frequency, p.DateTrx,"
				+ "	mp.DocumentNo ||'-'|| mp.Description AS MP_name, a.name AS assetname "
				+ "FROM AM_Prognosis p "
				+ "INNER JOIN AM_Maintenance mp ON (p.AM_Maintenance_ID=mp.AM_Maintenance_ID) "
				+ "INNER JOIN A_Asset a ON (p.A_Asset_ID=a.A_Asset_ID) "
				+ "WHERE p.Processed='N' AND p.IsSelected='N' "
				+ "ORDER BY p.DateTrx ASC");

		log.config("Prognosis=" + sql.toString());
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), null);
			rs = pstmt.executeQuery();

			while (rs.next())
			{
				Vector<Object> line = new Vector<Object>();
				line.add(new Boolean(false)); // 0-Selection
				line.add(rs.getInt(8)); // 1-Frequency
				line.add(rs.getTimestamp(9)); // 2-DateTrx
				KeyNamePair pp = new KeyNamePair(rs.getInt(4), rs.getString(11));
				line.add(pp); // 3-AssetInfo
				KeyNamePair pp2 = new KeyNamePair(rs.getInt(5), rs.getString(10));
				line.add(pp2); // 4-Maintenance
				line.add(rs.getString(7)); // 5-programing type
				line.add(rs.getString(6)); // 6-description

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
			rs.close();
			pstmt.close();
		}

		prognosisTable.clear();
		prognosisTable.getModel().removeTableModelListener(this);

		Vector<String> columnNames = new Vector<String>();
		columnNames.add("Generar Service Order");
		columnNames.add(Msg.getMsg(Env.getCtx(), "Frequency"));
		columnNames.add(Msg.translate(Env.getCtx(), "Date"));
		columnNames.add(Util.cleanAmp(Msg.translate(Env.getCtx(), "Asset")));
		columnNames.add(Msg.getMsg(Env.getCtx(), "AM"));
		columnNames.add(Msg.getMsg(Env.getCtx(), "Programing Type"));
		columnNames.add(Msg.getMsg(Env.getCtx(), "Description"));

		// Set Model
		ListModelTable modelP = new ListModelTable(data);
		modelP.addTableModelListener(this);
		prognosisTable.setData(modelP, columnNames);

		int i = 0;
		prognosisTable.setColumnClass(i++, Boolean.class, false); // 0-Selection
		prognosisTable.setColumnClass(i++, Integer.class, true); // 2-Frequency
		prognosisTable.setColumnClass(i++, Timestamp.class, true); // 3-TrxDate
		prognosisTable.setColumnClass(i++, String.class, true); // 4-asset
		prognosisTable.setColumnClass(i++, String.class, true); // 5-Maintenance
		prognosisTable.setColumnClass(i++, String.class, true); // 6-programingtype
		prognosisTable.setColumnClass(i++, String.class, true); // 7-Description
		prognosisTable.autoSize();

		prognosisTable.setColumnReadOnly(1, false);

		calculate();
	} // loadBPartner

	public void onEvent(Event e) throws SQLException
	{
		log.config("");
		if (e.getTarget().equals(ProcessButton))
		{
			saveData();
		}
		else if (e.getTarget().equals(EnableButton))
		{
			prognosisTable.setColumnReadOnly(1, false);
			prognosisTable.setColumnReadOnly(2, false);
			prognosisTable.setColumnReadOnly(3, false);
			/*
			 * Object Period = JOptionPane.showInputDialog( this,
			 * "Seleccione Periodo", "Selector de opciones",
			 * JOptionPane.QUESTION_MESSAGE, null, // null para icono defecto
			 * new Object[] { "1", "2", "3", "4", "5", "6", "7", "8" }, "1");
			 * String trxName = Trx.createTrxName("IOG"); Trx trx =
			 * Trx.get(trxName, true); AD_Process_ID = DB.getSQLValue(trxName,
			 * "select AD_Process_ID from AD_Process where upper(procedurename)='MP_PROGNOSIS_PROCESS'"
			 * ); if(instance==null){ instance = new MPInstance(Env.getCtx(),
			 * AD_Process_ID, 0); if (!instance.save()) {
			 * log.severe("ProcessNoInstance"); return; } } //call process
			 * ProcessInfo pi = new ProcessInfo ("VMPGenerateOT",
			 * AD_Process_ID); pi.setAD_PInstance_ID
			 * (instance.getAD_PInstance_ID()); // Add Parameter - Selection=Y
			 * MPInstancePara ip = new MPInstancePara(instance, 10);
			 * ip.setParameter("PeriodNo",Integer.parseInt((String)Period)); if
			 * (!ip.save()) { String msg = "No Parameter added"; // not
			 * translated log.log(Level.SEVERE, msg); return; } // Execute
			 * Process ProcessCtl worker = new ProcessCtl(this,
			 * Env.getWindowNo(this), pi, trx); worker.start();
			 * loadMPs(instance.getAD_PInstance_ID());
			 */
		}
		/*
		 * else if (e.getSource().equals(addButton)){
		 * ProcessButton.setEnabled(true); //updating rows selected TableModel
		 * prognosis = prognosisTable.getModel(); int rows =
		 * prognosis.getRowCount(); for (int i = 0; i < rows; i++) { if
		 * (((Boolean)prognosis.getValueAt(i, 0)).booleanValue()) { KeyNamePair
		 * pp = (KeyNamePair)prognosis.getValueAt(i, 3); // Asset int
		 * Asset_ID=pp.getKey(); pp = (KeyNamePair)prognosis.getValueAt(i, 4);
		 * // MP int MP_ID=pp.getKey(); int
		 * ciclo=((Integer)prognosis.getValueAt(i, 1)).intValue(); // Ciclo
		 * String ProgramingType=(String)prognosis.getValueAt(i, 5); //
		 * Programing String
		 * sql="Update MP_Prognosis set selected='Y' where ciclo="+ciclo +
		 * " and MP_MAINTAIN_ID="+MP_ID+" and A_Asset_ID="+Asset_ID +
		 * " and PROGRAMMINGTYPE='"+ ProgramingType +"'"; DB.executeUpdate(sql);
		 * } } //load rows loadMPs(instance.getAD_PInstance_ID()); }
		 */
		else if (e.getTarget().equals(ChangeButton))
		{
			ProcessButton.setEnabled(true);

			int rows = prognosisTable.getRowCount();
			Timestamp DateTrx = (Timestamp) dateField.getValue();
			for (int i = 0; i < rows; i++)
			{
				if (((Boolean) prognosisTable.getValueAt(i, 0)).booleanValue())
				{
					prognosisTable.setValueAt(DateTrx, i, 2);
				}
			}
		}
		else if (e.getTarget().equals(selectall))
		{
			int rows = prognosisTable.getRowCount();
			for (int i = 0; i < rows; i++)
			{
				if (m_A_Asset_ID == 0)
					prognosisTable.setValueAt(selectall.isSelected(), i, 0);
				else
				{
					KeyNamePair pp = (KeyNamePair) prognosisTable.getValueAt(i, 3);// Asset
					int Asset_ID = pp.getKey();
					if (Asset_ID == m_A_Asset_ID)
						prognosisTable.setValueAt(selectall.isSelected(), i, 0);
				}

			}
		}
	} // actionPerformed

	public void tableChanged(WTableModelEvent e)
	{
		boolean isUpdate = (e.getType() == WTableModelEvent.CONTENTS_CHANGED);
		// Not a table update
		if (!isUpdate)
		{
			calculate();
			return;
		}

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

	} // calculate

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

	} // vetoableChange

	private void saveData() throws SQLException
	{
		ListModelTable prognosis = prognosisTable.getModel();
		int rows = prognosis.getRowCount();
		for (int i = 0; i < rows; i++)
		{
			if (((Boolean) prognosis.getValueAt(i, 0)).booleanValue())
			{
				KeyNamePair pp = (KeyNamePair) prognosis.getValueAt(i, 4);// Maintenance
				int AM_ID = pp.getKey();
				pp = (KeyNamePair) prognosis.getValueAt(i, 3);// Asset
				int Asset_ID = pp.getKey();
				Timestamp Datetrx = (Timestamp) prognosis.getValueAt(i, 2);// Date
				String description = (String) prognosis.getValueAt(i, 6);// Description
				Integer frequency = (Integer) (prognosis.getValueAt(i, 1));
				String ProgramingType = (String) prognosis.getValueAt(i, 5); // ProgramingType

				if (!createServiceOrder(AM_ID, Datetrx, description, Asset_ID))
				{
					log.log(Level.SEVERE, "Service Order not created #" + i);
					continue;
				}

				updateMP(AM_ID, Datetrx, description);

				String sql = "Update AM_Prognosis set Processed='Y' where frequency=" + frequency
						+ " and AM_Maintenance_ID=" + AM_ID + " and A_Asset_ID=" + Asset_ID
						+ " and AM_ProgrammingType='" + ProgramingType + "'";

				DB.executeUpdate(sql, null);

			}
		}
		loadMPs();
	} // saveData

	public boolean createServiceOrder(int AM_ID, Timestamp Datetrx, String description, int Asset_ID)
	{
		X_AM_ServiceOrder newServiceOrder = new X_AM_ServiceOrder(Env.getCtx(), 0, null);
		newServiceOrder.setDateTrx(Datetrx);
		newServiceOrder.setDescription(description);
		newServiceOrder.setA_Asset_ID(Asset_ID);
		newServiceOrder.setAM_Maintenance_ID(AM_ID);
		newServiceOrder.setDocStatus("DR");
		newServiceOrder.setDocAction("CO");
		newServiceOrder.setC_DocType_ID(MDocType.getOfDocBaseType(Env.getCtx(), "OTP")[0].getC_DocType_ID());
		if (!newServiceOrder.save())
			return false;

		if (!createSOTaskDetail(AM_ID, newServiceOrder))
			return false;

		String sql = "SELECT AM_Maintenance_ID FROM AM_Maintenance WHERE IsChild='Y' AND AM_MaintenanceParent_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, AM_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				createSOTaskDetail(rs.getInt(1), newServiceOrder);
				lookChilds(rs.getInt(1), newServiceOrder);
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

	public void lookChilds(int MP_ID, X_AM_ServiceOrder serviceOrder)
	{

		String sql = "SELECT AM_Maintenance_ID FROM AM_Maintenance WHERE isChild='Y' and AM_MaintenanceParent_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, MP_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				createSOTaskDetail(rs.getInt(1), serviceOrder);
				lookChilds(rs.getInt(1), serviceOrder);
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

	public boolean createSOTaskDetail(int MP_ID, X_AM_ServiceOrder serviceOrder)
	{

		String sql = "SELECT * FROM AM_Maintenance_Task WHERE AM_Maintenance_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, MP_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				X_AM_ServiceOrder_Task ta = new X_AM_ServiceOrder_Task(Env.getCtx(), 0, null);
				ta.setAM_Maintenance_ID(MP_ID);
				ta.setAD_Org_ID(rs.getInt("AD_Org_ID"));
				ta.setAM_ServiceOrder_ID(serviceOrder.getAM_ServiceOrder_ID());
				ta.setDescription(rs.getString("Description"));
				ta.setDuration(rs.getBigDecimal("Duration").intValue());
				ta.setC_UOM_ID(rs.getInt("C_UOM_ID"));
				ta.setStatus(X_AM_ServiceOrder_Task.STATUS_NotStarted);
				ta.saveEx();

				createSOResourceDetail(rs.getInt("AM_Maintenance_Task_ID"), ta.getAM_ServiceOrder_Task_ID());
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

		// update MP
		updateMP(MP_ID, serviceOrder.getDateTrx(), serviceOrder.getDescription());

		return true;

	}

	public boolean createSOResourceDetail(int oldTask_ID, int newTask_ID)
	{

		String sql = "SELECT * FROM AM_Maintenance_Resource WHERE AM_Maintenance_Task_ID=?";
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

	public void updateMP(int MP_ID, Timestamp Datetrx, String description)
	{
		X_AM_Maintenance mp = new X_AM_Maintenance(Env.getCtx(), MP_ID, null);
		mp.setDateLastRunAM(Datetrx);
		mp.setDateLastRun(Datetrx);
		if (mp.getAM_ProgrammingType().equals("C"))
			mp.setDateNextRun(new Timestamp(Datetrx.getTime() + (mp.getInterval().longValue() * 86400000)));
		else
		{
			mp.setNextAM(mp.getInterval().add(mp.getLastAM()).setScale(2, BigDecimal.ROUND_HALF_EVEN));
			if (!Util.isEmpty(description, false))
				mp.setLastAM(new BigDecimal(Float.parseFloat(description.split(":")[description.split(":").length - 1]
						.replace(',', '.'))).setScale(2, BigDecimal.ROUND_HALF_EVEN));
		}
		mp.save(); // update MP
	}

	public ADForm getForm()
	{
		return form;
	}

} // VAllocation
