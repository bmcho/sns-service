package com.service.sns.controller;

import com.service.sns.controller.request.UserLoginRequest;
import com.service.sns.controller.request.UserSignUpRequest;
import com.service.sns.controller.response.AlarmResponse;
import com.service.sns.controller.response.Response;
import com.service.sns.controller.response.UserLoginResponse;
import com.service.sns.controller.response.UserSignUpResponse;
import com.service.sns.exception.ErrorCode;
import com.service.sns.exception.SnsApplicationException;
import com.service.sns.model.User;
import com.service.sns.service.AlarmService;
import com.service.sns.service.UserService;
import com.service.sns.util.ClassUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AlarmService alarmService;

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
    public Response<Page<AlarmResponse>> alarm(Pageable pageable, Authentication authentication) {
        User user = ClassUtils.getSafeCastInstance(authentication.getPrincipal(), User.class).orElseThrow(() -> new SnsApplicationException(ErrorCode.INTERNAL_SERVER_ERROR, "Casting to user Class failed"));
        return Response.success(userService.alarmList(user.getId(), pageable).map(AlarmResponse::fromAlarm));
    }

    @GetMapping("/alarm/subscribe")
    public SseEmitter subscribe(Authentication authentication) {
        User user = ClassUtils.getSafeCastInstance(authentication.getPrincipal(), User.class).orElseThrow(() -> new SnsApplicationException(ErrorCode.INTERNAL_SERVER_ERROR, "Casting to user Class failed"));
        return alarmService.connectAlarm(user.getId());
    }
}
