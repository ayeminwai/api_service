package com.maybank.apiservice.service;

import com.maybank.apiservice.dto.KycUserResponse;
import com.maybank.apiservice.dto.UserRequest;
import com.maybank.apiservice.model.User;

public interface UserService {
    User createUser(UserRequest request);

    User getUser(Long id);

    KycUserResponse callExternalKyc(Long id);

}
