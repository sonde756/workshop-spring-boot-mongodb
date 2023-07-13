package com.sonde.workshopmongodb.services;

import com.sonde.workshopmongodb.domain.User;
import com.sonde.workshopmongodb.dto.UserDTO;
import com.sonde.workshopmongodb.repository.UserRepository;
import com.sonde.workshopmongodb.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(String id) {
        Optional<User> optionalUser = repository.findById(id);

        return optionalUser.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }


    public User insert(User obj) {
        return repository.insert(obj);
    }

    public void delete(String id) {
        findById(id);
        repository.deleteById(id);
    }

    public User update(User obj) {
        User existingObj = repository.findById(obj.getId())
                .orElseThrow(() -> new IllegalArgumentException("Objeto não encontrado"));
        updateData(existingObj, obj);
        return repository.save(existingObj);
    }


    private void updateData(User newObj, User obj) {
        newObj.setName(obj.getName());
        newObj.setEmail(obj.getEmail());

    }

    public User fromDTO(UserDTO objDto) {
        return new User(objDto.getId(), objDto.getName(), objDto.getEmail());
    }


}
