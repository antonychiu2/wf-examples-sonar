package app;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;

@RestController
public class ApiController {
    Connection connection;

    {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "root");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(value = "/authenticate")
    @ResponseBody
    public ResponseEntity<String> authenticate(
            @RequestParam("user") String user) throws SQLException {

        String query = "SELECT user FROM users WHERE user = '" + user + "'";

        try (Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(query);

            if (!resultSet.next()) {
                return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
        }

        return new ResponseEntity<>("Authentication Success", HttpStatus.OK);
    }
}
