package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    UserRepository repository;

    public boolean authTransaction(User sender, BigDecimal value) {
        String url = "https://util.devi.tools/api/v2/authorize";
        ResponseEntity<Map> authorizationResponse = this.restTemplate.getForEntity(url, Map.class);
        if(authorizationResponse.getStatusCode() == HttpStatus.OK) {
            String message = (String) authorizationResponse.getBody().get("status");
            return "success".equalsIgnoreCase(message);
        } else return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.repository.findByEmail(username);
    }
}
