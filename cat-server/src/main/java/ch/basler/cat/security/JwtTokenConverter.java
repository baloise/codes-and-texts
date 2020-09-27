/*
 * Copyright 2020 Baloise Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.basler.cat.security;

import net.minidev.json.JSONObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/** JWT converter that takes the roles from 'groups' claim of JWT token. */
public class JwtTokenConverter
        implements Converter<Jwt, AbstractAuthenticationToken> {
    private static final String REALM_ACCESS_CLAIM = "realm_access";
    private static final String ROLE_PREFIX = "ROLE_";


    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = extractAuthorities(jwt);
        return new UsernamePasswordAuthenticationToken(jwt.getClaimAsString("email"), "n/a", authorities);
    }

    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        return this.getRoles(jwt).stream()
                .map(authority -> ROLE_PREFIX + authority.toUpperCase())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private Collection<String> getRoles(Jwt jwt) {
        Object realms = jwt.getClaims().get(REALM_ACCESS_CLAIM);

        if (realms instanceof JSONObject) {
            JSONObject realmsObj = (JSONObject) realms;
            Object roles = realmsObj.get("roles");

            if (roles instanceof Collection) {
                return (Collection<String>) roles;
            }
        }

        return Collections.emptyList();
    }
}
