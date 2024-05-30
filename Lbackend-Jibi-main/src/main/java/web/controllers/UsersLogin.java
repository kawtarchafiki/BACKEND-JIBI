package web.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.request.LoginRequest;
import web.responses.JwtAgentResponse;
import web.responses.JwtResponse;
import web.security.AgentDetails;
import web.security.ClientDetails;
import web.security.JwtUtils;
import web.security.AdminDetails;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class UsersLogin {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/Agentlogin")
    public JwtAgentResponse authenticateUserAgent(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        List<String> roleAuthentification = authentication.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());


        if (roleAuthentification.get(0).contentEquals("ROLE_AGENT")){

            AgentDetails userDetails = (AgentDetails) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            return new JwtAgentResponse(jwt,
                    userDetails.getId()
                    ,userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles.toString()
                    ,userDetails.getNom(),
                    userDetails.getPrenom(),

                    userDetails.getAdress(),
                    userDetails.getDateNaissance(),

                    userDetails.getIdType(),
                    userDetails.getNumId(),
                    userDetails.getNumMatricule(),
                    userDetails.getNumPatente(),
                    userDetails.getPhone(),
                    userDetails.getPassword());

        }
        return null;
    }
    //client login
    @PostMapping("/clientLogin")
    public ResponseEntity<?> LoginClient(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        List<String> roleAuthentification = authentication.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());


        if (roleAuthentification.get(0).contentEquals("ROLE_CLIENT")) {
            ClientDetails userDetails = (ClientDetails) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            return  ResponseEntity.ok(new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles.toString()));
        }
        return null;
    }
    @PostMapping("/adminSignin")
    public ResponseEntity<?> authenticateUserAdmin(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        List<String> roleAuthentification = authentication.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());


        if (roleAuthentification.get(0).contentEquals("ROLE_ADMIN")){
            AdminDetails userDetails = (AdminDetails) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            return  ResponseEntity.ok(new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles.toString()));

        }

        return null;
    }


}
