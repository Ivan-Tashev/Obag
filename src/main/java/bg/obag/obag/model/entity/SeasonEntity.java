package bg.obag.obag.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "seasons")
public class SeasonEntity extends BaseEntity{

    @Column(nullable = false, unique = true)
    private String season;

    public String getSeason() {
        return season;
    }

    public SeasonEntity setSeason(String season) {
        this.season = season;
        return this;
    }
}
