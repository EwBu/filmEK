package pl.kowalska.filmek;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PasswordEncoder {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPass = "password";
        String encodedpass = encoder.encode(rawPass);

        System.out.println(encodedpass);
    }

}
