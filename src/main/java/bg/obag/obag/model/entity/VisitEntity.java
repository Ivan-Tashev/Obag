package bg.obag.obag.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "visits")
public class VisitEntity extends BaseEntity{
    @Column
    private LocalDate date;
    @Column(name = "unique_visits")
    private Integer uniqueVisits;
    @Column(name = "total_visits")
    private Integer totalVisits;

    public LocalDate getDate() {
        return date;
    }

    public VisitEntity setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public Integer getUniqueVisits() {
        return uniqueVisits;
    }

    public VisitEntity setUniqueVisits(Integer uniqueVisits) {
        this.uniqueVisits = uniqueVisits;
        return this;
    }

    public Integer getTotalVisits() {
        return totalVisits;
    }

    public VisitEntity setTotalVisits(Integer totalVisits) {
        this.totalVisits = totalVisits;
        return this;
    }
}
