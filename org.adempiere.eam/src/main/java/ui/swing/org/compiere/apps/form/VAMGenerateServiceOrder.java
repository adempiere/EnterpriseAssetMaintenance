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
package org.compiere.apps.form;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.VetoableChangeListener;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Vector;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.adempiere.plaf.AdempierePLAF;
import org.compiere.apps.StatusBar;
import org.compiere.grid.ed.VDate;
import org.compiere.grid.ed.VLookup;
import org.compiere.minigrid.MiniTable;
import org.compiere.model.MColumn;
import org.compiere.model.MDocType;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MMaintenance;
import org.compiere.model.X_AM_Maintenance;
import org.compiere.model.X_AM_Prognosis;
import org.compiere.model.X_AM_ServiceOrder;
import org.compiere.model.X_AM_ServiceOrder_Resource;
import org.compiere.model.X_AM_ServiceOrder_Task;
import org.compiere.model.X_A_Asset;
import org.compiere.plaf.CompiereColor;
import org.compiere.process.ProcessInfo;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTextField;
import org.compiere.util.ASyncProcess;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.compiere.util.Util;

/**
 * @author OFB Consulting http://www.ofbconsulting.com
 * 	<li> Initial Contributor
 * @contributor Mario Calderon, Systemhaus Westfalia, http://www.westfalia-it.com
 * @contributor Adaxa http://www.adaxa.com
 * @contributor Deepak Pansheriya, Loglite Technologies, http://logilite.com
 * @contributor Victor Perez, victor.perez@e-evolution.com, eEvolution http://www.e-evolution.com
 * @contributor Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 */
