package org.mrbonk97.fileshareserver.oauth2;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.mrbonk97.fileshareserver.model.User;
import org.mrbonk97.fileshareserver.utils.CookieUtils;
import org.mrbonk97.fileshareserver.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;

@Setter
@Component
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${oauth2.redirect.uri}")
    private String [] REDIRECT_URIS;

    @Value("${oauth2.redirect.url}")
    private String REDIRECT_URL;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();

        String accessToken =
                JwtUtils.generateRefreshToken(user.getId());

        String refreshToken =
                JwtUtils.generateAccessToken(user.getId());


        ResponseCookie cookie = CookieUtils.generateRefreshToken(refreshToken);
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        String targetUri = determineTargetUrl(request, response, authentication);

        String uri = UriComponentsBuilder.fromUriString(targetUri)
                .queryParam("access_token", encodeUtf8(accessToken))
                .build()
                .toUriString();

        getRedirectStrategy().sendRedirect(request, response, uri);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtils
                .getCookie(request, "redirect_uri")
                .map(Cookie::getValue);

        // CSRF 공격방지
        if(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new RuntimeException("redirect Uri 적합하지 않음");
        }

        return redirectUri.orElse(REDIRECT_URL);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);

        return Arrays.stream(REDIRECT_URIS).anyMatch(
                e -> {
                    URI validRedirectUri = URI.create(e);
                    return validRedirectUri.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                            && validRedirectUri.getPort() == clientRedirectUri.getPort();
                }
        );
    }

    private static String encodeUtf8(String val) {
        return URLEncoder.encode(val, StandardCharsets.UTF_8);
    }


}