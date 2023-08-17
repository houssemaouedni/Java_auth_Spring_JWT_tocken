package com.facturePro.FacturePro.services.Impl;

import com.facturePro.FacturePro.entity.Role;
import com.facturePro.FacturePro.entity.User;
import com.facturePro.FacturePro.repository.RoleRepository;
import com.facturePro.FacturePro.repository.UserRepository;
import com.facturePro.FacturePro.services.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        String roleName = user.getRoles().iterator().next().getName();
        Optional<Role> role = roleRepository.findByName(roleName);
        if (role.isPresent()) {
            user.setRoles(Set.of(role.get()));
        } else {
            user.setRoles(Set.of(user.getRoles().stream().findFirst().orElseThrow()));
        }

        return userRepository.save(user);
    }
}
