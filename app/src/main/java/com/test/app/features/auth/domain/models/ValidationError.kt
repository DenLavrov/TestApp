package com.test.app.features.auth.domain.models

open class ValidationError : Throwable()

class UserNameValidationError : ValidationError()

class NameValidationError : ValidationError()
