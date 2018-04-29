package team.benchem.usersystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.benchem.usersystem.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

}
