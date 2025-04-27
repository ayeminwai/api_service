package com.maybank.apiservice.service.impl;

import com.maybank.apiservice.constant.AppStatus;
import com.maybank.apiservice.dto.KycUserResponse;
import com.maybank.apiservice.dto.UpdateUserRequest;
import com.maybank.apiservice.dto.UserRequest;
import com.maybank.apiservice.exception.PaymentException;
import com.maybank.apiservice.model.User;
import com.maybank.apiservice.repository.UserRepository;
import com.maybank.apiservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final WebClient webClient;
    private final UserRepository userRepository;

    public UserServiceImpl(WebClient webClient, UserRepository userRepository) {
        // url should come from application.properties
        this.webClient = webClient;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User createUser(UserRequest request) {
        logger.info("Creating user: {}", request);
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUser(UpdateUserRequest request) {
        logger.info("Updating user: {}", request);
        User user = userRepository.findById(request.getId())
                .orElseThrow(() -> new PaymentException(AppStatus.APP_ERROR.getCode(), "User not found"));

        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }

        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new PaymentException(AppStatus.APP_ERROR.getCode(), "User not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public KycUserResponse callExternalKyc(Long id) {
        ;
        return getExternalKycUserResponse(id);
    }

    private KycUserResponse getExternalKycUserResponse(Long id) {
        return webClient.get()
                // end point should come from application.properties
                .uri(uriBuilder -> uriBuilder.path("/users/{id}")
                        .build(id))
                .retrieve()
                .bodyToMono(KycUserResponse.class)
                .block();
    }

}
