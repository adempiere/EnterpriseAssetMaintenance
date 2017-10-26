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
 * Copyright (C) 2003-2015 E.R.P. Consultores y Asociados, C.A.               *
 * All Rights Reserved.                                                       *
 * Contributor(s): Yamel Senih www.erpcya.com                                 *
 *****************************************************************************
 ***
 * Title:	Forecast for Maintenance
 * Description:
 *	Show all planning maintenance for a date
 *
 * Test:
 * 	SELECT A_Asset_ID, DocumentNo, DateNextRun, IsActive, Interval, Amt, Range, Average FROM RV_AM_Forecast ORDER BY A_Asset_ID, DateNextRun
 ************************************************************************/
-- DROP VIEW RV_AM_Forecast
CREATE OR REPLACE VIEW RV_AM_Forecast AS 
SELECT am.AM_Maintenance_ID, am.DocumentNo, am.Description, am.PriorityRule,   
a.A_Asset_ID, a.A_Asset_Group_ID, am.CalendarType, am.ProgrammingType, 
NULL::NUMERIC AS AM_ServiceRequest_ID, NULL::NUMERIC AS AM_RequestType_ID, am.AM_Pattern_ID,
am.Interval, 
am.Range, 
(CASE 
 	WHEN am.CalendarType = 'D' THEN adddays(am.DateNextRun, s)
	WHEN am.CalendarType = 'M' THEN add_months(am.DateNextRun, am.Interval)
 	WHEN am.CalendarType = 'R' THEN add_months(am.DateNextRun, s)
 	WHEN am.CalendarType = 'W' THEN addweeks(am.DateNextRun, s)
 	WHEN am.CalendarType = 'Y' THEN addyears(am.DateNextRun, s)
 ELSE NULL
END) AS DateNextRun,
0 AS Average,
0 Amt,
am.AD_Client_ID, am.AD_Org_ID, am.IsActive, am.Created, am.CreatedBy, am.Updated, am.UpdatedBy
FROM AM_Maintenance am
INNER JOIN A_Asset a ON(a.A_Asset_ID = am.A_Asset_ID OR a.A_Asset_Group_ID = am.A_Asset_Group_ID)
, generate_series(0, 2000) AS s
WHERE am.ProgrammingType = 'C'
AND am.DocStatus <> 'IT'
AND am.IsActive = 'Y'
AND (CASE 
 	-- For Daily
 	WHEN am.CalendarType = 'D' AND am.IsSunday = 'Y' AND extract(dow from am.DateNextRun + s) = 0 THEN 'Y'
	WHEN am.CalendarType = 'D' AND am.IsMonday = 'Y' AND extract(dow from am.DateNextRun + s) = 1 THEN 'Y'
 	WHEN am.CalendarType = 'D' AND am.IsTuesday = 'Y' AND extract(dow from am.DateNextRun + s) = 2 THEN 'Y'
 	WHEN am.CalendarType = 'D' AND am.IsWednesday = 'Y' AND extract(dow from am.DateNextRun + s) = 3 THEN 'Y'
 	WHEN am.CalendarType = 'D' AND am.IsThursday = 'Y' AND extract(dow from am.DateNextRun + s) = 4 THEN 'Y'
 	WHEN am.CalendarType = 'D' AND am.IsFriday = 'Y' AND extract(dow from am.DateNextRun + s) = 5 THEN 'Y'
 	WHEN am.CalendarType = 'D' AND am.IsSaturday = 'Y' AND extract(dow from am.DateNextRun + s) = 6 THEN 'Y'
 	-- For MonthlyFixed
 	WHEN am.CalendarType = 'M' THEN 'Y'
  	-- For Other
 	WHEN am.CalendarType IN('R', 'W', 'Y') AND mod(s, am.Interval) = 0 THEN 'Y'
 ELSE 'N'
END) = 'Y'
AND NOT EXISTS(SELECT 1 FROM AM_ServiceOrder so 
               WHERE so.AM_Maintenance_ID = am.AM_Maintenance_ID
               AND trunc(so.DateStartPlan, 'DY') = trunc(adddays(am.DateNextRun, s), 'DY') 
               AND so.DocStatus IN('IP', 'CO', 'CL'))
