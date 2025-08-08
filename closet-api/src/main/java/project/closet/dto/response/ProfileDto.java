package project.closet.dto.response;

import java.time.LocalDate;
import java.util.UUID;
import project.closet.entity.user.Gender;
import project.closet.entity.user.Profile;
import project.closet.entity.user.User;

public record ProfileDto(
    UUID userId,
    String name,
    Gender gender,
    LocalDate birthDate,
    WeatherAPILocation location,
    Integer temperatureSensitivity,
    String profileImageUrl
) {

    public static ProfileDto of(
        User user,
        WeatherAPILocation location,
        Profile profile,
        String profileImageUrl
    ) {
        return new ProfileDto(
            user.getId(),
            user.getName(),
            profile.getGender(),
            profile.getBirthDate(),
            location,
            profile.getTemperatureSensitivity(),
            profileImageUrl
        );
    }
}