public class VAMGenerateServiceOrder extends CPanel implements FormPanel, ActionListener, TableModelListener,
		VetoableChangeListener, ASyncProcess
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -4548392501402718230L;

	/** Logger */
	private static CLogger		log					= CLogger.getCLogger(VAMGenerateServiceOrder.class);

	/** Window No */
	private int					m_WindowNo			= 0;
	/** FormFrame */
	private FormFrame			m_frame;

	private boolean				m_calculating		= false;
	private int					m_C_Currency_ID		= 0;

	private CPanel				mainPanel			= new CPanel();
	private CPanel				parameterPanel		= new CPanel();
	private GridBagLayout		parameterLayout		= new GridBagLayout();
	private BorderLayout		mainLayout			= new BorderLayout();
	private CPanel				allocationPanel		= new CPanel();
	private MiniTable			prognosisTable		= new MiniTable();
	private CPanel				infoPanel			= new CPanel();
	private int					m_A_Asset_ID		= 0;

	private CPanel				prognosisPanel		= new CPanel();
	private CPanel				selectedPanel		= new CPanel();
	private JLabel				prognosisLabel		= new JLabel();
	private JLabel				selectedLabel		= new JLabel();
	private BorderLayout		prognosisLayout		= new BorderLayout();
	private BorderLayout		selectedLayout		= new BorderLayout();
	private JLabel				prognosisInfo		= new JLabel();
	private JScrollPane			prognosisScrollPane	= new JScrollPane();
	private GridBagLayout		allocationLayout	= new GridBagLayout();
	private JLabel				differenceLabel		= new JLabel();
	private CTextField			differenceField		= new CTextField();
	private JButton				ProcessButton		= new JButton();
	private JButton				searchButton		= new JButton();
	private JButton				addButton			= new JButton();
	private JButton				EnableButton		= new JButton();
	private JButton				ChangeButton		= new JButton();
	private JButton				DateFilterButton	= new JButton();

	private JLabel				assetLabel			= new JLabel();
	private JCheckBox			selectall			= new JCheckBox();
	private JLabel				allocCurrencyLabel	= new JLabel();
	private StatusBar			statusBar			= new StatusBar();
	private JLabel				dateLabel			= new JLabel();
	private VDate				dateField			= new VDate();
	private JLabel				dateToLabel			= new JLabel();

	private VDate				dateToField			= new VDate();

	private VLookup				assetSearch			= null;

	private JLabel				maintAreaLabel		= new JLabel();
	private VLookup				maintAreaSearch		= null;
	private boolean				dateFilter			= false;

	public void init(int WindowNo, FormFrame frame)
	{
		m_WindowNo = WindowNo;
		m_frame = frame;
		Env.setContext(Env.getCtx(), m_WindowNo, "IsSOTrx", "Y");

		m_C_Currency_ID = Env.getContextAsInt(Env.getCtx(), "$C_Currency_ID");

		log.info("Currency=" + m_C_Currency_ID);
		try
		{
			dynInit();
			jbInit();

			frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
			frame.getContentPane().add(statusBar, BorderLayout.SOUTH);

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
	private void jbInit() throws Exception
	{
		CompiereColor.setBackground(this);
		mainPanel.setLayout(mainLayout);

		dateLabel.setText(Msg.getMsg(Env.getCtx(), "Date"));
		dateLabel.setToolTipText(Msg.getMsg(Env.getCtx(), "AllocDate", false));
		dateToLabel.setText(Msg.getMsg(Env.getCtx(), "Date of Transaction"));
		dateToLabel.setToolTipText(Msg.getMsg(Env.getCtx(), "AllocDate", false));

		parameterPanel.setLayout(parameterLayout);
		allocationPanel.setLayout(allocationLayout);
		prognosisLabel.setRequestFocusEnabled(false);
		prognosisLabel.setText("Predicted maintenance");
		selectedLabel.setRequestFocusEnabled(false);
		selectedLabel.setText("selected");
		prognosisPanel.setLayout(prognosisLayout);
		selectedPanel.setLayout(selectedLayout);
		/*
		 * invoiceInfo.setHorizontalAlignment(SwingConstants.RIGHT);
		 * invoiceInfo.setHorizontalTextPosition(SwingConstants.RIGHT);
		 * invoiceInfo.setText(".");
		 */
		prognosisInfo.setHorizontalAlignment(SwingConstants.RIGHT);
		prognosisInfo.setHorizontalTextPosition(SwingConstants.RIGHT);
		prognosisInfo.setText(".");
		differenceLabel.setText(Msg.getMsg(Env.getCtx(), "Difference"));
		differenceField.setBackground(AdempierePLAF.getFieldBackground_Inactive());
		differenceField.setEditable(false);
		differenceField.setText("0");
		differenceField.setColumns(8);
		differenceField.setHorizontalAlignment(SwingConstants.RIGHT);
		ProcessButton.setText("Generar Service Order");
		ProcessButton.addActionListener(this);
		searchButton.setText("To find");
		searchButton.addActionListener(this);
		addButton.setText("Add selection");
		addButton.addActionListener(this);
		EnableButton.setText("Enable editing");
		EnableButton.addActionListener(this);
		ChangeButton.setText("Apply date");
		ChangeButton.addActionListener(this);
		DateFilterButton.setText("Filter by date");
		DateFilterButton.addActionListener(this);

		assetLabel.setText(Msg.translate(Env.getCtx(), "A_Asset_ID"));
		maintAreaLabel.setText(Msg.translate(Env.getCtx(), "Maintenance Area"));
		selectall.setText(Msg.getMsg(Env.getCtx(), "Select all"));
		selectall.addActionListener(this);
		allocCurrencyLabel.setText(".");
		prognosisScrollPane.setPreferredSize(new Dimension(300, 300));

		mainPanel.add(parameterPanel, BorderLayout.NORTH);
		parameterPanel.add(dateLabel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		parameterPanel.add(dateField, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
		parameterPanel.add(dateToLabel, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		parameterPanel.add(dateToField, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
		parameterPanel.add(DateFilterButton, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

		parameterPanel.add(selectall, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		parameterPanel.add(ChangeButton, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		parameterPanel.add(assetLabel, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		parameterPanel.add(assetSearch, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

		parameterPanel.add(maintAreaLabel, new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		parameterPanel.add(maintAreaSearch, new GridBagConstraints(6, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

		mainPanel.add(allocationPanel, BorderLayout.SOUTH);
		allocationPanel.add(ProcessButton, new GridBagConstraints(6, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));

		prognosisPanel.add(prognosisLabel, BorderLayout.NORTH);
		prognosisPanel.add(prognosisInfo, BorderLayout.SOUTH);
		prognosisPanel.add(prognosisScrollPane, BorderLayout.CENTER);
		prognosisScrollPane.getViewport().add(prognosisTable, null);
		mainPanel.add(infoPanel, BorderLayout.CENTER);

		infoPanel.setLayout(new BorderLayout());

		infoPanel.add(prognosisPanel, BorderLayout.CENTER);
		infoPanel.setPreferredSize(new Dimension(1000, 450));

	} // jbInit

	/**
	 * Dispose
	 */
	public void dispose()
	{
		if (m_frame != null)
			m_frame.dispose();
		m_frame = null;
	} // dispose

	/**
	 * Dynamic Init (prepare dynamic fields)
	 * 
	 * @throws Exception if Lookups cannot be initialized
	 */
	private void dynInit() throws Exception
	{

		// Asset
		int AD_Column_ID = MColumn.getColumn_ID("A_Asset", "A_Asset_ID");
		MLookup lookupBP = MLookupFactory.get(Env.getCtx(), m_WindowNo, 0, AD_Column_ID, DisplayType.Search);
		assetSearch = new VLookup("A_Asset_ID", true, false, true, lookupBP);
		assetSearch.addVetoableChangeListener(this);

		int maintainAreaColumn_ID = MColumn.getColumn_ID("AM_MaintenancePattern", "MaintenanceArea");
		MLookup lookupAreaMtto = MLookupFactory.get(Env.getCtx(), m_WindowNo, 0, maintainAreaColumn_ID,
				DisplayType.List);
		maintAreaSearch = new VLookup("MaintenanceArea", true, false, true, lookupAreaMtto);
		maintAreaSearch.addVetoableChangeListener(this);

		dateField.setValue(Env.getContextAsDate(Env.getCtx(), "#Date"));
		dateToField.setValue(Env.getContextAsDate(Env.getCtx(), "#Date"));
		statusBar
				.setStatusLine("Select the maintenance., change the predicted date, or directly generate Service Order ");
		statusBar.setStatusDB("");
		loadMPs();

	} // dynInit

	/**
	 * Load Business Partner Info - Payments - Invoices
	 */
	private void loadMPs()
	{
		/********************************
		 * Load Prognosis table 1-TrxDate, 2-DocumentNo, (3-Currency, 4-PayAmt,)
		 * 5-ConvAmt, 6-ConvOpen, 7-Allocated
		 */
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		StringBuffer sql = new StringBuffer("SELECT p.AM_Prognosis_ID,p.AD_CLIENT_ID,p.AD_ORG_ID,"
				+ "p.A_ASSET_ID,p.AM_Maintenance_ID,p.DESCRIPTION,p.AM_ProgrammingType,p.Frequency,p.DATETRX,"
				+ "mp.DOCUMENTNO||'-'||mp.Description as MP_name,a.name as assetname, AM_Prognosis_ID  "
				+ " from AM_Prognosis p" + " Inner Join AM_Maintenance mp on (p.AM_Maintenance_ID=mp.AM_Maintenance_ID)"
				+ " Inner Join A_Asset a on (p.A_ASSET_ID=a.A_ASSET_ID)"
				+ " where p.Processed='N' and p.IsSelected='N' and p.isactive='Y' ");

		if (assetSearch != null && assetSearch.getValue() != null)
			sql.append(" and p.A_ASSET_ID=" + assetSearch.getValue());

		if (maintAreaSearch != null && maintAreaSearch.getValue() != null)
			sql.append(" and mp.MaintenanceArea= '" + maintAreaSearch.getValue() + "'");

		if (dateFilter)
			sql.append(" and (p.DATETRX >= '" + dateField.getValue() + "' and p.DATETRX <= '" + dateToField.getValue()
					+ "')");

		sql.append(" order by p.DATETRX asc");
		log.config("Forecast=" + sql.toString());

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
				line.add(pp); // 3-Maintenance
				KeyNamePair pp2 = new KeyNamePair(rs.getInt(5), rs.getString(10));
				line.add(pp2); // 4-mp
				line.add(rs.getString(7)); // 5-programing type
				line.add(rs.getString(6)); // 6-description
				KeyNamePair forecast = new KeyNamePair(rs.getInt(12), "Forecast"); // 7-mp_prognosis_id

				line.add(forecast);
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

		prognosisTable.getModel().removeTableModelListener(this);

		// Header Info
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("Generar Service Order");
		columnNames.add(Msg.getMsg(Env.getCtx(), "Week"));
		columnNames.add(Msg.translate(Env.getCtx(), "Date"));
		columnNames.add(Util.cleanAmp(Msg.translate(Env.getCtx(), "Asset")));
		columnNames.add(Msg.getMsg(Env.getCtx(), "MP"));
		columnNames.add(Msg.getMsg(Env.getCtx(), "Programming Type"));
		columnNames.add(Msg.getMsg(Env.getCtx(), "Description"));
		columnNames.add(Msg.getMsg(Env.getCtx(), "Forecast"));

		// Set Model
		DefaultTableModel modelP = new DefaultTableModel(data, columnNames);
		modelP.addTableModelListener(this);
		prognosisTable.setModel(modelP);
		int i = 0;
		prognosisTable.setColumnClass(i++, Boolean.class, false); // 0-Selection
		prognosisTable.setColumnClass(i++, Integer.class, true); // 2-Frequency
		prognosisTable.setColumnClass(i++, Timestamp.class, true); // 3-TrxDate
		prognosisTable.setColumnClass(i++, String.class, true); // 4-asset
		prognosisTable.setColumnClass(i++, String.class, true); // 5-MP
		prognosisTable.setColumnClass(i++, String.class, true); // 6-
																// ProgramingType
		prognosisTable.setColumnClass(i++, String.class, true); // 7-Description
		prognosisTable.setColumnClass(i++, String.class, true); // 8-forecast

		prognosisTable.autoSize();

		prognosisTable.setColumnReadOnly(1, false);

		calculate();
	} // loadBPartner

	/**************************************************************************
	 * Action Listener. - MultiCurrency - Allocate
	 * 
	 * @param e event
	 */
	public void actionPerformed(ActionEvent e)
	{
		log.config("");
		if (e.getSource().equals(ProcessButton))
		{
			saveData();
		}
		else if (e.getSource().equals(EnableButton))
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
		else if (e.getSource().equals(ChangeButton))
		{
			ProcessButton.setEnabled(true);
			// updating rows selected
			TableModel prognosis = prognosisTable.getModel();
			int rows = prognosis.getRowCount();
			Timestamp DateTrx = (Timestamp) dateField.getValue();
			for (int i = 0; i < rows; i++)
			{
				if (((Boolean) prognosis.getValueAt(i, 0)).booleanValue())
				{

					prognosis.setValueAt(DateTrx, i, 2);

				}
			}
		}
		else if (e.getSource().equals(selectall))
		{
			TableModel prognosis = prognosisTable.getModel();
			int rows = prognosis.getRowCount();
			for (int i = 0; i < rows; i++)
			{
				if (m_A_Asset_ID == 0)
					prognosis.setValueAt(selectall.isSelected(), i, 0);
				else
				{
					KeyNamePair pp = (KeyNamePair) prognosis.getValueAt(i, 3);// Asset
					int Asset_ID = pp.getKey();
					if (Asset_ID == m_A_Asset_ID)
						prognosis.setValueAt(selectall.isSelected(), i, 0);
				}

			}
		}
		else if (e.getSource().equals(DateFilterButton))
		{
			DateFilterButton.setEnabled(true);
			dateFilter = true;
			loadMPs();
			dateFilter = false;
		}
	} // actionPerformed

	/**
	 * Table Model Listener. - Recalculate Totals
	 * 
	 * @param e event
	 */
	public void tableChanged(TableModelEvent e)
	{
		boolean isUpdate = (e.getType() == TableModelEvent.UPDATE);

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
	 * Vetoable Change Listener. - Business Partner - Currency - Date
	 * 
	 * @param e event
	 */
	public void vetoableChange(PropertyChangeEvent e)
	{
		String name = e.getPropertyName();
		Object value = e.getNewValue();
		log.config(name + "=" + value);

		if (name.equals("A_Asset_ID") && value != null)
		{
			assetSearch.setValue(value);
			m_A_Asset_ID = ((Integer) value).intValue();
		}
		else if (name.equals("MaintainArea") && value != null)
		{
			// Nothing needed yet
			;
		}
		else
		{
			assetSearch.setValue(null);
			m_A_Asset_ID = 0;
		}
		loadMPs();

	} // vetoableChange

	/**************************************************************************
	 * Save Data
	 */
	private void saveData()
	{

		TableModel prognosis = prognosisTable.getModel();
		int rows = prognosis.getRowCount();

		for (int i = 0; i < rows; i++)
		{
			if (((Boolean) prognosis.getValueAt(i, 0)).booleanValue())
			{
				KeyNamePair pp = (KeyNamePair) prognosis.getValueAt(i, 4); // MP
				int mp_maintain_id = pp.getKey();
				pp = (KeyNamePair) prognosis.getValueAt(i, 3); // Asset
				int Asset_ID = pp.getKey();
				Timestamp now = (Timestamp) prognosis.getValueAt(i, 2); // Date
				String description = (String) prognosis.getValueAt(i, 6); // Description
				int ciclo = ((Integer) prognosis.getValueAt(i, 1)).intValue(); // Frequency
				String ProgramingType = (String) prognosis.getValueAt(i, 5); // Programing
				pp = (KeyNamePair) prognosis.getValueAt(i, 7); // mp_prognosis_id
				int mp_prognosis_id = pp.getKey();

				X_AM_ServiceOrder createdSO = createSO(mp_maintain_id, now, description, Asset_ID);
				if (createdSO == null)
				{
					log.log(Level.SEVERE, "Service Order not created #" + i + ". ID of the Prognosis= "
							+ mp_prognosis_id);
					continue;
				}

				updateMP(mp_maintain_id, now, description);

				X_AM_Prognosis forecast = new X_AM_Prognosis(Env.getCtx(), mp_prognosis_id, null);
				forecast.setIsActive(false);
				forecast.setProcessed(true);

				if (ProgramingType.equals("C"))
				{
					X_A_Asset ass = new X_A_Asset(Env.getCtx(), Asset_ID, null);
					if (ass.getNextMaintenenceDate() == null || now.before(ass.getNextMaintenenceDate()))
						ass.setNextMaintenenceDate(now);
					ass.saveEx();

					forecast.setNextMaintenenceDate(now);
					forecast.setAM_ServiceOrder_ID(createdSO.getAM_ServiceOrder_ID());
				}
				forecast.saveEx();
			}
		}
		loadMPs();
		statusBar.setStatusLine(rows + " Forecasts were processed");
	} // saveData

	public X_AM_ServiceOrder createSO(int MP_ID, Timestamp Datetrx, String description, int Asset_ID)
	{

		X_AM_ServiceOrder newOT = new X_AM_ServiceOrder(Env.getCtx(), 0, null);
		newOT.setDateStartPlan(Datetrx);
		newOT.setDescription(description);
		newOT.setA_Asset_ID(Asset_ID);
		newOT.setAM_Maintenance_ID(MP_ID);
		newOT.setDocStatus("DR");
		newOT.setDocAction("IP");
		newOT.setC_DocType_ID(MDocType.getOfDocBaseType(Env.getCtx(), "WKO")[0].getC_DocType_ID());

		MMaintenance maintain = new MMaintenance(Env.getCtx(), MP_ID, null);
		newOT.setCostAmtPlan(maintain.getCostAmt());
		newOT.setCostAmt(maintain.getCostAmt());
		newOT.setMaintenanceArea(maintain.getMaintenanceArea());
		newOT.setAM_MaintenancePattern_ID(maintain.getAM_MaintenancePattern_ID());
		newOT.setAD_User_ID(maintain.getAD_User_ID());
		newOT.setDescription(maintain.getDescription() + " <BR> " + description);
		newOT.saveEx();

		if (!createSOTaskDetail(MP_ID, newOT))
			return null;

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
				createSOTaskDetail(rs.getInt(1), newOT);
				lookChilds(rs.getInt(1), newOT);
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

		return newOT;
	}

	public void lookChilds(int MP_ID, X_AM_ServiceOrder OT)
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
				createSOTaskDetail(rs.getInt(1), OT);
				lookChilds(rs.getInt(1), OT);
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

	public boolean createSOTaskDetail(int MP_ID, X_AM_ServiceOrder OT)
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
				ta.setAM_ServiceOrder_ID(OT.getAM_ServiceOrder_ID());
				ta.setDescription(rs.getString("Description"));
				ta.setDuration(rs.getBigDecimal("Duration").intValue());
				ta.setC_UOM_ID(rs.getInt("C_UOM_ID"));
				ta.setStatus(X_AM_ServiceOrder_Task.STATUS_NotStarted);
				ta.setCostAmtPlan(rs.getBigDecimal("CostAmt")); // in SO
																// werden die
																// Kosten zu
																// Plan
				ta.setCostAmt(rs.getBigDecimal("CostAmt")); 
				ta.setName(rs.getString("Name"));
				ta.setLine(rs.getInt("Line")); 
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
				re.setCostAmtPlan(rs.getBigDecimal("CostAmt"));
				re.setQtyPlan(rs.getBigDecimal("RESOURCEQTY"));
				re.setC_UOM_ID(rs.getInt("C_UOM_ID"));
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

	public void updateMP(int mp_maintain_id, Timestamp Datetrx, String description)
	{
		X_AM_Maintenance mp = new X_AM_Maintenance(Env.getCtx(), mp_maintain_id, null);

		if (mp.getAM_ProgrammingType().equals("M"))
		{
			mp.setDateLastRunAM(Datetrx);
			mp.setDateLastRun(Datetrx);
			mp.setLastAM(mp.getNextAM());
			mp.setNextAM(mp.getNextAM().add(mp.getInterval()).setScale(2, BigDecimal.ROUND_HALF_EVEN));

			mp.saveEx();
		}
	}

	/**************************************************************************
	 * Lock User Interface. Called from the Worker before processing
	 * 
	 * @param pi process info
	 */
	public void lockUI(ProcessInfo pi)
	{
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		this.setEnabled(false);
	} // lockUI

	/**
	 * Unlock User Interface. Called from the Worker when processing is done
	 * 
	 * @param pi result of execute ASync call
	 */
	public void unlockUI(ProcessInfo pi)
	{
		this.setEnabled(true);
		this.setCursor(Cursor.getDefaultCursor());

	} // unlockUI

	/**
	 * Is the UI locked (Internal method)
	 * 
	 * @return true, if UI is locked
	 */
	public boolean isUILocked()
	{
		return this.isEnabled();
	} // isUILocked

	/**
	 * Method to be executed async. Called from the Worker
	 * 
	 * @param pi ProcessInfo
	 */
	public void executeASync(ProcessInfo pi)
	{
	} // executeASync

} // VAllocation
