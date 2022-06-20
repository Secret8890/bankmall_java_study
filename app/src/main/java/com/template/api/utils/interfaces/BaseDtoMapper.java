package com.template.api.utils.interfaces;

public interface BaseDtoMapper<E, R, C> {

    R toResponse(E entity);

    E create(C dto);

}
