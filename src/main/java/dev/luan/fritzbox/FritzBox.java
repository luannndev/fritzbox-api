package dev.luan.fritzbox;

import dev.luan.fritzbox.model.FritzBoxLanguage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.java.Log;

import java.net.http.HttpClient;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Log
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FritzBox {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(
            "[dd.MM.yyyy HH:mm]" +
                    "[dd.MM.yyyy H:mm]" +
                    "[dd.MM.yyyy H:m]"
    );

    private static final String DEFAULT_DOMAIN = "fritz.box";
    private static final String DEFAULT_ADDRESS = "192.168.178.1";

    private static final String FAKE_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36";

    private static final String REST_LOGIN = "login_sid.lua";
    private static final String REST_DATA = "data.lua";

    @Getter(AccessLevel.NONE)
    HttpClient client;

    String fritzboxAddress;
    String username;
    String password;
    FritzBoxLanguage defaultLanguage;

    @NonFinal
    String sessionId;

    public FritzBox(String username, String password) {
        this(null, username, password, null);
    }

    public FritzBox(String address, String username, String password) {
        this(address, username, password, null);
    }

    public FritzBox(String address, String username, String password, FritzBoxLanguage language) {
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(20))
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();

        if (address == null || address.isEmpty()) {
            fritzboxAddress = DEFAULT_ADDRESS;
        } else {
            fritzboxAddress = address;
        }

        if (username == null || username.isEmpty()) {
            throw new NullPointerException("Given username cant be null or empty!");
        }
        this.username = username;

        if (password == null || password.isEmpty()) {
            throw new NullPointerException("Given password cant be null or empty!");
        }
        this.password = password;
        this.defaultLanguage = Objects.requireNonNullElse(language, FritzBoxLanguage.GERMAN);
    }

}
