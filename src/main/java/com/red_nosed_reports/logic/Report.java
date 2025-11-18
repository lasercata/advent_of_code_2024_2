package com.red_nosed_reports.logic;

import java.util.Objects;
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
     * Creates a new Report instance.
     *
     * @return A new Report instance
     */
    public Report(AbstractList<Integer> levels) {
        this.levels = levels;
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
     * Removes the level at index `k`
     *
     * @param k The index at which to remove the level
     */
    public void removeLevel(int k) {
        this.levels.remove(k);
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
        return this.isSafe(true);
    }

    /**
     * Determines if the report is safe.
     * Defined to hide the parameter.
     *
     * @param allowSkip Set to true when calling, used to allow to skip at most one value (only one recurrence). This argument is necessary here to synchronise both sub-methods.
     * @return true if and only if the report is safe.
     */
    private boolean isSafe(boolean allowSkip) {
        // If there is only one level, do not skip it
        if (this.levels.size() == 1)
            allowSkip = false;

        // Proceed with the case where removing the first level makes it safe
        Report reportWithout0 = new Report(new ArrayList<>(this.levels));
        reportWithout0.removeLevel(0);

        if (allowSkip && reportWithout0.isSafe(false))
            return true;

        // Proceed to the specific checks
        return this.areValuesMonotonous(allowSkip) && this.areIntervalCorrect(allowSkip);
    }

    /**
     * Checks the first condition for the report to be safe:
     * are the levels either all increasing or all decreasing?
     * It is possible to skip one level (part2).
     *
     * @param allowSkip Set to true when calling, used to allow to skip at most one value (only one recurrence)
     * @return true if and only if the condition is verified
     */
    private boolean areValuesMonotonous(boolean allowSkip) {
        boolean increasing = true;

        for (int k = 1; k < this.levels.size(); ++k) {
            int diff = this.levels.get(k - 1) - this.levels.get(k);

            // First pass in the loop, set the direction (increasing / decreasing)
            if (k == 1) {
                increasing = (diff <= 0);
            }
            // Check if the direction stays the same
            else if ((increasing && diff > 0) || (!increasing && diff < 0)) {
                // If not, check if it can work by removing the wrong level (either k or k - 1)
                if (allowSkip) {
                    Report reportWithoutK = new Report(new ArrayList<>(this.levels));
                    reportWithoutK.removeLevel(k);

                    Report reportWithoutK1 = new Report(new ArrayList<>(this.levels));
                    reportWithoutK1.removeLevel(k - 1);

                    if (reportWithoutK.isSafe(false) || reportWithoutK1.isSafe(false))
                        return true;
                }

                return false;
            }
        }

        return true;
    }

    /**
     * Checks the second condition for the report to be safe:
     * does any two adjacent levels differ by at least one and at most three?
     * It is possible to skip one level (part2).
     *
     * @param allowSkip Set to true when calling, used to allow to skip at most one value (only one recurrence)
     * @return true if and only if the condition is verified
     */
    private boolean areIntervalCorrect(boolean allowSkip) {
        for (int k = 1; k < this.levels.size(); ++k) {
            int diff = Math.abs(this.levels.get(k - 1) - this.levels.get(k));

            if (diff < 1 || 3 < diff) {
                if (allowSkip) { // If the interval is not correct, check if it can work by removing the wrong level (either k or k - 1)
                    Report reportWithoutK = new Report(new ArrayList<>(this.levels));
                    reportWithoutK.removeLevel(k);

                    Report reportWithoutK1 = new Report(new ArrayList<>(this.levels));
                    reportWithoutK1.removeLevel(k - 1);

                    if (reportWithoutK.isSafe(false) || reportWithoutK1.isSafe(false))
                        return true;
                }

                return false;
            }
        }

        return true; // All intervals are correct
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;

        if (other == null || this.getClass() != other.getClass())
            return false;

        Report otherReport = (Report) other;
        
        return Objects.equals(this.levels, otherReport.levels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.levels);
    }

    @Override
    public String toString() {
        return this.levels.toString();
    }
}

