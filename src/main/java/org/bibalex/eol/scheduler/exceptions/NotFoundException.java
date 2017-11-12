package org.bibalex.eol.scheduler.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by sara.mustafa on 4/20/17.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    public NotFoundException(String entity,long id) {
        super("could not find "+ entity +" with id "+ id );
    }
    public NotFoundException(String entity, String ids) {
        super("could not find "+ entity +" with id "+ ids );
    }
}
