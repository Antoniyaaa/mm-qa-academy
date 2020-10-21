package user;

import java.time.LocalDate;

public class User {
    public enum Title {
        MR("1"), MRS("2");

        private String titleValue;

        Title(String titleValue) {
            this.titleValue = titleValue;
        }

        public String getTitleValue() {
            return titleValue;
        }
    }

    private String email = null;
    private String password = null;
    private Title title = null;
    private String firstName = null;
    private String lastName = null;
    private boolean isSignedUpForNewsletter = false;
    private boolean isReceivingSpecialOffersFromPartners = false;
    private LocalDate birthDate = null;
    private String company = null;
    private String homePhone = null;
    private String mobilePhone = null;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, Title title, String firstName, String lastName, boolean isSignedUpForNewsletter, boolean isReceivingSpecialOffersFromPartners, LocalDate birthDate, String company, String homePhone, String mobilePhone) {
        this.email = email;
        this.password = password;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isSignedUpForNewsletter = isSignedUpForNewsletter;
        this.isReceivingSpecialOffersFromPartners = isReceivingSpecialOffersFromPartners;
        this.birthDate = birthDate;
        this.company = company;
        this.homePhone = homePhone;
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Title getTitle() {
        return title;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public boolean isSignedUpForNewsletter() {
        return isSignedUpForNewsletter;
    }

    public boolean isReceivingSpecialOffersFromPartners() {
        return isReceivingSpecialOffersFromPartners;
    }

    public String getCompany() {
        return company;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }
}
