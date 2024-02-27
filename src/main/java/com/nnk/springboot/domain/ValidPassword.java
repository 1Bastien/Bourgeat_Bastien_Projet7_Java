package com.nnk.springboot.domain;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.nnk.springboot.domain.validation.PasswordValidator;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
	String message() default "Le mot de passe doit contenir au moins une lettre majuscule, une lettre minuscule, un chiffre et un symbole ainsi que faire minimum 8 caractères.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
