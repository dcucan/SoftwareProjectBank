package bankband.bank.models;

import bankband.bank.repositories.AccountRepository;

import java.util.List;

public class User {

    private Integer id;

    private String name;

    private String surname;

    private String email;

    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Account> getAccounts() {
        return (new AccountRepository()).findAllForUser(this);
    }

    @Override
    public String toString() {
        return "User ID: " + id + ", name: " + name + ", surname: " + surname;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof User)) return false;

        return ((User) o).getId() == this.getId();
    }

}
