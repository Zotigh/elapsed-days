## Value

* Basic implementation: 15 points
* Basic unit tests: 5 points
* Extra credit implementation: 10 points
* Extra credit unit tests: 5 points

## Basic task

One useful date-only operation is the calculation of the difference in days between some agreed-upon starting date and a specified ending date; this is your task here.

### Background

In some computational tasks, we need to manipulate dates&mdash;but just dates, without time components, and without any reference to a time zone.

For example, assume we have an Android app that displays the NASA Astronomy Picture of the Day (APOD). If we want to store retrieved APOD objects in a local database on the device, the only date or time information we receive in each such object from the NASA APOD service is the date (with no time) for which the referenced image was the picture of the day. But if we deserialize the dates received from NASA to instances of `java.util.Date`, then there _will be_ a time in each: 00:00:00.000, in the device's current time zone. If we store those APOD objects in a SQLite database, using a `@TypeConverter` method to convert each `java.util.Date` instance to its most direct numeric value (namely, the number of milliseconds elapsed since 1970-01-01 00:00:00.000 UTC to the specified date-time value), and we later search the database for the APOD object for some given day, a change in the device's configured time zone could result in database "misses"&mdash;i.e. queries that should return an APOD object, but don't. 

We could avoid the issue described above if we deserialized the dates received from NASA to some type that had no time information at all (not even time zone), converted that date-only information to one of the SQLite data types for storage in the local database, and executed queries using criteria expressed with the same sort of date-only values.

