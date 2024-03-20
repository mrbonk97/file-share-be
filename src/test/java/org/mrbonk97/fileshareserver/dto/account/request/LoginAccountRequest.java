import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginAccountRequest {
    private final String username;
    private final String imageUrl;
    private final String accessToken;
    private final String refreshToken;
}
