package io.nitinpoc.tinder_ai_backend.profiles;

public record Profile(
        String id,
        String FirstName,
        String LastName,
        int age,
        String ethnicity,
        Gender gender,
        String bio,
        String imageUrl,
        String myersBriggsPersonalityType
) {
}