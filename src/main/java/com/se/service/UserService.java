package com.se.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.se.annotation.CurrentUser;
import com.se.exception.AppException;
import com.se.exception.UserLogoutException;
import com.se.model.CustomUserDetails;
import com.se.model.Role;
import com.se.model.RoleName;
import com.se.model.User;
import com.se.model.UserDevice;
import com.se.model.payload.LogOutRequest;
import com.se.model.payload.RegistrationRequest;
import com.se.repository.RoleRepository;
import com.se.repository.UserRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private static final Logger logger = Logger.getLogger(UserService.class);
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserDeviceService userDeviceService;
    private final RefreshTokenService refreshTokenService;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleService roleService, UserDeviceService userDeviceService, RefreshTokenService refreshTokenService, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.userDeviceService = userDeviceService;
        this.refreshTokenService = refreshTokenService;
        this.roleRepository = roleRepository;
    }

    /**
     * Finds a user in the database by username
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public Optional<User> findByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    /**
     * Finds a user in the database by email
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Find a user in db by id.
     */
    public Optional<User> findById(Long Id) {
        return userRepository.findById(Id);
    }

    /**
     * Save the user to the database
     */
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * Check is the user exists given the email: naturalId
     */
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    
    /**
     * Check is the user exists given the email: naturalId
     */
    public Boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }

    /**
     * Check is the user exists given the username: naturalId
     */
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }


    /**
     * Creates a new user from the registration request
     */
    public User createUser(RegistrationRequest registerRequest) {
        User newUser = new User();
        String role = registerRequest.getRole();
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setUsername(registerRequest.getUsername());
//       
    
        Role userRole = getRolesForNewUser(role);
        
        newUser.setLastName(registerRequest.getLastname());
        newUser.setPhone(registerRequest.getPhone());
        newUser.setFirstName(registerRequest.getFirstname());
        newUser.setRoles(Collections.singleton(userRole));  
//        newUser.addRoles(getRolesForNewUser(isNewUserAsAdmin));
        newUser.setActive(true);
        newUser.setEmailVerified(false);
        return newUser;
    }

    /**
     * Performs a quick check to see what roles the new user could be assigned to.
     *
     * @return list of roles for the new user
     */
    private Role getRolesForNewUser(String role) {
//        Set<Role> newUserRoles = new HashSet<>(roleService.findAll());
    	Role userRole = new Role();
        if(role.equals("MERCHANT"))   
        {
            userRole = roleRepository.findByRole(RoleName.ROLE_MERCHANT)
                    .orElseThrow(() -> new AppException("User Role not set."));
        }
        
        else if(role.equals("CUSTOMER"))   
        {
            userRole = roleRepository.findByRole(RoleName.ROLE_CUSTOMER)
                    .orElseThrow(() -> new AppException("User Role not set."));
        }
        
        else if(role.equals("ADMIN"))   
        {
            userRole = roleRepository.findByRole(RoleName.ROLE_ADMIN)
                    .orElseThrow(() -> new AppException("User Role not set."));
        }
        
        logger.info("Setting user roles: " + userRole);
        return userRole;
    }

    /**
     * Log the given user out and delete the refresh token associated with it. If no device
     * id is found matching the database for the given user, throw a log out exception.
     */
    public void logoutUser(@CurrentUser CustomUserDetails currentUser, LogOutRequest logOutRequest) {
        String deviceId = logOutRequest.getDeviceInfo().getDeviceId();
        UserDevice userDevice = userDeviceService.findByUserId(currentUser.getId())
                .filter(device -> device.getDeviceId().equals(deviceId))
                .orElseThrow(() -> new UserLogoutException(logOutRequest.getDeviceInfo().getDeviceId(), "Invalid device Id supplied. No matching device found for the given user "));

        logger.info("Removing refresh token associated with device [" + userDevice + "]");
        refreshTokenService.deleteById(userDevice.getRefreshToken().getId());
    }
}
