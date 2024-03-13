package com.blog.app.auth.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.app.auth.config.JwtUtil;
import com.blog.app.auth.dto.request.SignInRequest;
import com.blog.app.auth.dto.request.SignUpRequest;
import com.blog.app.auth.dto.response.SignInResponse;
import com.blog.app.auth.entity.User;
import com.blog.app.auth.exceptions.handlers.DisabledUserException;
import com.blog.app.auth.exceptions.handlers.ForbiddenActionException;
import com.blog.app.auth.exceptions.handlers.InvalidUserCredentialsException;
import com.blog.app.auth.repository.UserRepository;
import com.blog.app.main.entity.UserProfile;
import com.blog.app.main.repository.UserProfileRepository;

@Service
public class UserAuthService implements UserDetailsService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserProfileRepository userProfileRepository;

	@Autowired
	private JwtUtil jwtUtil;

	public SignInResponse signIn(SignInRequest request) {
		Authentication authentication = authenticateCredentials(request.getEmail(), request.getPassword());

		org.springframework.security.core.userdetails.User userDetails = (org.springframework.security.core.userdetails.User) authentication
				.getPrincipal();
		Set<String> roles = userDetails.getAuthorities().stream().map(r -> r.getAuthority())
				.collect(Collectors.toSet());

		String token = jwtUtil.generateToken(authentication);

		User user = userRepository.findByEmail(request.getEmail()).get();

		UserProfile userProfile = user.getProfile();
		userProfile.setLastSignIn(new Timestamp(System.currentTimeMillis()));
		user = userRepository.save(user);

		return SignInResponse.builder().authToken(token).roles(new ArrayList<String>(roles)).build();
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user;
		try {
			user = userRepository.findByEmail(username).get(); // since email is the username in Spring Security context
		} catch (NoSuchElementException e) {
			throw new InvalidUserCredentialsException("Invalid user credentials");
		}

		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole()));

		return new org.springframework.security.core.userdetails.User(username, user.getPassword(), grantedAuthorities);
	}

	public SignInResponse signUp(SignUpRequest signUpRequest) {
		String username = signUpRequest.getUserName();
		String email = signUpRequest.getEmail();

		if (userRepository.existsByUsernameOrEmail(username, email)) {
			throw new ForbiddenActionException(
					"Username or email has already been registered with a different account");
		}

		UserProfile profile = UserProfile.builder().fullName(signUpRequest.getFullName())
				.lastSignIn(new Timestamp(System.currentTimeMillis())).build();

		User user = User.builder().username(username).password(passwordEncoder.encode(signUpRequest.getPassword()))
				.email(email).profile(profile).build();

		userRepository.save(user);

		// Sign in the user right away, since the approach does not require any further
		// validation actions, such as 2FA
		SignInRequest signInRequest = SignInRequest.builder().email(email).password(signUpRequest.getPassword())
				.build();
		return signIn(signInRequest);
	}

	public void updateUser(User user) {
		userRepository.save(user);
	}

	private Authentication authenticateCredentials(String email, String password) {
		Authentication authentication = null;
		try {
			authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		} catch (DisabledException e) {
			throw new DisabledUserException("User Inactive");
		} catch (BadCredentialsException e) {
			throw new InvalidUserCredentialsException("Invalid Credentials");
		}
		return authentication;
	}
}
