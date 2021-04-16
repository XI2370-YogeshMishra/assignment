package com.xebia.tennistest.entity;

import javax.validation.constraints.Size;

public class PlayerForm {
    @Size(min = 1, max = 32, message = "First name's length must be between {min} and {max} characters.")
    private String firstName;

    @Size(min = 1, max = 32, message = "Last name's length must be between {min} and {max} characters.")
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
