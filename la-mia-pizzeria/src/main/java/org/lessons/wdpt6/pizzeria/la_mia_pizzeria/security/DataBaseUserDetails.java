package org.lessons.wdpt6.pizzeria.la_mia_pizzeria.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.lessons.wdpt6.pizzeria.la_mia_pizzeria.model.Role;
import org.lessons.wdpt6.pizzeria.la_mia_pizzeria.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class DataBaseUserDetails implements UserDetails {

    private final Integer id;
    private final String  username;
    private final String  password;
    private final Set<GrantedAuthority> authorities;

    public DataBaseUserDetails(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();

        //popolo le authorities
        
        this.authorities = new HashSet<GrantedAuthority>();

        for (Role role : user.getRoles()) {
            this.authorities.add(new SimpleGrantedAuthority(role.getName()));

        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return this.authorities;

    }

    //overide perchè sono metodi astratti della UserDetails

    @Override
    public String getPassword() {
        return this.password;

    }

    @Override
    public String getUsername() {
        return this.username;

    }

    public Integer getId() {
        return this.id;

    }

}
