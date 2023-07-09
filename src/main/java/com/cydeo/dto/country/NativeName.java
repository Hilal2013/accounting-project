
package com.cydeo.dto.country;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.LinkedHashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "deu"
})
@Generated("jsonschema2pojo")
public class NativeName {

    @JsonProperty("deu")
    private Deu deu;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("deu")
    public Deu getDeu() {
        return deu;
    }

    @JsonProperty("deu")
    public void setDeu(Deu deu) {
        this.deu = deu;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
