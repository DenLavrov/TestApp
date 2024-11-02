package com.test.app.features.auth.domain.exceptions

open class ValidationException : Throwable()

class UserNameValidationException : ValidationException()

class NameValidationException : ValidationException()
