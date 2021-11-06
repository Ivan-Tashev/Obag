package bg.obag.obag.model.view;

import java.time.LocalDateTime;

public class LogViewModel {
    private Long id;
    private String user;
    private String productName;
    private String action;
    private LocalDateTime createdOn;

    public Long getId() {
        return id;
    }

    public LogViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUser() {
        return user;
    }

    public LogViewModel setUser(String user) {
        this.user = user;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public LogViewModel setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public String getAction() {
        return action;
    }

    public LogViewModel setAction(String action) {
        this.action = action;
        return this;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public LogViewModel setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }
}
