package testex.Interfaces;

import testex.JokeException;

import java.util.Date;

/**
 * Created by ms on 14-03-17.
 */
 public interface IDataFormatter {

     String getFormattedDate(Date date, String timeZone) throws JokeException;


}
