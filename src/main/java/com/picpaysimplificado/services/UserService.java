package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.domain.user.UserType;
import com.picpaysimplificado.dtos.UserDTO;
import com.picpaysimplificado.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public void validateTransaction(User sender, BigDecimal amount) throws Exception {
        if(sender.getUserType() == UserType.MERCHANT ) {
            throw new Exception("Usuário do tipo logista não está autorizado a realizar transação");
        }

        if(sender.getBalance().compareTo(amount) < 0) {
            throw new Exception("Saldo insuficiente");
        }
    }

    public User findUserById(Long id) throws Exception {
        return this.repository.findUserById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

    public User createUser(UserDTO data) {
       User newUser = new User(data);
       String encryptPassword = new BCryptPasswordEncoder().encode(data.password());
       newUser.setPassword(encryptPassword);
       this.saveUser(newUser);
       return newUser;
    }

    public List<User> getAllUsers() {
        return this.repository.findAll();

    }

    public void saveUser(User user) {
        this.repository.save(user);
    }
}
