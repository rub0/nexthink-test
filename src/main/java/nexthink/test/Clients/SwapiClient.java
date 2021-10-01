package nexthink.test.Clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.uri.UriBuilder;
import jakarta.inject.Singleton;
import nexthink.test.Entities.Inhabitant;
import nexthink.test.Entities.Planet;
import nexthink.test.Entities.Starship;
import nexthink.test.Entities.SwapiResultList;
import reactor.core.publisher.Mono;

@Singleton
public class SwapiClient {

    private final HttpClient httpClient;

    private ObjectMapper objectMapper = new ObjectMapper();

    public SwapiClient(@Client("https://swapi.dev/api/") HttpClient httpClient){
        this.httpClient = httpClient;
    }


    @Get(value = "/people/?search={name}")
    public Mono<SwapiResultList<Inhabitant>> getCharacterByName(String name) {
        HttpRequest<?> request = HttpRequest.GET(
                UriBuilder.of("people").queryParam("search", name)
                        .build());
        return Mono.from(httpClient.retrieve(request, String.class)).map(response -> {
            try {
//                TypeReference<SwapiResultList<Inhabitant>> token = new TypeReference<>() {};
//                SwapiResultList asd = objectMapper.readValue(response, token);
                return objectMapper.readValue(response, new TypeReference<SwapiResultList<Inhabitant>>() {});
            } catch (JsonProcessingException e) {
                return null;
            }
        });
    }

    @Get(value = "/planets/?search={name}")
    public Mono<SwapiResultList<Planet>> getPlanetByName(String name){
        HttpRequest<?> request = HttpRequest.GET(
                UriBuilder.of("planets").queryParam("search", name)
                        .build());
        return Mono.from(httpClient.retrieve(request, String.class)).map(response -> {
            try {
                return objectMapper.readValue(response, SwapiResultList.class);
            } catch (JsonProcessingException e) {
                return null;
            }
        });
    }

    @Get(value = "/starships/{id}")
    public Mono<Starship> getStarship(String id){
        HttpRequest<?> request = HttpRequest.GET(
                UriBuilder.of("starships").path(id)
                        .build());
        return Mono.from(httpClient.retrieve(request, Argument.of(Starship.class)));
    }

    @Get(value = "/people/{id}")
    public Mono<Inhabitant> getCharacter(String id){
        HttpRequest<?> request = HttpRequest.GET(
                UriBuilder.of("people").path(id)
                        .build());
        return Mono.from(httpClient.retrieve(request, Argument.of(Inhabitant.class)));
    }
}
