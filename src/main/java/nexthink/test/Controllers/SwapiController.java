package nexthink.test.Controllers;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import nexthink.test.Entities.Inhabitant;
import nexthink.test.Entities.Starship;
import nexthink.test.Services.SwapiService;
import reactor.core.publisher.Flux;

@Controller("/")
public class SwapiController {

    SwapiService swapiService;

    public SwapiController(SwapiService service){
        this.swapiService = service;
    }

    @Get("characters/{characterName}/starships")
    public Flux<Starship> getStarships(String characterName){
        return swapiService.getStarships(characterName);
    }

    @Get("planets/{planetName}/inhabitants")
    public Flux<Inhabitant> getInhabitants(String planetName){
        return swapiService.getInhabitants(planetName);
    }
}
