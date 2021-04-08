package com.sg.pokemonproject.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Other {

    @JsonProperty("official-artwork")
    private OfficialArtwork officialArtwork;

    public OfficialArtwork getOfficialArtwork() {
        return officialArtwork;
    }

    public void setOfficialArtwork(OfficialArtwork officialArtwork) {
        this.officialArtwork = officialArtwork;
    }

}
