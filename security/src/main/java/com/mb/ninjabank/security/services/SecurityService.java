package com.mb.ninjabank.security.services;

import java.util.Map;

public interface SecurityService {

    Map<String, String> getToken(String username, String password);
}
