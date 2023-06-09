package com.service.sns.controller;

import com.service.sns.controller.request.UserLoginRequest;
import com.service.sns.controller.request.UserSignUpRequest;
import com.service.sns.controller.response.AlarmResponse;
import com.service.sns.controller.response.Response;
import com.service.sns.controller.response.UserLoginResponse;
import com.service.sns.controller.response.UserSignUpResponse;
import com.service.sns.model.User;
import com.service.sns.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public Response<UserSignUpResponse> signUp(@RequestBody UserSignUpRequest request) {
        User user = userService.signUp(request.getName(), request.getPassword());
        return Response.success(UserSignUpResponse.fromUser(user));
    }

    @PostMapping("/sign-in")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
        String token = userService.login(request.getName(), request.getPassword());
        return Response.success(new UserLoginResponse(token));
    }

    @GetMapping("/alarm")
    public Response<Page<AlarmResponse>>alarm(Pageable pageable, Authentication authentication) {
        return Response.success(userService.alarmList(authentication.getName(), pageable).map(AlarmResponse::fromAlarm));
    }



}
