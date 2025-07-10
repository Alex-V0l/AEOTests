package UI.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestUser {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String zipCode;
}
