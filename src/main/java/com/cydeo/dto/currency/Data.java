
package com.cydeo.dto.currency;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "CAD",
    "EUR",
    "GBP",
    "INR",
    "JPY"
})
@Generated("jsonschema2pojo")
public class Data {

    @JsonProperty("CAD")
    private Cad cad;
    @JsonProperty("EUR")
    private Eur eur;
    @JsonProperty("GBP")
    private Gbp gbp;
    @JsonProperty("INR")
    private Inr inr;
    @JsonProperty("JPY")
    private Jpy jpy;

    @JsonProperty("CAD")
    public Cad getCad() {
        return cad;
    }

    @JsonProperty("CAD")
    public void setCad(Cad cad) {
        this.cad = cad;
    }

    @JsonProperty("EUR")
    public Eur getEur() {
        return eur;
    }

    @JsonProperty("EUR")
    public void setEur(Eur eur) {
        this.eur = eur;
    }

    @JsonProperty("GBP")
    public Gbp getGbp() {
        return gbp;
    }

    @JsonProperty("GBP")
    public void setGbp(Gbp gbp) {
        this.gbp = gbp;
    }

    @JsonProperty("INR")
    public Inr getInr() {
        return inr;
    }

    @JsonProperty("INR")
    public void setInr(Inr inr) {
        this.inr = inr;
    }

    @JsonProperty("JPY")
    public Jpy getJpy() {
        return jpy;
    }

    @JsonProperty("JPY")
    public void setJpy(Jpy jpy) {
        this.jpy = jpy;
    }

}
