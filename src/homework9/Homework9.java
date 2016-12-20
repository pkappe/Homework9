
package homework9;

import java.io.File;
import java.io.PrintWriter;
import java.util.Random;

/**
 * Write a Java program to calculate the monthly, quarterly, and annual bonuses
 * for a retail company that has 5 employees. Each month of the year, the
 * company logs a single integer for each employee, the total monthly sales for
 * that employee. Bonuses are awarded to deserving employees at the end of the
 * year.
 * <p>
 *
 * • The month-based bonus of $1000 is awarded to the employee(s) who have
 * increased sales at least 3 consecutive months in the year. <br>
 * • The quarter-based bonus of $2000 is awarded to the employee(s) who had the
 * most quarters with total sales better than the other employees. <br>
 * • The annual-based bonus of $3000 is awarded to the employee(s) who had the
 * best average monthly sales.
 * <p>
 *
 * You must read the 60 data points from a file and use a 2D primitive array to
 * store that sales data.
 *
 * @author Phil Kappe
 */
public class Homework9
{

    public static void main(String[] args)
    {

        populateSalesNumbers();
        Bonus bonus = new Bonus("empsales.txt");

        String[] employees =
        {
            "Joe", "John", "Bill", "Sue", "Mary"
        };
        bonus.addNames(employees);

        bonus.printEmployeeData();
        System.out.println();

        // Calculate all bonuses
        bonus.calculateMonthlyBonus();
        bonus.calculateQuarterBonus();
        bonus.calculateAnnualBonus();

        bonus.printBonusData();
        System.out.println();

        bonus.printBonusTotals();
        System.out.println();
    }

    /**
     * Automatically populates sales data in "empsales.txt" Items are placed in
     * file on a 5 row, 12 column format
     */
    private static void populateSalesNumbers()
    {
        try (PrintWriter write = new PrintWriter(new File("empsales.txt")))
        {
            Random rand = new Random();
            for (int i = 0; i < 5; i++)
            {
                for (int j = 0; j < 12; j++)
                {
                    int sale = rand.nextInt(5000);
                    write.print(sale + " ");
                }
                write.println();
            }
            write.close();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

}
