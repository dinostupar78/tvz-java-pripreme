package hr.java.restaurant.model;

public class Category extends Entity {
    private String name;
    private String description;

    public Category( Long id, String name, String description) {
        super(id);
        this.name = name;
        this.description = description;

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
