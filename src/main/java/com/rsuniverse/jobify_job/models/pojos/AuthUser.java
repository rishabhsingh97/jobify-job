package com.rsuniverse.jobify_job.models.pojos;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import com.rsuniverse.jobify_job.models.enums.UserRole;
import com.rsuniverse.jobify_job.models.enums.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthUser implements UserDetails {
    private String id;
    private String fullName;
    private String email;
    private Set<UserRole> roles;
    private UserStatus status;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return "";
    }

}
