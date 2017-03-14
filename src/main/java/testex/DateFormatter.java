package testex;

import testex.Interfaces.IDataFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

public class DateFormatter implements IDataFormatter {

  private DateFormat simpleFormat;

  public DateFormatter(DateFormat simpleFormat) {
    this.simpleFormat = simpleFormat;
  }

  public String getFormattedDate(Date time, String timeZone) throws JokeException  {
    if(!Arrays.asList(TimeZone.getAvailableIDs()).contains(timeZone)){
      throw new JokeException("Illegal Time Zone String");
    }
    simpleFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
    return simpleFormat.format(time); 
  }

  /**
   * DO NOT TEST this function as part of the exercise.
   * It's included only to get a quick way of executing the code
   * Execute to see available time format strings and responses to calling the single (non-main) public method
   */
  public static void main(String[] args) throws JokeException  {

    String dateTimeFormat = "dd MMM yyyy hh:mm aa";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateTimeFormat);

    IDataFormatter dataFormatter = new DateFormatter(simpleDateFormat);

    for (String str : TimeZone.getAvailableIDs()) {
      System.out.println(str);
    }
    
    //Executing our public method with a valid String:
    System.out.println(dataFormatter.getFormattedDate(new Date(),"Europe/Kiev"));
    
    System.out.println(dataFormatter.getFormattedDate(new Date(), "ImNotLegal"));
    
    
    
  }
}

//Just to try out the power of abstactions and stubbing.
class FakeDateFormatter implements IDataFormatter {

  @Override
  public String getFormattedDate(Date date, String timeZone) throws JokeException {
    return "OK";
  }
}
