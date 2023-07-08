
package com.cydeo.dto.country;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.LinkedHashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "2016"
})
@Generated("jsonschema2pojo")
public class Gini {

    @JsonProperty("2016")
    private Double _2016;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("2016")
    public Double get2016() {
        return _2016;
    }

    @JsonProperty("2016")
    public void set2016(Double _2016) {
        this._2016 = _2016;
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
