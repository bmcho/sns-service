package com.service.sns.controller;

import com.service.sns.controller.request.UserSignUpRequest;
import com.service.sns.controller.response.Response;
import com.service.sns.controller.response.UserSignUpResponse;
import com.service.sns.model.User;
import com.service.sns.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public Response<UserSignUpResponse> signUp(@RequestBody UserSignUpRequest request) {
        User user = userService.signUp(request.getUserName(),request.getPassword());
        return Response.success(UserSignUpResponse.fromUser(user));
    }
}
