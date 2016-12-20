
package homework9;

import java.io.File;
import java.util.Scanner;

/**
 * This Bonus class utilizes data from a passed in file. The file has rows of
 * employee data. Each row has 12 columns. Each column is a month of the year.
 * For example: the 3rd column of data would be the employee sales totals for
 * the month of March.<p>
 *
 *
 * @author Phil Kappe
 */
public class Bonus
{

    private final int MONTHS; // Months in a year
    private final int EMPLOYEES; // Employees in company
    private final int BONUSES; // How many bonuses for each employee
    private final String[] names; // Names of employees
    private final int[][] sales; // Sales data for employees
    private final int[][] empBonus; // Employee bonus'

    /**
     * Constructor
     *
     * @param fileName The name of the file to use data for calculating bonuses.
     */
    public Bonus(String fileName)
    {
        MONTHS = 12;
        EMPLOYEES = 5;
        BONUSES = 3;
        names = new String[EMPLOYEES];
        sales = new int[EMPLOYEES][MONTHS];
        empBonus = new int[EMPLOYEES][BONUSES];

        try (Scanner scan = new Scanner(new File(fileName)))
        {
            for (int i = 0; i < 5; i++)
            {
                for (int j = 0; j < 12; j++)
                {
                    sales[i][j] = scan.nextInt();
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("ERROR: " + e);
        }
    }

    /**
     * Adds names to an array held in this class.
     *
     * @param addNames An array of names to store in this class
     */
    public void addNames(String[] addNames)
    {
        if (addNames.length <= EMPLOYEES)
        {
            System.arraycopy(addNames, 0, names, 0, EMPLOYEES);
        }
        else
        {
            System.out.println("Limit of 5 names exceeded. Please try again.");
        }

    }

    /**
     * Grabs the information from the empBonus array in this class. The bonuses
     * are held in a 2d array. The arrays rows are the amount of the employees
     * for the company, and the columns are each type of bonus.
     *
     * @return An array of all bonuses for each employee.
     */
    public int[] bonusTotals()
    {
        int[] tempArray = new int[EMPLOYEES];
        for (int i = 0; i < EMPLOYEES; i++)
        {
            int totalBonus = 0;
            for (int j = 0; j < BONUSES; j++)
            {
                totalBonus += empBonus[i][j];
            }
            tempArray[i] = totalBonus;
        }
        return tempArray;
    }

    /**
     * The annual-based bonus of $3000 is awarded to the employee(s) who had the
     * best average monthly sales.
     */
    public void calculateAnnualBonus()
    {
        // Find the monthly averages for each employee
        double[] annualAvg = new double[EMPLOYEES];
        for (int i = 0; i < EMPLOYEES; i++)
        {
            double totalSales = 0;
            for (int j = 0; j < MONTHS; j++)
            {
                totalSales += sales[i][j];
            }
            annualAvg[i] = totalSales / MONTHS;
        }

        // Set the highest average
        double highestAverage = annualAvg[0];
        for (int i = 1; i < annualAvg.length; i++)
        {
            if (highestAverage < annualAvg[i])
            {
                highestAverage = annualAvg[i];
            }
        }

        // Add 3000 to the bonus array for the employee with the highest 
        // monthly average.
        for (int i = 0; i < annualAvg.length; i++)
        {
            if (annualAvg[i] == highestAverage)
            {
                empBonus[i][2] = 3000;
            }
        }

    }

    /**
     * The month-based bonus of $1000 is awarded to the employee(s) who have
     * increased sales at least 3 consecutive months in the year.
     */
    public void calculateMonthlyBonus()
    {
        if (sales.length > 0)
        {
            for (int i = 0; i < EMPLOYEES; i++)
            {
                int previousMonth = sales[i][0];
                int consecutiveCount = 0;
                for (int j = 1; j < MONTHS; j++)
                {
                    if (previousMonth < sales[i][j])
                    {
                        consecutiveCount++;
                        previousMonth = sales[i][j];
                    }
                    else
                    {
                        if (consecutiveCount >= 3)
                        {
                            empBonus[i][0] = 1000;
                        }
                        consecutiveCount = 0;
                        previousMonth = sales[i][0];

                    }
                }
            }
        }
    }

    /**
     * The quarter-based bonus of $2000 is awarded to the employee(s) who had
     * the most quarters with total sales better than the other employees.
     */
    public void calculateQuarterBonus()
    {
        int[][] quarterSales = new int[EMPLOYEES][4]; // Each quarter sales

        if (sales.length > 0)
        {
            // Populate quarterSales from sales array by adding 3 months
            for (int i = 0; i < EMPLOYEES; i++)
            {
                int tempSales = 0;
                int monthCount = 0;
                int quarterCount = 0;
                for (int j = 0; j < MONTHS; j++)
                {
                    tempSales += sales[i][j];
                    monthCount++;
                    if (monthCount == 3)
                    {
                        quarterSales[i][quarterCount] = tempSales;
                        tempSales = 0;
                        monthCount = 0;
                        quarterCount++;
                    }
                }
            }

            // Find which employee has the best sales per quarter
            int[] mostQuarters = new int[EMPLOYEES]; //Counter for most quarters
            for (int i = 0; i < 4; i++)
            {
                int highestValue = 0;
                int highestIndex = 0;
                for (int j = 0; j < EMPLOYEES; j++)
                {
                    if (quarterSales[j][i] > highestValue)
                    {
                        highestValue = quarterSales[j][i];
                        highestIndex = j;
                    }
                }
                mostQuarters[highestIndex]++;
            }

            // Find the highest winning quarter count
            int highestQuarterCount = 0;
            for (int counts : mostQuarters)
            {
                if (counts > highestQuarterCount)
                {
                    highestQuarterCount = counts;
                }
            }

            // Use the highest winning quarter count to add 2000 into bonus for
            // the employee corresponding with the current index.
            for (int i = 0; i < mostQuarters.length; i++)
            {
                if (mostQuarters[i] == highestQuarterCount)
                {
                    empBonus[i][1] = 2000;
                }
            }
        }
    }

    /**
     * Get all employee names from this class as an array
     *
     * @return An Array of all employees for company
     */
    public String[] getNames()
    {
        return names;
    }

    /**
     * Prints all the employees bonuses.
     */
    public void printBonusData()
    {
        if (empBonus.length > 0)
        {
            System.out.printf("%-8s%8s\t%8s\t%8s", "NAME", "MONTHLY",
                    "QUARTERLY", "ANNUAL");
            System.out.println();

            for (int i = 0; i < 50; i++)
            {
                System.out.print("-");
            }
            System.out.println();

            for (int i = 0; i < EMPLOYEES; i++)
            {
                System.out.printf("%-8s", names[i]);
                for (int j = 0; j < BONUSES; j++)
                {
                    System.out.printf("$%5s", empBonus[i][j]);
                    System.out.print(".00\t");
                }
                System.out.println();
            }
        }
        else
        {
            System.out.println("NO DATA...");
        }
    }

    /**
     * Prints the current bonus totals in the following table format:
     * <p>
     * NAME &nbsp;&nbsp;&nbsp; BONUS
     *
     */
    public void printBonusTotals()
    {
        int[] bonuses = bonusTotals();
        System.out.printf("%-7s\t%6s", "NAME", "BONUS");
        System.out.println();
        System.out.println("-----------------");
        for (int i = 0; i < names.length; i++)
        {
            System.out.printf("%-8s", names[i]);
            System.out.printf("$%8s", bonuses[i] + ".00");
            System.out.println();
        }
    }

    /**
     * Print out the employee data that exists in this class to the console.
     */
    public void printEmployeeData()
    {
        if (names.length > 0 && sales.length > 0)
        {
            System.out.printf("%-8s%-4s\t%-4s\t%4s\t%-4s\t%-4s\t%-4s\t%-4s\t"
                    + "%-4s\t%-4s\t%-4s\t%-4s\t%-4s", "NAME", "JAN", "FEB",
                    "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT",
                    "NOV", "DEC");
            System.out.println();
            for (int i = 0; i < EMPLOYEES; i++)
            {
                System.out.printf("%-8s", names[i]);
                for (int j = 0; j < MONTHS; j++)
                {
                    System.out.printf("%4s\t", sales[i][j]);
                }
                System.out.println();
            }
        }
        else
        {
            System.out.println("NO DATA...");
        }

    }

}
