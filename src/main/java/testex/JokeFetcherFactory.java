package testex;

import testex.Entities.ChuckNorris;
import testex.Entities.EduJoke;
import testex.Entities.Moma;
import testex.Entities.Tambal;
import testex.Interfaces.IJokeFetcher;
import testex.Interfaces.IFetcherFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ms on 14-03-17.
 */
public class JokeFetcherFactory implements IFetcherFactory {

    private EduJoke eduJoke;
    private ChuckNorris chuckNorris;
    private Moma moma;
    private Tambal tambal;

    public JokeFetcherFactory(EduJoke eduJoke, ChuckNorris chuckNorris, Moma moma, Tambal tambal) {
        this.eduJoke = eduJoke;
        this.chuckNorris = chuckNorris;
        this.moma = moma;
        this.tambal = tambal;
    }

    private final List<String> availableTypes =
            Arrays.asList("EduJoke", "ChuckNorris", "Moma", "Tambal");

    @Override
    public List<String> getAvailableTypes(){
        return availableTypes;
    }

    @Override
    public List<IJokeFetcher> getJokeFetchers(String jokesToFetch) {

        List<IJokeFetcher> jokes = new ArrayList<>();

        String[] tokens = jokesToFetch.split(",");

        for (String token : tokens) {
            switch (token) {
                case "EduJoke":
                    jokes.add(eduJoke);
                    break;
                case "ChuckNorris":
                    jokes.add(chuckNorris);
                    break;
                case "Moma":
                    jokes.add(moma);
                    break;
                case "Tambal":
                    jokes.add(tambal);
                    break;
            }
        }

        return jokes;
    }
    }

