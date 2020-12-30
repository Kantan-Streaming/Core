package de.jandev.core.web;

import de.jandev.core.exception.ApplicationException;
import de.jandev.core.utility.LogMessage;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;

public interface ApplicationRestController {

    default void checkAuthorizedUserOwnsRequestedResource(String id) throws ApplicationException {
        if (!(getAuthenticatedUserId().equals(id))) {
            throw new ApplicationException(HttpStatus.FORBIDDEN, MessageFormatter.format(LogMessage.USER_NO_PERMISSION, id).getMessage());
        }
    }

    default String getAuthenticatedUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }


}
