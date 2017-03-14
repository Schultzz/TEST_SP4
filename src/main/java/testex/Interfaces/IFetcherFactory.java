package testex.Interfaces;

import java.util.List;

/**
 * Created by ms on 14-03-17.
 */
public interface IFetcherFactory {

    List<String> getAvailableTypes();

    List<IJokeFetcher> getJokeFetchers(String jokesToFetch);

}
