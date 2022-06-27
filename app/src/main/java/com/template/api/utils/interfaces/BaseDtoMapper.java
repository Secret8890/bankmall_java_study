package com.template.api.utils.interfaces;

import org.checkerframework.checker.units.qual.A;

public interface BaseDtoMapper<E, R, C , D> {

    R toResponse(E entity);

    E create(C dto);

    D create(A dto);

}
