package bg.obag.obag.model.view;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class VisitViewModel {
    private Long id;
    private LocalDate date;
    private Integer uniqueVisits;
    private Integer totalVisits;
    private LocalDateTime createdOn;

    public Long getId() {
        return id;
    }

    public VisitViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public VisitViewModel setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public Integer getUniqueVisits() {
        return uniqueVisits;
    }

    public VisitViewModel setUniqueVisits(Integer uniqueVisits) {
        this.uniqueVisits = uniqueVisits;
        return this;
    }

    public Integer getTotalVisits() {
        return totalVisits;
    }

    public VisitViewModel setTotalVisits(Integer totalVisits) {
        this.totalVisits = totalVisits;
        return this;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public VisitViewModel setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }
}
