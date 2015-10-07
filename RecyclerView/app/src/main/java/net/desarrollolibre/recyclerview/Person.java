package net.desarrollolibre.recyclerview;

/**
 * Created by andres on 04/10/15.
 */
public class Person {
    String name;
    String description;
    String color;

    Person(String name, String description,String color){
        this.name = name;
        this.description = description;
        this.color = color;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
