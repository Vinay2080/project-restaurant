package org.miniproject.restuarant.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

@Configuration
public class SessionConfig {

    private static final String SESSION_COOKIE_NAME = "JSESSIONID";
    private static final String SESSION_COOKIE_PATH = "/";
    // Use SameSite= None, so cookies can be sent on cross-site requests (e.g., via ngrok).
    // Requires Secure=true and HTTPS in the browser.
    private static final String SESSION_COOKIE_SAME_SITE = "None";
    private static final boolean SESSION_COOKIE_HTTP_ONLY = true;
    private static final boolean SESSION_COOKIE_SECURE = true;

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName(SESSION_COOKIE_NAME);
        serializer.setCookiePath(SESSION_COOKIE_PATH);
        serializer.setUseHttpOnlyCookie(SESSION_COOKIE_HTTP_ONLY);
        serializer.setUseSecureCookie(SESSION_COOKIE_SECURE);
        serializer.setSameSite(SESSION_COOKIE_SAME_SITE);
        return serializer;
    }

    @Bean
    public SessionSerializationMarker sessionSerializationMarker() {
        return new SessionSerializationMarker();
    }

    /**
     * Marker type used to prevent session serialization.
     * Instances of this class are not meant to carry any state.
     */
    public static final class SessionSerializationMarker {

        private SessionSerializationMarker() {
            // Intentionally empty: prevents external instantiation
        }
    }
}