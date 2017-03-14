package testex;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import testex.Interfaces.IDataFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.Mockito.verify;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


/**
 * Created by ms on 14-03-17.
 */

@RunWith(MockitoJUnitRunner.class)
public class DateFormatterTest {

    String dateTimeFormat = "dd MMM yyyy hh:mm aa";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateTimeFormat);

    IDataFormatter fakeDataFormatter = new FakeDateFormatter();
    IDataFormatter dateFormatter = new DateFormatter(simpleDateFormat);

    @Test
    public void getFormattedDate() throws JokeException {
        String timeZone = "Europe/Copenhagen";
        Date date = new Date(946681200000L);

        String formattedDate = dateFormatter.getFormattedDate(date,timeZone);

        assertThat(formattedDate, equalTo("01 jan. 2000 12:00 AM"));

    }

    @Test(expected = JokeException.class)
    public void getFormattedDateInvalid() throws JokeException {
        String timeZone = "ImInvalid";

        DateFormatter dateFormatterInvalid = new DateFormatter(new SimpleDateFormat());
        dateFormatterInvalid.getFormattedDate(new Date(), timeZone);

        Assert.assertTrue(true);
    }
}
