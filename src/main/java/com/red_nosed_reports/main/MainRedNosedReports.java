package com.red_nosed_reports.main;

import java.io.IOException;
import java.util.ArrayList;

import com.red_nosed_reports.logic.Report;
import com.red_nosed_reports.logic.ReportInput;

/**
 * This is the main class running the project.
 */
public class MainRedNosedReports {
    public static void main(String[] args) {
        // Get the file name
        String fileName;
        if (args.length == 0)
            fileName = "data/input.txt";
        else
            fileName = args[0];

        // Read the input file and convert it to a list of reports
        ReportInput input = new ReportInput(fileName);
        ArrayList<Report> reports;

        try {
            reports = input.toReportList();
        }
        catch (IOException e) {
            System.err.printf("Error while reading input file: '%s'\n", e.toString());
            return;
        }

        // Count the safe reports
        int safeReportsCount = 0;

        for (Report report : reports) {
            if (report.isSafe())
                safeReportsCount++;
        }

        System.out.printf("Number of safe reports: %d\n", safeReportsCount);
    }
}
