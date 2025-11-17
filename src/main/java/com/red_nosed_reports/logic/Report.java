package com.red_nosed_reports.logic;

import java.util.AbstractList;
import java.util.ArrayList;

/**
 * Represents a report (list of integers)
 */
public class Report {
    private AbstractList<Integer> levels;

    /**
     * Creates a new empty Report instance (containing no levels).
     *
     * @return A new Report instance
     */
    public Report() {
        this.levels = new ArrayList<Integer>();
    }

    /**
     * Adds `level` to the report.
     *
     * @param level The level to add to the report
     */
    public void addLevel(int level) {
        this.levels.add(level);
    }

    /**
     * Getter for the report's level list.
     *
     * @return the list of levels composing the report
     */
    public AbstractList<Integer> getLevels() {
        return this.levels;
    }

    /**
     * Determines if the report is safe.
     *
     * @return true if and only if the report is safe.
     */
    public boolean isSafe() {
        return this.areValuesMonotonous() && this.areIntervalCorrect();
    }

    /**
     * Checks the first condition for the report to be safe:
     * are the levels either all increasing or all decreasing?
     *
     * @return true if and only if the condition is verified
     */
    private boolean areValuesMonotonous() {
        boolean increasing = true;

        for (int k = 1; k < this.levels.size(); ++k) {
            int diff = this.levels.get(k - 1) - this.levels.get(k);

            if (k == 1) { // First pass in the loop, set the direction (increasing / decreasing)
                if (diff <= 0)
                    increasing = true;
                else
                    increasing = false;
            }
            else { // Check if the direction stays the same
                if (increasing && diff > 0)
                    return false;
                if (!increasing && diff < 0)
                    return false;
            }
        }

        return true;
    }

    /**
     * Checks the second condition for the report to be safe:
     * does any two adjacent levels differ by at least one and at most three?
     *
     * @return true if and only if the condition is verified
     */
    private boolean areIntervalCorrect() {
        for (int k = 1; k < this.levels.size(); ++k) {
            int diff = Math.abs(this.levels.get(k - 1) - this.levels.get(k));

            if (diff < 1 || 3 < diff)
                return false;
        }

        return true;
    }
}

