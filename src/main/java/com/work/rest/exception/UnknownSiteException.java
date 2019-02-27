package com.work.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason="UNKNOWN_SITE")
public class UnknownSiteException extends Exception {}
