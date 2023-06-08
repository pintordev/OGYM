package com.ogym.project.user;

import com.ogym.project.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.zip.DataFormatException;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public SiteUser getUserByLoginId(String loginId) {
        Optional<SiteUser> _siteUser = this.userRepository.findByLoginId(loginId);
        if (_siteUser.isPresent()) {
            return _siteUser.get();
        } else {
            throw new DataNotFoundException("user not found");
        }
    }
}
