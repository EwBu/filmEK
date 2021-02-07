package pl.kowalska.filmek;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class MoviesRepositoryTest {

    @Autowired
    private OmdbWebServiceClient omdbWebServiceClient;

    @Test
    public void testSearchMovies() {
    }

}
