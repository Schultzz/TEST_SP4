
package testex;

import testex.Entities.ChuckNorris;
import testex.Entities.EduJoke;
import testex.Entities.Moma;
import testex.Entities.Tambal;
import testex.Interfaces.IDataFormatter;
import testex.Interfaces.IFetcherFactory;
import testex.Interfaces.IJokeFetcher;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class used to fetch jokes from a number of external joke API's
 */
public class JokeFetcher {

  private IDataFormatter dateFormatter;
  private IFetcherFactory fetcherFactory;

    public JokeFetcher(IDataFormatter dateFormatter, IFetcherFactory fetcherFactory) {
        this.dateFormatter = dateFormatter;
        this.fetcherFactory = fetcherFactory;
    }


  /**
   * Verifies whether a provided value is a valid string (contained in availableTypes)
   * @param jokeTokens. Example (with valid values only): "eduprog,chucknorris,chucknorris,moma,tambal"
   * @return true if the param was a valid value, otherwise false
   */
  public boolean isStringValid(String jokeTokens){
    String[] tokens = jokeTokens.split(",");
      System.out.println(fetcherFactory.getAvailableTypes());
      for(String token: tokens){
      if(!fetcherFactory.getAvailableTypes().contains(token)){
        return false;
      }
    }
    return true;
  }
  
  /**
   * Fetch jokes from external API's as given in the input string - jokesToFetch
   * @param jokesToFetch A comma separated string with values (contained in availableTypes) indicating the jokes
   * to fetch. Example: "eduprog,chucknorris,chucknorris,moma,tambal" will return five jokes (two chucknorris)
   * @param timeZone. Must be a valid timeZone string as returned by: TimeZone.getAvailableIDs()  
   * @return A Jokes instance with the requested jokes + time zone adjusted string representing fetch time
   * (the jokes list can contain null values, if a server did not respond correctly)
   * @throws JokeException. Thrown if either of the two input arguments contains illegal values
   */
  public Jokes getJokes(String jokesToFetch, String timeZone) throws JokeException {
      if(!isStringValid(jokesToFetch)){
          throw new JokeException("Invalid");
      }
      Jokes jokes = new Jokes();
      for (IJokeFetcher fetcher : fetcherFactory.getJokeFetchers(jokesToFetch)) {
          jokes.addJoke(fetcher.getJoke());
      }
      String formattedDate = dateFormatter.getFormattedDate(new Date(), timeZone);
      jokes.setTimeZoneString(formattedDate);
      return jokes;
  }
  
  /**
   * DO NOT TEST this function. It's included only to get a quick way of executing the code
   * @param args 
   */
  public static void main(String[] args) throws JokeException {

    String dateTimeFormat = "dd MMM yyyy hh:mm aa";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateTimeFormat);

    IDataFormatter dateFormatter = new DateFormatter(simpleDateFormat);

    IFetcherFactory jokeFetcherFactory = new JokeFetcherFactory(new EduJoke(), new ChuckNorris(), new Moma(), new Tambal());

    JokeFetcher jf = new JokeFetcher(dateFormatter, jokeFetcherFactory);
    Jokes jokes = jf.getJokes("EduJoke,ChuckNorris,Moma,Tambal","Europe/Copenhagen");
    jokes.getJokes().forEach((joke) -> {
      System.out.println(joke);
    });
    System.out.println("Is String Valid: "+jf.isStringValid("edu_prog,xxx"));
  }
}
