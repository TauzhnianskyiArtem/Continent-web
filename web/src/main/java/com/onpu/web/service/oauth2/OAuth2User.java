package com.onpu.web.service.oauth2;

import com.onpu.web.store.entity.UserEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class OAuth2User implements OidcUser, Serializable {

    static final long serialVersionUID = 1L;

    OidcUser oauthUser;

    UserEntity user;

    public OAuth2User(OidcUser oauthUser, UserEntity user) {
        this.oauthUser = oauthUser;
        this.user = user;
    }


    @Override
    public Map<String, Object> getClaims() {
        return oauthUser.getClaims();
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return oauthUser.getUserInfo();
    }

    @Override
    public OidcIdToken getIdToken() {
        return oauthUser.getIdToken();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oauthUser.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oauthUser.getAuthorities();
    }

    @Override
    public String getName() {
        return oauthUser.getName();
    }

    public String getUserName(){ return user.getName(); }

    public UserEntity getUser() {
        return user;
    }
}
