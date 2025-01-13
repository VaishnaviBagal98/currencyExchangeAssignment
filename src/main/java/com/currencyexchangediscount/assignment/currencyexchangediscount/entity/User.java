package com.currencyexchangediscount.assignment.currencyexchangediscount.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * Entity class representing a user in the system.
 * <p>
 * This class is mapped to the "users" table in the database and contains information about the user
 * such as user ID, username, and password.
 * </p>
 * <p>
 * This entity will be used for storing user information and interacting with the database.
 * </p>
 *
 * @author Vaishnavi Bagal
 * @version 1.0
 */
@Entity
@Data
@Table(name = "users")
@Slf4j
public class User implements Serializable {

    /**
     * Unique identifier for the user.
     * <p>
     * This is the primary key for the "users" table, and it is generated automatically.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The username of the user.
     * <p>
     * This field stores the user's name, which is used for login and identification.
     * </p>
     */
    private String userName;

    /**
     * The password of the user.
     * <p>
     * This field stores the user's password in a hashed format.
     * </p>
     */
    private String password;

    /**
     * Default constructor for the User entity.
     * <p>
     * This constructor is used by JPA to instantiate the entity. It does not contain logic,
     * but is required for JPA to function correctly.
     * </p>
     */
    public User() {
        log.debug("User entity instance created.");
    }

    /**
     * Overridden toString method to avoid printing sensitive information like password.
     * <p>
     * This method will return the user name and user id, but not the password.
     * </p>
     *
     * @return A string representation of the User entity.
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                '}';
    }

    /**
     * Log the user data when the entity is persisted.
     * <p>
     * This method is invoked when the user entity is saved to the database.
     * It logs the user creation event.
     * </p>
     *
     * @param user The user entity that is about to be persisted.
     */
    @PrePersist
    public void logBeforePersist() {
        log.info("Preparing to persist user: {}", this);
    }

    /**
     * Log the user data after the entity is updated.
     * <p>
     * This method is invoked after the user entity is updated in the database.
     * It logs the user update event.
     * </p>
     *
     * @param user The user entity that was updated.
     */
    @PostUpdate
    public void logAfterUpdate() {
        log.info("User updated successfully: {}", this);
    }

    /**
     * Log the user data when the entity is deleted.
     * <p>
     * This method is invoked when the user entity is deleted from the database.
     * It logs the user deletion event.
     * </p>
     *
     * @param user The user entity that is about to be deleted.
     */
    @PreRemove
    public void logBeforeRemove() {
        log.info("Preparing to remove user: {}", this);
    }
}
