package de.lootfans.restapi.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Entity
@Table(name="users")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Email(message = "Email can not be null")
    @NotNull(message = "Email must be entered")
    private String email;

    @Column(name = "first_name")
    @NotNull(message = "Firstname can not be null")
    private String firstName;

    @Column(name = "last_name")
    @NotNull(message = "Lastname can not be null")
    private String lastName;

    @Min(value = 18, message = "Age should not be less than 18")
    @Max(value = 150, message = "Age should not be greater than 150")
    @Column(name = "age")
    @NotNull(message = "Age cannot be null")
    private int age;

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private String status;

    @Column(name = "last_update")
    private String lastUpdate;

    public User() {

    }

    public User(@NotNull String email, @NotNull String firstName, @NotNull String lastName, @NotNull int age) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }


    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", birthDate='" + birthDate + '\'' +
                ", password='" + password + '\'' +
                ", status='" + status + '\'' +
                ", lastUpdate='" + lastUpdate + '\'' +
                '}';
    }
}
