package com.reloadly.transactionapi.configs;

import com.reloadly.transactionapi.helpers.AuthorisationHelper;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * To Authenticate User.
 * @see SecurityContextRepository
 *
 * To get current login info use AuthenticationManager.currentUser
 * or add "@CurrentSecurityContext(expression="authentication?.name") String loggedInEmail" to the controller
 */
@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final AuthorisationHelper authorisationHelper;
    public static AuthorisationHelper.Payload currentUser = new AuthorisationHelper.Payload();

    public AuthenticationManager(AuthorisationHelper authorisationHelper) {
        this.authorisationHelper = authorisationHelper;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication)  throws AuthenticationException {

        // Get Credential value(Jwt value).
        String authToken = authentication.getCredentials().toString();

        try {
            AuthorisationHelper.Payload payload = authorisationHelper.validateJwt(authToken);

            if (payload == null || !payload.isTokenValid())
                return Mono.empty();
                //throw new BadCredentialsException("The credentials provided are incorrect");

            // Add Roles as Authorities
            List<GrantedAuthority> authorities = new ArrayList<>();
            for (var role : payload.getRoles())
                authorities.add(new SimpleGrantedAuthority(role));

            // Authenticate Customer roles  in Authorities
            var auth = new UsernamePasswordAuthenticationToken(payload.getUserName(), payload, authorities);
            auth.setDetails(payload);
            currentUser = payload;
            return Mono.just(auth);

        } catch (Exception e) {
            //throw new BadCredentialsException("The credentials provided are incorrect. " + e.getMessage());
            return Mono.empty();
        }


    }
}
