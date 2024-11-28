package com.alumniportal.unmsm.Data;

import com.alumniportal.unmsm.model.User;

import java.util.List;

public class UserProvider {

    public static List<User> userList() {
        return List.of(
                User.builder().id(1L).name("User 1").paternalSurname("Surname 1").maternalSurname("Surname 1")
                        .email("email@gmail.com").about("abouttext").build(),
                User.builder().id(2L).name("User 2").paternalSurname("Surname 2").maternalSurname("Surname 2")
                        .email("email2@gmail.com").about("abouttext").build(),
                User.builder().id(3L).name("User 3").paternalSurname("Surname 3").maternalSurname("Surname 3")
                        .email("email3@gmail.com").about("abouttext").build()
        );
    }

    public static User userOne() {
        return User.builder()
                .id(1L)
                .name("User 1")
                .paternalSurname("Surname 1")
                .maternalSurname("Surname 1")
                .email("emailuser@gmail.com")
                .about("abouttext")
                .build();
    }

}
