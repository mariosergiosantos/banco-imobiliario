package com.nossogame.bancoimobiliario.service.validation;

import com.nossogame.bancoimobiliario.exception.RegraNegocialException;

public interface Validate<T, Y> {

    T validate(Y source) throws RegraNegocialException;
}
