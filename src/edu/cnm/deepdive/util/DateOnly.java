package edu.cnm.deepdive.util;

/**
 * This class contains a stub of the {@link #elapsedDays(int, int, int)} method.
 * Implementation of this method is included in the practical exam problems of
 * the Deep Dive Coding Java + Android Bootcamp.
 *
 * <p>For extra credit, values of the <code>month</code> and <code>day</code>
 * parameters outside the "natural" ranges must be allowed, with such values
 * normalized automatically. For example, a <code>month</code> value of 12
 * would be treated as <code>month</code> 0 (the first month) of the following
 * year; a <code>day</code> value of 0 would be treated as the last day of the
 * previous month; etc.</p>
 *
 * @author Nicholas Bennett, Deep Dive Coding
 */
public class DateOnly {


  private DateOnly() {
    // NOTE: There is NO need to do anything with this constructor! The method
    //       you will implement in this class are static; thus, there is no need
    //       to create instances of this class.
  }

  /**
   * Computes and returns the number of days between January 1, 1970 and the
   * specified date. The <a href="https://en.wikipedia.org/wiki/ISO_8601#Years"
   * target="_blank">ISO 8601 calendar</a> is assumed, and dates prior to 1583
   * (and even prior to 1 BCE) are supported.
   *
   * @param year  year number in the ISO 8601 calendar.
   * @param month month number, where 0=January, 1=February, etc.
   * @param day   day number.
   * @return      difference in days between specified date and January 1, 1970.
   *              If the specified date is before January 1, 1970, a negative
   *              value is returned.
   */
  public static int elapsedDays(int year, int month, int day) {
    return 0; // TODO Implement this method as specified in README.
  }

}
