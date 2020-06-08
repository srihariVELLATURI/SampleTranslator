package com.se.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.se.exception.PasswordResetLinkException;
import com.se.exception.ResourceAlreadyInUseException;
import com.se.exception.ResourceNotFoundException;
import com.se.exception.TokenRefreshException;
import com.se.model.CustomUserDetails;
import com.se.model.PasswordResetToken;
import com.se.model.User;
import com.se.model.UserDevice;
import com.se.model.payload.LoginRequest;
import com.se.model.payload.PasswordResetLinkRequest;
import com.se.model.payload.PasswordResetRequest;
import com.se.model.payload.RegistrationRequest;
import com.se.model.payload.TemplateModel;
import com.se.model.payload.TokenRefreshRequest;
import com.se.model.token.EmailVerificationToken;
import com.se.model.token.RefreshToken;
import com.se.repository.UserRepository;
import com.se.security.JwtTokenProvider;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

@Service
public class AuthService {
	
	
	@Value("${app.velocity.templates.location}")
    private String basePackagePath;

    private static final Logger logger = Logger.getLogger(AuthService.class);
    private final UserService userService;
    private final JwtTokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailVerificationTokenService emailVerificationTokenService;
    private final UserDeviceService userDeviceService;
    private final PasswordResetTokenService passwordResetTokenService;
    private final UserRepository userRepository;
    private final Configuration templateConfiguration;

