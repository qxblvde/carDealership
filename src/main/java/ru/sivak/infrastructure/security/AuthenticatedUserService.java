package ru.sivak.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.infrastructure.persistence.repository.AppUserEntityJpaRepository;

@Service
@RequiredArgsConstructor
public class AuthenticatedUserService {
    private final AppUserEntityJpaRepository appUserRepository;

    public Id getCurrentUserId() {
        String username = getCurrentUsername();
        return appUserRepository.findByUsernameAndRemovedFalse(username)
                .map(entity -> Id.of(entity.getId()))
                .orElseThrow(() -> new AccessDeniedException("Authenticated user is not mapped in app_users"));
    }

    public String getCurrentUsername() {
        Authentication authentication = requireAuthentication();

        String name = authentication.getName();
        if (name != null && !name.isBlank()) {
            return name;
        }

        throw new AccessDeniedException("Authenticated username cannot be resolved");
    }

    public boolean hasRole(String role) {
        Authentication authentication = requireAuthentication();

        for (GrantedAuthority authority : authentication.getAuthorities()) {
            if (("ROLE_" + role).equals(authority.getAuthority())) {
                return true;
            }
        }

        return false;
    }

    private Authentication requireAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
