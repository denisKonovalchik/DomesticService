package by.konovalchik.domesticservice.cotrollerREST;

import by.konovalchik.domesticservice.configuration.jwt.JwtUtils;
import by.konovalchik.domesticservice.configuration.jwt.response.JwtResponse;
import by.konovalchik.domesticservice.dto.cardDTO.TaskCardDTO;
import by.konovalchik.domesticservice.dto.userDTO.AllArgUserDTO;
import by.konovalchik.domesticservice.dto.userDTO.UsernamePasswordUserDTO;
import by.konovalchik.domesticservice.entity.Task;
import by.konovalchik.domesticservice.entity.User;
import by.konovalchik.domesticservice.service.TaskService;
import by.konovalchik.domesticservice.service.UserService;
import by.konovalchik.domesticservice.utils.ControllerMessageManager;
import by.konovalchik.domesticservice.utils.ConverterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/guest")
public class GuestControllerREST {


    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    @Autowired
    TaskService taskService;


    @PostMapping("/reg")
    public ResponseEntity<?> registration (@Valid @RequestBody AllArgUserDTO userDTO){
        if (userService.isExistByUserName(userDTO.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(ControllerMessageManager.REG_FAIL);
        }
        User user = ConverterDTO.getAllArgUsersDTO(userDTO);
        userService.save(user);
        return ResponseEntity.ok(ControllerMessageManager.REG_SUCCESSFULLY);
    }



    @PostMapping("/auth")
    public ResponseEntity<?> auth (@Valid @RequestBody UsernamePasswordUserDTO userAuthDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userAuthDTO.getUsername(), userAuthDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        User userDetails = (User) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(JwtResponse.builder()
                .token(jwt)
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .roles(roles)
                .build());
    }


}
