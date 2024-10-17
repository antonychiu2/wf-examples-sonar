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

        String query = "SELECT user FROM users WHERE user = '?'";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            try {
                statement.setInt(1, Math.round(Float.parseFloat(user)));
            } catch (NumberFormatException e) {
                // MOBB: consider printing this message to logger: mobb-77deb6d83cc4f80b44f2a942cb026c6d: Failed to convert input to type integer

                // MOBB: using a default value for the SQL parameter in case the input is not convertible.
                // This is important for preventing users from causing a denial of service to this application by throwing an exception here.
                statement.setInt(1, 0);
            }
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
        }

        return new ResponseEntity<>("Authentication Success", HttpStatus.OK);
    }
}
