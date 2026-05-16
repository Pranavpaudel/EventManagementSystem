package com.college.eventms.service;

import com.college.eventms.dao.ReportDAO;

import java.util.List;
import java.util.Map;

/**
 * Service layer for reporting — delegates report queries to ReportDAO.
 */
public class ReportService {

    private final ReportDAO reportDAO = new ReportDAO();

    /** Returns per-event attendance figures. */
    public List<Map<String, Object>> getAttendanceReport()  { return reportDAO.getAttendanceReport(); }

    /** Returns the count of events per category. */
    public List<Map<String, Object>> getCategoryBreakdown() { return reportDAO.getCategoryBreakdown(); }

    /** Returns system-wide summary statistics (users, events, bookings, pending). */
    public Map<String, Object> getSummaryStats()            { return reportDAO.getSummaryStats(); }
}
