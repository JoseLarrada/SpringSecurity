package com.ejercicios.SpringSecurity.Service;

import com.ejercicios.SpringSecurity.Dto.AuthenticationRequest;
import com.ejercicios.SpringSecurity.Dto.AuthenticationResponse;
import com.ejercicios.SpringSecurity.Repository.UserRepository;
import com.ejercicios.SpringSecurity.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    public AuthenticationResponse login(AuthenticationRequest authRequest){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(),authRequest.getPassword()
        );
        authenticationManager.authenticate(authenticationToken);

        User user=userRepository.findByUsername(authRequest.getUsername()).get();

        String jwt=jwtService.generateToken(user,generateExtraClaims(user));
        return new AuthenticationResponse(jwt);
    }

    private Map<String,Object> generateExtraClaims(User user) {
        Map<String,Object> extraClaims= new HashMap<>();
        extraClaims.put("name",user.getName());
        extraClaims.put("role", user.getRole().name());
        return extraClaims;
    }
}
