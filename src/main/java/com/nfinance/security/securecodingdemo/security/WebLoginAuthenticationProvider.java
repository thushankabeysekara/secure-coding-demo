package com.nfinance.security.securecodingdemo.security;

import com.nfinance.security.securecodingdemo.entity.User;
import com.nfinance.security.securecodingdemo.service.UserService;
import com.nfinance.security.securecodingdemo.validator.InputValidator;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class WebLoginAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;

    private final UserDetailsService userDetailsService;

    private final InputValidator inputValidator;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        inputValidator.isValidUsername(username);
        inputValidator.isValidPassword(password);

        Optional<User> user = userService.findUserByUsernameAndPassword(username, password);

        if (user.isEmpty()) {
            throw new BadCredentialsException("Invalid username and password!");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.get().getUsername());

        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authenticationClass) {
        return authenticationClass.equals(UsernamePasswordAuthenticationToken.class);
    }

}
