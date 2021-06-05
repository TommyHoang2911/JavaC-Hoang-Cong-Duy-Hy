package com.example.finalproject.appuser;

import com.example.finalproject.registration.token.ConfirmationToken;
import com.example.finalproject.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "user with email %s not found .";
    private final static String USER_EXIST = "1";

    private final AppUserRepository appUserRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG,email)));
    }

    public List<AppUser> getAllUsers(){
        return appUserRepository.findAll();
    }

    public String signupUser(AppUser user){
        boolean userExist = appUserRepository.findByEmail(user.getEmail()).isPresent();

        if(userExist){
            return "1";
        }
//        Encode Password
        String encodePassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);

        appUserRepository.save(user);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }
}