    @Autowired
    public AuthService(UserService userService, JwtTokenProvider tokenProvider, RefreshTokenService refreshTokenService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, EmailVerificationTokenService emailVerificationTokenService, UserDeviceService userDeviceService, PasswordResetTokenService passwordResetTokenService, UserRepository userRepository, Configuration templateConfiguration) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.refreshTokenService = refreshTokenService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.emailVerificationTokenService = emailVerificationTokenService;
        this.userDeviceService = userDeviceService;
        this.passwordResetTokenService = passwordResetTokenService;
        this.userRepository = userRepository;
        this.templateConfiguration = templateConfiguration;
    }

    /**
     * Registers a new user in the database by performing a series of quick checks.
     *
     * @return A user object if successfully created
     */
    public Optional<User> registerUser(RegistrationRequest newRegistrationRequest) {
        String newRegistrationRequestEmail = newRegistrationRequest.getEmail();
        String newRegistrationRequestUsername = newRegistrationRequest.getUsername();
        String newRegistrationRequestPhone = newRegistrationRequest.getPhone();
        
        if (emailAlreadyExists(newRegistrationRequestEmail)) {
            logger.error("Email already exists: " + newRegistrationRequestEmail);
            throw new ResourceAlreadyInUseException("Email", newRegistrationRequestEmail, "" );
        }
        
        else if (usernameAlreadyExists(newRegistrationRequestUsername)) {
            logger.error("Uername already exists: " + newRegistrationRequestUsername);
            throw new ResourceAlreadyInUseException("Username", newRegistrationRequestUsername, "");
        }
        
        else if (phoneAlreadyExists(newRegistrationRequestPhone)) {
            logger.error("Phone number already exists: " + newRegistrationRequestPhone);
            throw new ResourceAlreadyInUseException("Phone Number", newRegistrationRequestPhone, "");
        }
    
        logger.info("Trying to register new user [" + newRegistrationRequestEmail + "]");
        User newUser = userService.createUser(newRegistrationRequest);
        User registeredNewUser = userService.save(newUser);
        return Optional.ofNullable(registeredNewUser);
    }

    /**
     * Checks if the given email already exists in the database repository or not
     *
     * @return true if the email exists else false
     */
    public Boolean emailAlreadyExists(String email) {
        return userService.existsByEmail(email);
    }

    /**
     * Checks if the given email already exists in the database repository or not
     *
     * @return true if the email exists else false
     */
    public Boolean usernameAlreadyExists(String username) {
        return userService.existsByUsername(username);
    }
    
    
    /**
     * Checks if the given phone number already exists in the database repository or not
     *
     * @return true if the phone number exists else false
     */
    public Boolean phoneAlreadyExists(String phone) {
        return userService.existsByPhone(phone);
    }

    /**
     * Authenticate user and log them in given a loginRequest
     */
    public Optional<Authentication> authenticateUser(LoginRequest loginRequest) {
    		
    		
    	    Optional<User> byPhone = userRepository.findByPhone(loginRequest.getUsername());
    	    Optional<User> byId = userRepository.findByUsernameOrEmail(loginRequest.getUsername(), loginRequest.getUsername());
    	    User user;
    	    String email = null;
    	    String password = loginRequest.getPassword();
    	    
    	    if(byId.isPresent()) {          
    	        user = byId.get();
    	        email = byId.get().getEmail();
    	    }
    	    else if (byPhone.isPresent()) {
    	    	user = byPhone.get();
    	    	email = byPhone.get().getEmail();
    	    	
    		}
    	       	    	
    	    return Optional.ofNullable(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,
                loginRequest.getPassword())));
    }

    /**
     * Confirms the user verification based on the token expiry and mark the user as active.
     * If user is already verified, save the unnecessary database calls.
     * @throws TemplateException 
     * @throws IOException 
     */
    public String confirmEmailRegistration(String emailToken) throws IOException, TemplateException {
        EmailVerificationToken emailVerificationToken = emailVerificationTokenService.findByToken(emailToken)
                .orElseThrow(() -> new ResourceNotFoundException("Token", "Email verification", emailToken));

        User registeredUser = emailVerificationToken.getUser();
        if (registeredUser.getEmailVerified()) {
            logger.info("User [" + emailToken + "] already registered.");
            return "Email already verified";
        }

        emailVerificationTokenService.verifyExpiration(emailVerificationToken);
        emailVerificationToken.setConfirmedStatus();
        emailVerificationTokenService.save(emailVerificationToken);
        registeredUser.markVerificationConfirmed();
        userService.save(registeredUser);
                
//        Optional<User> checkNull =  Optional.of(registeredUser);   
//        		if (checkNull.isPresent()) {   
        			TemplateModel tm = new TemplateModel();
//         			tm.getModel().put("", );
           			templateConfiguration.setClassForTemplateLoading(getClass(), basePackagePath);
           			Template template = templateConfiguration.getTemplate("email-post-verification.html");
                	String Content = FreeMarkerTemplateUtils.processTemplateIntoString(template, tm.getModel());
               		return Content; 
//        		} 
//        		else  
//        			System.out.println("word is null");
  

//        Scanner scanner = new Scanner(new File(System.getProperty("user.dir")+"\\src\\main\\resources\\templates\\email-post-verification.txt"));
//        StringBuilder sb = new StringBuilder();
//        while(scanner.hasNextLine()) {
//            sb.append(scanner.nextLine());
//        }
//        
//        String message = sb.toString();
       
    }

    /**
     * Attempt to regenerate a new email verification token given a valid
     * previous expired token. If the previous token is valid, increase its expiry
     * else update the token value and add a new expiration.
     */
    public Optional<EmailVerificationToken> recreateRegistrationToken(String existingToken) {
        EmailVerificationToken emailVerificationToken = emailVerificationTokenService.findByToken(existingToken)
                .orElseThrow(() -> new ResourceNotFoundException("Token", "Existing email verification", existingToken));

        if (emailVerificationToken.getUser().getEmailVerified()) {
            return Optional.empty();
        }
        return Optional.ofNullable(emailVerificationTokenService.updateExistingTokenWithNameAndExpiry(emailVerificationToken));
    }

    /**
     * Validates the password of the current logged in user with the given password
     */
    private Boolean currentPasswordMatches(User currentUser, String password) {
        return passwordEncoder.matches(password, currentUser.getPassword());
    }

  

    /**
     * Generates a JWT token for the validated client
     */
    public String generateToken(CustomUserDetails customUserDetails) {
        return tokenProvider.generateToken(customUserDetails);
    }

    /**
     * Generates a JWT token for the validated client by userId
     */
    private String generateTokenFromUserId(Long userId) {
        return tokenProvider.generateTokenFromUserId(userId);
    }

    /**
     * Creates and persists the refresh token for the user device. If device exists
     * already, we don't care. Unused devices with expired tokens should be cleaned
     * with a cron job. The generated token would be encapsulated within the jwt.
     * Remove the existing refresh token as the old one should not remain valid.
     */
    public Optional<RefreshToken> createAndPersistRefreshTokenForDevice(Authentication authentication, LoginRequest loginRequest) {
        User currentUser = (User) authentication.getPrincipal();
        userDeviceService.findByUserId(currentUser.getId())
                .map(UserDevice::getRefreshToken)
                .map(RefreshToken::getId)
                .ifPresent(refreshTokenService::deleteById);

        UserDevice userDevice = userDeviceService.createUserDevice(loginRequest.getDeviceInfo());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken();
        userDevice.setUser(currentUser);
        userDevice.setRefreshToken(refreshToken);
        refreshToken.setUserDevice(userDevice);
        refreshToken = refreshTokenService.save(refreshToken);
        return Optional.ofNullable(refreshToken);
    }

    /**
     * Refresh the expired jwt token using a refresh token and device info. The
     * * refresh token is mapped to a specific device and if it is unexpired, can help
     * * generate a new jwt. If the refresh token is inactive for a device or it is expired,
     * * throw appropriate errors.
     */
    public Optional<String> refreshJwtToken(TokenRefreshRequest tokenRefreshRequest) {
        String requestRefreshToken = tokenRefreshRequest.getRefreshToken();

        return Optional.of(refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshToken -> {
                    refreshTokenService.verifyExpiration(refreshToken);
                    userDeviceService.verifyRefreshAvailability(refreshToken);
                    refreshTokenService.increaseCount(refreshToken);
                    return refreshToken;
                })
                .map(RefreshToken::getUserDevice)
                .map(UserDevice::getUser)
                .map(User::getId).map(this::generateTokenFromUserId))
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Missing refresh token in database.Please login again"));
    }

    /**
     * Generates a password reset token from the given reset request
     */
    public Optional<PasswordResetToken> generatePasswordResetToken(PasswordResetLinkRequest passwordResetLinkRequest) {
        String email = passwordResetLinkRequest.getEmail();
        return userService.findByEmail(email)
                .map(user -> {
                    PasswordResetToken passwordResetToken = passwordResetTokenService.createToken();
                    passwordResetToken.setUser(user);
                    passwordResetTokenService.save(passwordResetToken);
                    return Optional.of(passwordResetToken);
                })
                .orElseThrow(() -> new PasswordResetLinkException(email, "No matching user found for the given request"));
    }

    /**
     * Reset a password given a reset request and return the updated user
     */
    public Optional<User> resetPassword(PasswordResetRequest passwordResetRequest, String token) {
       
        PasswordResetToken passwordResetToken = passwordResetTokenService.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Password Reset Token", "Token Id", token));

        passwordResetTokenService.verifyExpiration(passwordResetToken);
        final String encodedPassword = passwordEncoder.encode(passwordResetRequest.getPassword());

        return Optional.of(passwordResetToken)
                .map(PasswordResetToken::getUser)
                .map(user -> {
                    user.setPassword(encodedPassword);
                    userService.save(user);
                    return user;
                });
    }
}