UNION ALL
SELECT am.AM_Maintenance_ID, am.DocumentNo, am.Description, am.PriorityRule,   
a.A_Asset_ID, a.A_Asset_Group_ID, am.CalendarType, am.ProgrammingType, 
NULL::NUMERIC AS AM_ServiceRequest_ID, NULL::NUMERIC AM_RequestType_ID, am.AM_Pattern_ID,
am.Interval, am.Range, 
adddays(am.DateNextRun, s) AS DateNextRun, 
mr.Average,
(mr.Amt + (mr.Average * s)) AS Amt,
am.AD_Client_ID, am.AD_Org_ID, am.IsActive, am.Created, am.CreatedBy, am.Updated, am.UpdatedBy
FROM AM_Maintenance am
INNER JOIN A_Asset a ON(a.A_Asset_ID = am.A_Asset_ID OR a.A_Asset_Group_ID = am.A_Asset_Group_ID)
INNER JOIN (SELECT m.AM_AssetMeter_ID, m.A_Asset_ID, m.Amt, 
            (m.Amt / 
             CASE 
             	WHEN daysbetween(MAX(ml.DateTrx), MIN(ml.DateTrx)) = 0 THEN 1 
             	ELSE daysbetween(MAX(ml.DateTrx), MIN(ml.DateTrx)) 
             END) Average
            FROM AM_AssetMeter m
           	INNER JOIN AM_AssetMeterLog ml ON(ml.AM_AssetMeter_ID = m.AM_AssetMeter_ID)
           GROUP BY m.AM_AssetMeter_ID, m.A_Asset_ID, m.Amt) AS mr ON(mr.A_Asset_ID = a.A_Asset_ID)
, generate_series(0, 2000) AS s
WHERE am.ProgrammingType = 'M'
AND am.DocStatus <> 'IT'
AND am.IsActive = 'Y'
AND (mr.Amt + (mr.Average * s)) 
	BETWEEN ((am.Interval * ROUND((mr.Amt + (mr.Average * s)) / CASE WHEN am.Interval = 0 THEN 1 ELSE am.Interval END, 0)) - am.Range)
      AND ((am.Interval * ROUND((mr.Amt + (mr.Average * s)) / CASE WHEN am.Interval = 0 THEN 1 ELSE am.Interval END, 0)) + am.Range)
AND NOT EXISTS(SELECT 1 FROM AM_ServiceOrder so 
               WHERE so.AM_Maintenance_ID = am.AM_Maintenance_ID
               AND trunc(so.DateStartPlan, 'DY') = trunc(adddays(am.DateNextRun, s), 'DY') 
               AND so.DocStatus IN('IP', 'CO', 'CL'))
UNION ALL
SELECT NULL AS AM_Maintenance_ID, sr.DocumentNo, sr.Description, sr.PriorityRule,   
sr.A_Asset_ID, a.A_Asset_Group_ID, NULL CalendarType, NULL ProgrammingType, 
sr.AM_ServiceRequest_ID, sr.AM_RequestType_ID, sr.AM_Pattern_ID,
0 AS Interval, 0 AS Range, 
sr.DateRequired AS DateNextRun, 
0 AS Average,
0 AS Amt,
sr.AD_Client_ID, sr.AD_Org_ID, sr.IsActive, sr.Created, sr.CreatedBy, sr.Updated, sr.UpdatedBy
FROM AM_ServiceRequest sr
INNER JOIN A_Asset a ON(a.A_Asset_ID = sr.A_Asset_ID)
WHERE sr.DocStatus = 'CO'
AND sr.IsActive = 'Y'
AND NOT EXISTS(SELECT 1 FROM AM_ServiceOrder so 
               WHERE so.AM_ServiceRequest_ID = sr.AM_ServiceRequest_ID 
               AND so.DocStatus IN('IP', 'CO', 'CL'))