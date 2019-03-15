package entity;

public class Customer {
    private Name name;

    public Customer() {
    }

    public Customer(Name name) {
        this.name = name;
    }

    public Name getName() {
        return this.name;
    }

    public void setName(Name name) {
        this.name = name;
    }
}