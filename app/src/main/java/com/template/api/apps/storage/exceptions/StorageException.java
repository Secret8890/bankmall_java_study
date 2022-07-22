package com.template.api.apps.storage.exceptions;

public class StorageException extends RuntimeException{


    /**
     * Instantiates a new Storage exception.
     *
     * @param message the message
     */

    public StorageException(String message){super(message);}


    /**
     * Instantiates a new Storage exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public StorageException(String message, Throwable cause){ super(message, cause);}
}
