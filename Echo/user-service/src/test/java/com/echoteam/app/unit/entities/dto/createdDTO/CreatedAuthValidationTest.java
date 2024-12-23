package com.echoteam.app.unit.entities.dto.createdDTO;

import com.echoteam.app.entities.dto.createdDTO.CreatedAuth;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CreatedAuthValidationTest {
    Validator validator;

    @BeforeEach
    void setValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void whenValidCreatedAuth_thenNoConstraintViolation() {
        // given
        CreatedAuth auth = CreatedAuth.getValidInstance();

        // when
        Set<ConstraintViolation<CreatedAuth>> violations = validator.validate(auth);

        // then
        assertThat(violations).isEmpty();
    }

    @Test
    void whenUserIdIsNull_thenHasConstraintViolation() {
        // given
        CreatedAuth auth = CreatedAuth.getValidInstance();
        auth.setUserId(null);

        // when
        Set<ConstraintViolation<CreatedAuth>> violations = validator.validate(auth);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("User id is required");
    }

    @Test
    void whenUserIdIsNegative_thenHasConstraintViolation() {
        // given
        CreatedAuth auth = CreatedAuth.getValidInstance();
        auth.setUserId(-1L);

        // when
        Set<ConstraintViolation<CreatedAuth>> violations = validator.validate(auth);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Id should be positive");
    }

    @Test
    void whenEmailIsNull_thenHasConstraintViolation() {
        // given
        CreatedAuth auth = CreatedAuth.getValidInstance();
        auth.setEmail(null);

        // when
        Set<ConstraintViolation<CreatedAuth>> violations = validator.validate(auth);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Email address is required");
    }

    @Test
    void whenEmailIsNotValid_thenHasConstraintViolation() {
        // given
        CreatedAuth auth = CreatedAuth.getValidInstance();
        auth.setEmail("hello my friend!");

        // when
        Set<ConstraintViolation<CreatedAuth>> violations = validator.validate(auth);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Incorrect email address");
    }

    @Test
    void whenEmailLengthIsTooMuch_thenHasConstraintViolation() {
        // given
        CreatedAuth auth = CreatedAuth.getValidInstance();
        auth.setEmail("very.long.email.address.that.exceeds.limit.but.is.still.valid@subdomain.example.the.typical.length.com");

        // when
        Set<ConstraintViolation<CreatedAuth>> violations = validator.validate(auth);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Email length shouldn`t be greater than 100 characters");
    }

    @Test
    void whenPasswordIsNull_thenHasConstraintViolation() {
        // given
        CreatedAuth auth = CreatedAuth.getValidInstance();
        auth.setPassword(null);

        // when
        Set<ConstraintViolation<CreatedAuth>> violations = validator.validate(auth);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Password is required");
    }

    @Test
    void whenPasswordLengthIsTooSmall_thenHasConstraintViolation() {
        // given
        CreatedAuth auth = CreatedAuth.getValidInstance();
        auth.setPassword("pass");

        // when
        Set<ConstraintViolation<CreatedAuth>> violations = validator.validate(auth);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Length should be between 8-255 inclusive");
    }

    @Test
    void whenPasswordLengthIsTooLarge_thenHasConstraintViolation() {
        // given
        CreatedAuth auth = CreatedAuth.getValidInstance();
        auth.setPassword("MFQWEFQGWEQYIUFOHGQWUIEFHQWHFWKQEJHFIQWUEYHFQWfdjsaffdwsfjkhqwekjrhflqwejhfwejkqhfjfhweqlkjfhwqkejfhMFQWEFQGWEQYIUFOHGQWUIEFHQWHFWKQEJHFIQWUEYHFQWfdjsaffdwsfjkhqwekjrhflqwejhfwejkqhfjfhweqlkjfhwqkejfhMFQWEFQGWEQYIUFOHGQWUIEFHQWHFWKQEJHFIQWUEYHFQfdjshalfjhsadf;ldsakf");

        // when
        Set<ConstraintViolation<CreatedAuth>> violations = validator.validate(auth);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Length should be between 8-255 inclusive");
    }

    @Test
    void whenPasswordHasProhibitedCharacters_thenHasConstraintViolation() {
        // given
        CreatedAuth auth = CreatedAuth.getValidInstance();
        auth.setPassword("password has white spaces");

        // when
        Set<ConstraintViolation<CreatedAuth>> violations = validator.validate(auth);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Password should be without: whitespaces");
    }

}
