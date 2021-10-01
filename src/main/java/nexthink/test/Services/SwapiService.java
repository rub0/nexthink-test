package nexthink.test.Services;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import nexthink.test.Clients.SwapiClient;
import nexthink.test.Entities.Inhabitant;
import nexthink.test.Entities.Starship;
import org.apache.commons.lang3.StringUtils;
import reactor.core.publisher.Flux;

import java.io.File;

@Singleton
public class SwapiService {

    @Inject
    SwapiClient swapiClient;

    public Flux<Starship> getStarships(String characterName) {
        return swapiClient.getCharacterByName(characterName)
                .flatMapMany(result ->
                    Flux.fromIterable(result.results)).switchIfEmpty(Flux.error(new Exception("character not found")))
                .flatMap(result -> Flux.fromIterable(result.getStarships()))
                .flatMap(starship -> swapiClient.getStarship(new File(StringUtils.chomp(starship)).getName()));
    }

    public Flux<Inhabitant> getInhabitants(String planetName) {
        return swapiClient.getPlanetByName(planetName)
                .flatMapMany(result ->
                        Flux.fromIterable(result.results)).switchIfEmpty(Flux.error(new Exception("character not found")))
                .flatMap(result -> Flux.fromIterable(result.getResidents()))
                .flatMap(planet -> swapiClient.getCharacter(new File(StringUtils.chomp(planet)).getName()));
    }
}
