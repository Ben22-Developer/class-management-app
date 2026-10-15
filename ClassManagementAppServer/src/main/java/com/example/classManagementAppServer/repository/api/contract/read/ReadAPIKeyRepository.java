package com.example.classManagementAppServer.repository.api.contract.read;

import com.example.classManagementAppServer.dto.enums.*;
import com.example.classManagementAppServer.dto.user.*;

public interface ReadAPIKeyRepository {
    UserBuilder getUnsavedUserByUsername(UserBuilder userBuilder);
    int readTokenTypeID (TokenCategory tokenCategory);
    int readTokenNumAmount(int tokenTypeID);
    double readTokenCashAmount(int tokenTypeID);
}
