package vn.io.vutiendat3601.imdb.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegistrationRequest(@Email String email, @NotBlank String password) {}
