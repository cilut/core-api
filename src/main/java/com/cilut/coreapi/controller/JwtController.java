package com.cilut.coreapi.controller;

import com.cilut.coreapi.dto.UserDto;
import com.cilut.coreapi.request.JwtRequest;
import com.cilut.coreapi.response.JwtResponse;
import com.cilut.coreapi.jwtconfig.JwtUtil;
import com.cilut.coreapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/core-api/api/auth/")
@RequiredArgsConstructor
@CrossOrigin("*")
public class JwtController {

    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;


    @PostMapping("/generate-token")
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest){
        System.out.println(jwtRequest);

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtRequest.username());

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.username(), jwtRequest.password()));
            String generatedToken = jwtUtil.generateToken(userDetails);
            UserDto userDto = this.userService.getUser(jwtRequest.username());

            JwtResponse jwtResponse = new JwtResponse(generatedToken, userDto);

            return new ResponseEntity<>(jwtResponse, HttpStatus.OK);

        }catch (RuntimeException e){
            throw new BadCredentialsException("Incorrect Email or Password!!! " + e.getMessage());
        }
    }


}