The `java.time` package (added in Java 8) includes the `LocalDate` class for date-only computations; but for Android development, the use of `java.time` requires API 26 or higher, which may be too restrictive for some projects. There are 3<sup>rd</sup>-party libraries, such as [Joda-Time](https://www.joda.org/joda-time/), that can be used when `java.time` isn't an option. However, such a library might be overkill for simple applications.

While the problem you'll be solving here is much more simple than the example, a successful implementation will include key elements that could be incorporated into such real-world applications.

### Implementation 

Complete the `edu.cnm.deepdive.util.DateOnly.toEpochDay` method, declared as:

```java
int toEpochDay(int year, int month, int day)
```

For more method declaration details, see the [Javadoc documentation](docs/api/edu/cnm/deepdive/util/DateOnly.html#toEpochDay-int-int-int-).

For any [ISO 8601](https://en.wikipedia.org/wiki/ISO_8601#Years)-based date value in the range from (roughly) 5,880,000 BCE to 5,880,000 CE, your implementation should return the difference (in days) between January 1, 1970 and the specified date. Dates after January 1, 1970 will result in positive differences, while dates before will result in negative differences.

### Historical calendars vs. ISO 8601

The Gregorian calendar we use has only been around for the last 440 years or so&mdash;much less than that in many countries (including the US). Before that, the Julian calendar (dating from 45 BCE, with the quadrennial leap year rule formalized in 4 CE) was used in most of the European-dominated regions of the world. The Julian calendar had the same months as the Gregorian calendar, but used a leap year rule that resulted in the accumulation of an excess of ~10 leap days over 1500 years, motivating the changeover to the Gregorian calendar. Rather than deal with that changeover&mdash;and with the lack of a year 0 in either the Julian or the proleptic Gregorian calendars&mdash;we'll be following the [ISO 8601 standard](https://en.wikipedia.org/wiki/ISO_8601#Years), and providing support not only for years prior to 1583, but years prior to 1 CE (or AD) as well. (The year traditionally referred to as 1 BCE, or 1 BC, is denoted as year 0 of the ISO 8601 calendar.)

Thus, while a date preceding the advent of the Gregorian calendar (and even the Julian calendar) may be specified as the `year`, `month`, and `day` arguments to the `toEpochDay` method, those values will not match the historic year, month, and day denoting the same day on the calendar(s) actually in use then. (They will also differ from the `java.util.Calendar`-based values, since `java.util.GregorianCalendar`, the concrete extension of `java.util.Calendar`, uses the Julian calendar for dates prior to October 15, 1582.)

### Assumptions

* The `year` value will be a positive or negative `int` in the range stated in ["Implementation"](#implementation), above. 

* The `month` value will be in the range 0&hellip;11. This is in keeping with the convention followed in most C-derived languages and libraries, where months are numbered starting from 0.

* The `day` value will be in the range from 1 to the last day of `month` in `year`.

* Together, the 3 arguments above will specify a date falling in the range beginning 2<sup>31</sup> days before January 1, 1970, and ending (2<sup>31</sup> - 1) days after that date. Your implementation is not required to behave in any predictable fashion for dates outside that range.

### Restrictions

* To enable the use of the `toEpochDay` method in Android development projects, only Java 8 (or lower) syntax and standard library classes should be used.

* To avoid explicit or implicit dependence on classes which incorporate time (in addition to date), or on classes not yet available on the majority of Android devices, implementations should not use the `java.util.Date` or `java.util.Calendar` classes (or any subclass of either), nor any classes in the `java.time` package.

* Your implementation should not depend on any 3<sup>rd</sup>-party libraries.

### Unit tests

For unit testing credit, use JUnit5 to verify your code with the following inputs and expected outputs:

| `year` | `month` | `day` | `toEpochDay(year, month, day)` |
|:------:|:-------:|:-----:|:------------------------------:|
| `1970` | `0` | `1` | `0` |
| `1970` | `0` | `2` | `1` |
| `1999` | `11` | `31` | `10956` |
| `2999` | `11` | `31` | `376199` |
| `1777` | `3` | `30` | `-70372` |

### Hints

* The method to be completed is `static`; there is no need to create instances of `DateOnly` (and arguably no benefit in doing so).

* You may find it useful to create one or more additional `static` methods as "helpers"; the problem can be solved without doing so, however.

* Don't hesitate to declare any constants (`static final` fields) that you feel might simplify or clarify your code.

* The method to be completed includes a `TODO` comment to that effect.

* This problem can be solved in a number of ways; one relatively straightforward approach breaks it down into 2 parts:

    * Computing the number of days between January 1, 1970 and January 1 in the year specified by the `year` parameter. For a year prior to 1970, this should be a negative number.

    * Computing the number of days between January 1 in the year specified by `year`, and the date specified by `month` and `day` in that same year.
    
    The sum of the results of these 2 computations will give the correct overall answer.
    
* In the Gregorian and IS0 8601 calendars, a leap year is defined as one which is

    * evenly divisible by 4, **and**
    
        * _not_ divisible by 100, **or**
        * divisible by 400.
        
    So, 1976 is a leap year (1976 is evenly divisible by 4, and not by 100), as is 2000 (evenly divisible by 4, and by 400); 2100, however, is not a leap year (2100 is evenly disivible by 4, and also by 100, but not by 400).

* The number of days between January 1, 1970 and January 1 of some other year can be computing by multiplying the difference in years by 365, and then adding to that product the number of leap days between those 2 dates.

## Extra credit

In the basic problem, the `year`, `month`, and `day` values will all be well-behaved&mdash;that is, each will be in the expected range. However, date computations often require normalization of non-normalized inputs. For example, we would treat month 12 of some year as actually being month 0 of the following year; similarly, January 32 would be considered February 1. We might even have negative values for `month` and `day`: July -2 should be treated as June 28, for example.

For extra credit, normalize the input data before making the elapsed day computations, so that even if `month` or `day` is outside the expected range, the computations work as expected.

### Unit tests

For unit testing credit on the extra credit portion of the problem, use JUnit5 to verify the correctness of your code with the following inputs and expected outputs:

| `year` | `month` | `day` | `toEpochDay(year, month, day)` |
|:------:|:-------:|:-----:|:-------------------------------:|
| `1970` | `0` | `0` | `-1` |
| `1969` | `12` | `3` | `2` |
| `1975` | `-48` | `1` | `365` |
| `1998` | `18` | `184` | `10956` |

### Hints

* Normalization should be done from the largest unit to the smallest. That is, you should normalize the `month` component of the date first, adjusting the `year` component as necessary; then, normalize the `day` component, adjusting the `month` and `year` components as necessary.