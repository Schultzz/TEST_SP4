package testex.Entities;

import testex.Interfaces.IJokeFetcher;
import testex.Joke;

import static com.jayway.restassured.RestAssured.given;
/**
 * Created by ms on 14-03-17.
 */
public class Tambal implements IJokeFetcher {
    @Override
    public Joke getJoke() {
        try{
            String joke  = given().get("http://tambal.azurewebsites.net/joke/random").path("joke");
            return new Joke(joke,"http://tambal.azurewebsites.net/joke/random");
        } catch(Exception e){
            return null;
        }
    }
}
