package testex.Entities;

import static com.jayway.restassured.RestAssured.given;
import testex.Interfaces.IJokeFetcher;
import testex.Joke;

/**
 * Created by ms on 14-03-17.
 */
public class ChuckNorris implements IJokeFetcher {
    @Override
    public Joke getJoke() {
        try{
            String joke  = given().get("http://api.icndb.com/jokes/random").path("value.joke");
            return new Joke(joke,"http://api.icndb.com/");
        } catch(Exception e){
            return null;
        }
    }
}
