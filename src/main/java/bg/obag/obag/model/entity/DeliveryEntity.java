package bg.obag.obag.model.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "deliveries")
public class DeliveryEntity extends BaseEntity{
    @Column(nullable = false)
    private String name; // Speedy: Стандарт 24часа (Безплатно)

    public String getName() {
        return name;
    }

    public DeliveryEntity setName(String name) {
        this.name = name;
        return this;
    }
}
