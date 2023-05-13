package ru.dextermedical.laboratory.models;
import jakarta.persistence.*;

@Entity
@Table(name = "point_object")
public class PointObject {

    @Column(name = "longitude") //В координатах OSM долгота это второе значение после широты
    private double longitude;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public PointObject(double longitude, double latitude, String name, String address) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;
        this.address = address;
    }

    public PointObject() {

    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}