package xurshid_azizbek.com.example.nasiyabackend.component;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import xurshid_azizbek.com.example.nasiyabackend.entity.User;
import xurshid_azizbek.com.example.nasiyabackend.entity.enums.Role;
import xurshid_azizbek.com.example.nasiyabackend.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (ddl.equals("create") || ddl.equals("create-drop")) {
            User user = new User();
            user.setFullName("Admin admin");
            user.setAge(20);
            user.setEmail("admin@gmail.com");
            user.setPassword(passwordEncoder.encode("admin123"));
            user.setRole(Role.ROLE_ADMIN);
            user.setEnabled(true);
            userRepository.save(user);
        }
    }
}
