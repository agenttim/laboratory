package ru.dextermedical.laboratory.models;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "polygon_object")
public class PolygonObjects {
    @Id
    private Long id;

    @Column(name = "fig_name")
    private String name;

    @Column(name = "fig_color")
    private String color;

    @Column(name = "polygon_loc")
    private String polygon; // можно использовать строковое представление типа polygon

    public PolygonObjects(String name, String color, String polygon) {
        this.name = name;
        this.color = color;
        this.polygon = polygon;
    }

    public PolygonObjects() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPolygon() {
        return polygon;
    }

    public void setPolygon(String polygon) {
        this.polygon = polygon;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
