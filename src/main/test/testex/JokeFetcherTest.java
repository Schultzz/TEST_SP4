package testex;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import testex.Entities.ChuckNorris;
import testex.Entities.EduJoke;
import testex.Entities.Moma;
import testex.Entities.Tambal;
import testex.Interfaces.IDataFormatter;
import testex.Interfaces.IFetcherFactory;
import testex.Interfaces.IJokeFetcher;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * Created by ms on 14-03-17.
 */

@RunWith(MockitoJUnitRunner.class)
public class JokeFetcherTest {

    private JokeFetcher jf = null;

    @Mock
    IDataFormatter dataFormatterMock;
    @Mock
    IFetcherFactory factory;
    @Mock
    EduJoke eduJoke;
    @Mock
    ChuckNorris chuckNorris;
    @Mock
    Moma moma;
    @Mock
    Tambal tambal;


    @Before
    public void setUp() {

        List<IJokeFetcher> fetcher = Arrays.asList(chuckNorris, eduJoke, moma, tambal);
        when(factory.getJokeFetchers("eduprog,chucknorris,chucknorris,moma,tambal")).thenReturn(fetcher);

        List<String> types = Arrays.asList("EduJoke", "ChuckNorris", "Moma", "Tambal");
        when(factory.getAvailableTypes()).thenReturn(types);

        Joke joke = new Joke("testJoke", "testReference");
        given(chuckNorris.getJoke()).willReturn(joke);
        given(eduJoke.getJoke()).willReturn(joke);
        given(moma.getJoke()).willReturn(joke);
        given(tambal.getJoke()).willReturn(joke);

        jf = new JokeFetcher (dataFormatterMock, factory);

    }


    @Test
    public void getJokes(){



    }

    @Test
    public void getJokesSize() throws JokeException {

        Jokes jokes = jf.getJokes("EduJoke,ChuckNorris,Moma,Tambal","Europe/Copenhagen");

        List<Joke> jokeList = jokes.getJokes();

        assertThat(jokeList, hasSize(0));
    }

    @Test
    public void getAvailableTypes(){

        IFetcherFactory iFetcherFactory = new JokeFetcherFactory(eduJoke, chuckNorris, moma, tambal);

        List<String> types = iFetcherFactory.getAvailableTypes();

        assertThat(types, hasItems("EduJoke", "ChuckNorris", "Moma", "Tambal"));

    }

    @Test
    public void isStringValid(){

        assertThat(jf.isStringValid("EduJoke,ChuckNorris,Moma,Tambal"), is(true));
        assertThat(jf.isStringValid("wrongType"), is(false));

    }

    @Test
    public void testDateFormatterMock() throws JokeException {
        Date date = new Date(946681200000L);
        String expectedDateFormat = "01 jan. 2000 00:00 PM";
        //Given
        when(dataFormatterMock.getFormattedDate(date,"Europe/Copenhagen")).thenReturn(expectedDateFormat);
        assertThat(dataFormatterMock.getFormattedDate(date, "Europe/Copenhagen"), is(expectedDateFormat));

        //Verify
        verify(dataFormatterMock, times(1)).getFormattedDate(date, "Europe/Copenhagen");


    }


}
