package testex.Entities;

import testex.Interfaces.IJokeFetcher;
import testex.Joke;

import static com.jayway.restassured.RestAssured.given;
/**
 * Created by ms on 14-03-17.
 */
public class Moma implements IJokeFetcher {
    @Override
    public Joke getJoke() {
        try{
            String joke = given().get("http://api.yomomma.info/").andReturn().jsonPath().getString("joke");
            return new Joke(joke,"http://api.yomomma.info/");
        } catch(Exception e){
            return null;
        }
    }
}
