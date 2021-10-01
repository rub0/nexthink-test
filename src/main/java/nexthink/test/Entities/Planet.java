package nexthink.test.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Introspected
@JsonIgnoreProperties(ignoreUnknown = true)
public class Planet {
    String name;
    List<String> residents;
}
