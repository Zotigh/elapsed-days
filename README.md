# Date-only Calculations

## Value

* Basic implementation: 10 points
* Basic unit tests: 5 points
* Extra credit implementation: 10 points
* Extra credit unit tests: 5 points

## Basic task

For many computational tasks, we want to manipulate dates&mdash;but only dates. For example, in an Android app that displays the NASA Astronomy Picture of the Day (APOD), if we want to store retrieved APOD objects and images in local storage (database and/or file storage), the only date or time information that's relevant about each image is the date for which that image was the APOD. (You might also want to record a timestamp for when the image was retrieved, but that's not our concern at the moment.) Then, if you later want to search the local database for the APOD for a given day, using a time component in the search could result in database "misses"&mdash;i.e. queries that should return an APOD object, but don't.

For some applications, this leads us to the need to have a numeric representation of dates that is _not_ the number of milliseconds elapsed since 1970-01-01 00:00:00 UTC (the usual way we measure time in Java), but the number of calendar days since some agreed-upon date (for the sake of consistency, we might as well use 1970-01-01 here again). Computing this is the basic task in this assignment.

### Implementation 

Complete the `edu.cnm.deepdive.util.DateOnly.elapsedDays` method, declared as:

```java
int elapsedDays(int year, int month, int day)
```

For more method declaration details, see the [Javadoc documentation](docs/api/edu/cnm/deepdive/util/DateOnly.html#elapsedDays-int-int-int-).

For any given date value in the range from (roughly) 5,880,000 BCE to 5,880,000 CE, your implementation should return the numbers of days before (negative) or after (positive) January 1, 1970.

### Assumptions

* The `year` value will be a positive or negative `int` in the range given above. Obviously, the Gregorian calendar we use has only been around for the last 500 years or so; much less than that in many countries (including the US). Before that, the Julian calendar was used in most of the European-dominated parts of the world; it had the same months as the Gregorian calendar, but had included too many leap days over the years. Rather than deal with that changeover&mdash;and with the lack of a year 0 in either the Julian or Gregorian calendars&mdash;we'll be following the [ISO 8601 standard](https://en.wikipedia.org/wiki/ISO_8601#Years), with the added explicit requirement to support not only years prior to 1583, but years prior to 1 CE (or AD), as well, with the year traditionally referred to as 1 BCE (or 1 BC) denoted as year 0 of the ISO 8601 calendar.

* The `month` value will be in the range 0&hellip;11. This is in keeping with the convention followed in most C-derived languages and libraries, where months are numbered starting from 0.

* The `day` value will be in the range 1&hellip;(last day of `month` in specified `year`).

### Restrictions

* Because a key use case for the `elapsedDays` method is Android development, only Java 8 (or lower) syntax and standard library classes should be used.

* To avoid explicit or implicit dependence on classes which incorporate time (in addition to date), or on classes not yet available on the majority of Android devices, implementations should not use the `java.util.Date` or `java.util.Calendar` classes (or any subclass of either), nor any classes in the `java.time` package.  

### Unit tests

For unit testing credit, use JUnit5 to verify your code with the following inputs and expected outputs:

| `year` | `month` | `day` | `elapsedDays(year, month, day)` |
|:------:|:-------:|:-----:|:-------------------------------:|
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

* Fundamentally, this problem can be broken down into 2 parts:

    * Computing the number of days between January 1, 1970 and January 1 in the year specified by the `year` parameter. For a year prior to 1970, this should be a negative number.

    * Computing the number of days between January 1 in the year specified by `year`, and the date specified by `month` and `day` in that same year.
    
    Implemented correctly, the sum of these 2 computations will give the correct overall answer.
    
* In the IS0 8601 calendar, a leap year is defined as one which is

    * evenly divisible by 4, **and**
        * _not_ divisible by 100, **or**
        * divisible by _400_.
        
    So, 1976 is a leap year (1976 is evenly divisible by 4, and not by 100), as is 2000 (evenly divisible by 4, and by 400); 2100, however, is not a leap year (2100 is evenly disivible by 4, and also by 100, but not by 400).

* The number of days between January 1, 1970 and January 1 of some other year can be computing by multiplying the difference in years by 365, and then adding to that product the number of leap days between those 2 dates.

    If the difference between the two years is negative, then the number of leap days (if not zero) should be treated as negative as well. For example, when computing the number of days between January 1, 1970 and January 1, 1973, the difference in years is 3, and the number of leap days is 1; the number of days is thus 3 * 365 + 1, or 1096. On the other hand, to compute the number of days between January 1, 1970, and January 1, 1968, we have a difference in years of -2, and 1 leap day between the two dates (February 29, 1968)&mdash;but the latter should also be treated as negative, for a total of -2 * 365 - 1, or -730 days.


## Extra credit: 5 points

In the basic problem, the `year`, `month`, and `day` values will all be well-behaved&mdash;that is, each will be in the expected range. However, date computations often require normalization of non-normalized inputs. For example, we would treat month 12 of some year as actually being month 0 of the following year; similarly, January 32 would be considered February 1. We might even have negative values for `month` and `day`: July -2 should be treated as June 28, for example.

For extra credit, normalize the input data before making the elapsed day computations, so that even if `month` or `day` is outside the expected range, the computations work as expected.

### Unit tests

For unit testing credit on the extra credit portion of the problem, use JUnit5 to verify your code with the following inputs and expected outputs:

| `year` | `month` | `day` | `elapsedDays(year, month, day)` |
|:------:|:-------:|:-----:|:-------------------------------:|
| `1970` | `0` | `0` | `-1` |
| `1969` | `12` | `3` | `2` |
| `1975` | `-48` | `1` | `365` |
| `1998` | `18` | `184` | `10956` |

### Hints

* Normalization should be done from the largest unit to the smallest. That is, normalize the `month` component of the date first, adjusting the `year` component as necessary; then, normalize the `day` component, adjusting the `month` and `year` components as necessary.

* When normalizing the `day` component, one approach giving valid results is the following:

    * While `day` is less than or equal to zero, subtract 1 from `month` (renormalizing `month` and adjusting `year`, as necessary), then add to `day` the numbers of days in the the resulting `month` for `year`.
    
    * While `day` is greater than the number of days in `month` for `year`, subtract the number of days in the month from `day`, increase `month` by 1, then re-normalize `month` and adjust `year`, as necessary.
    