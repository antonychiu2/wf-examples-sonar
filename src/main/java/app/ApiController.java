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

        String query = "SELECT user FROM users WHERE user = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
        }

        return new ResponseEntity<>("Authentication Success", HttpStatus.OK);
    }
}
