package user;

public class Address {
    private String street = null;
    private String buildingInfo = null;
    private String city = null;
    private String state = null;
    private String postalCode = null;
    private String country = null;
    private String additionalInformation = null;
    private String alias = null;

    public Address(String street, String building, String city, String state, String postalCode, String country, String additionalInformation, String alias) {
        this.street = street;
        this.buildingInfo = building;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.additionalInformation = additionalInformation;
        this.alias = alias;
    }

    public String getStreet() {
        return street;
    }

    public String getBuildingInfo() {
        return buildingInfo;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public String getAlias() {
        return alias;
    }
}
