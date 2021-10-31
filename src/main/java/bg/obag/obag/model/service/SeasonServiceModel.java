package bg.obag.obag.model.service;

import bg.obag.obag.model.entity.SeasonEntity;

public class SeasonServiceModel {

    private Long id;
    private String season;

    public Long getId() {
        return id;
    }

    public SeasonServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getSeason() {
        return season;
    }

    public SeasonServiceModel setSeason(String season) {
        this.season = season;
        return this;
    }
}
