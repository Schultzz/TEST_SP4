package testex;

import org.hamcrest.beans.HasPropertyWithValue;
import org.hamcrest.core.Every;
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

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * Created by ms on 14-03-17.
 */

@RunWith(MockitoJUnitRunner.class)
public class JokeFetcherTest {

    private JokeFetcher jf = null;
    private Date date;
    private String expectedDateFormat = "01 jan. 2000 00:00 PM";

    @Mock
    private IDataFormatter dataFormatterMock;
    @Mock
    private IFetcherFactory factory;
    @Mock
    private EduJoke eduJoke;
    @Mock
    private ChuckNorris chuckNorris;
    @Mock
    private Moma moma;
    @Mock
    private Tambal tambal;


    @Before
    public void setUp() throws JokeException {

        date = new Date(946681200000L);

        when(dataFormatterMock.getFormattedDate(date,"Europe/Copenhagen")).thenReturn(expectedDateFormat);

        List<IJokeFetcher> fetcher = Arrays.asList(chuckNorris, eduJoke, moma, tambal);
        when(factory.getJokeFetchers("EduJoke,ChuckNorris,Moma,Tambal")).thenReturn(fetcher);

        List<String> types = Arrays.asList("EduJoke", "ChuckNorris", "Moma", "Tambal");
        when(factory.getAvailableTypes()).thenReturn(types);

        Joke joke = new Joke("new joke", "reference");

        given(eduJoke.getJoke()).willReturn(joke);
        given(chuckNorris.getJoke()).willReturn(joke);
        given(moma.getJoke()).willReturn(joke);
        given(tambal.getJoke()).willReturn(joke);

        jf = new JokeFetcher (dataFormatterMock, factory);

    }


    @Test
    public void getJokes() throws JokeException {

        List<Joke> mockedJokes = jf.getJokes("EduJoke,ChuckNorris,Moma,Tambal", "Europe/Copenhagen").getJokes();

        assertThat(mockedJokes,
                Every.everyItem(HasPropertyWithValue.hasProperty("joke", is("new joke"))));

        verify(dataFormatterMock, times(1)).getFormattedDate(date, "Europe/Copenhagen");
        verify(factory, times(1)).getJokeFetchers("EduJoke,ChuckNorris,Moma,Tambal");
    }


    @Test
    public void getJokeFetchers() {
        List<IJokeFetcher> fetcher = factory.getJokeFetchers("EduJoke,ChuckNorris,Moma,Tambal");
        assertThat(fetcher, containsInAnyOrder(eduJoke,chuckNorris, moma, tambal));
    }

    @Test
    public void getJokesSize() throws JokeException {

        Jokes jokes = jf.getJokes("EduJoke,ChuckNorris,Moma,Tambal","Europe/Copenhagen");

        List<Joke> jokeList = jokes.getJokes();

        assertThat(jokeList, hasSize(4));
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


        assertThat(dataFormatterMock.getFormattedDate(date, "Europe/Copenhagen"), is(expectedDateFormat));
        //Verify
        verify(dataFormatterMock, times(1)).getFormattedDate(date, "Europe/Copenhagen");


    }


}
