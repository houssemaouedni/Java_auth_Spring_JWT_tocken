package com.facturePro.FacturePro.services.Impl;

import com.facturePro.FacturePro.dto.JwtAuthResponse;
import com.facturePro.FacturePro.dto.LoginDto;
import com.facturePro.FacturePro.dto.RegisterDto;
import com.facturePro.FacturePro.entity.Role;
import com.facturePro.FacturePro.entity.User;
import com.facturePro.FacturePro.exception.TodoAPIException;
import com.facturePro.FacturePro.repository.RoleRepository;
import com.facturePro.FacturePro.repository.UserRepository;
import com.facturePro.FacturePro.security.JwtTokenProvider;
import com.facturePro.FacturePro.services.AuthService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    @Transactional
    public String register(RegisterDto registerDto) {
        // check username is already exist
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new TodoAPIException(HttpStatus.BAD_REQUEST,"Username is already taken!");
        }
        // check email is already exist
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new TodoAPIException(HttpStatus.BAD_REQUEST,"Email is already taken!");
        }
        // map dto to entity
        User user = modelMapper.map(registerDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new TodoAPIException(HttpStatus.BAD_REQUEST,"Role not found."));
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);

        return "User registered successfully";
    }

    @Override
    public JwtAuthResponse login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String role = jwtTokenProvider.authorize(authentication);
        String token = jwtTokenProvider.generateJwtToken(authentication);

        return new JwtAuthResponse(token,role);
    }
}
