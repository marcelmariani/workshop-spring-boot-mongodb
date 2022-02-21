package com.marcelmariani.workshopmongo.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<User> findOneAtividadeById(@PathVariable String id) {
        return this.service.findById(id).map(user -> ResponseEntity.ok(user))
                .orElse(ResponseEntity.notFound().build());
    }
}