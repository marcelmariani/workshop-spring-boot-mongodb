package com.marcelmariani.workshopmongo.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.marcelmariani.workshopmongo.domain.User;
import com.marcelmariani.workshopmongo.dto.UserDTO;
import com.marcelmariani.workshopmongo.repository.UserRepository;

@RestController
@RequestMapping(value="/users")
public class UserResource {
	
	@Autowired
	private UserRepository service;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<UserDTO>> findAll(){
		List<User> list = service.findAll();
		List<UserDTO> listDto = list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

    @GetMapping("/{id}")
    public ResponseEntity<User> findOneById(@PathVariable String id) {
        return this.service.findById(id).map(user -> ResponseEntity.ok(user))
                .orElse(ResponseEntity.notFound().build());
    }
    
   	@PostMapping
  	public User create(@RequestBody User objDto){
   		return service.save(objDto);
   	}
   	
   	@DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
   		service.deleteById(id);
   		return ResponseEntity.noContent().build();
    }
   	
    @PutMapping("/{id}")
    User update(@RequestBody User newUser, @PathVariable String id) {
      
      return service.findById(id).map(user -> {
          user.setName(newUser.getName());
          user.setEmail(newUser.getEmail());
          return service.save(user);
        })
        .orElseGet(() -> {
        	newUser.setId(id);
          return service.save(newUser);
        });

    }
}