package entities;

import annotations.Column;
import annotations.Entity;
import annotations.Id;
import annotations.Unique;

@Entity(name = "countries")
public class Country {

    @Id
    @Column(name = "id")
    private int id;

    @Unique()
    @Column(name = "name")
    private String name;

    @Column(name = "currency")
    private String currency;

    public Country(String name, String currency) {
        this.name = name;
        this.currency = currency;
    }
    public Country(){}

    public int getId() { return id; }
    public String getName() { return name; }
    public String getCurrency() { return currency; }
}
