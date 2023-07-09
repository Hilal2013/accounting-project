
package com.cydeo.dto.currency;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "last_updated_at"
})
@Generated("jsonschema2pojo")
public class Meta {

    @JsonProperty("last_updated_at")
    private String lastUpdatedAt;

    @JsonProperty("last_updated_at")
    public String getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    @JsonProperty("last_updated_at")
    public void setLastUpdatedAt(String lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

}
