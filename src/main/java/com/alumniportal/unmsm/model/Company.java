package com.alumniportal.unmsm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tblCompany")
public class Company extends AbstractEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String ruc;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = true)
    private String sector;

    @Column(nullable = true)
    private String phone;

    @Column(nullable = true)
    private String website;

    @Column(nullable = true)
    private String location;

    @Column(nullable = false)
    private LocalDate createdAt;

    @Column(nullable = true)
    private LocalDate updatedAt;

    private String photoUrl;

    @Enumerated(EnumType.STRING)
    private Role role;

    //    Relacion con JobOffer
    @OneToMany(mappedBy = "company", orphanRemoval = true)
    @JsonIgnore
    private List<JobOffer> jobOfferList;


    //    Relacion con Activity
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Activity> activityList;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
