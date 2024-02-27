package com.nnk.springboot.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.MyUser;
import com.nnk.springboot.repositories.MyUserRepository;

/**
 * Custom implementation of UserDetailsService for retrieving user details.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private MyUserRepository myUserRepository;

	/**
	 * Load user details by username.
	 *
	 * @param username the username to load user details for
	 * @return UserDetails containing user information
	 * @throws UsernameNotFoundException if the user is not found
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<MyUser> optionalMyUser = myUserRepository.findByUsername(username);

		MyUser myUser = optionalMyUser.get();

		return (UserDetails) new User(myUser.getUsername(), myUser.getPassword(),
				getGrantedAuthorities(myUser.getRole()));
	}

	/**
	 * Get the granted authorities based on the user's role.
	 *
	 * @param role the role of the user
	 * @return a list of GrantedAuthority
	 */
	private List<GrantedAuthority> getGrantedAuthorities(String role) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
		return authorities;
	}
}