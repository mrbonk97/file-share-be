package org.mrbonk97.fileshareserver.controller;

import org.mrbonk97.fileshareserver.controller.response.Response;
import org.mrbonk97.fileshareserver.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/test")
@RestController
public class TestController {
    @GetMapping
    public Response<String> test() {
        return Response.success("테스트 성공");
    }

    @GetMapping("/auth")
    public Response<String> authTest(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return Response.success("테스트 성공 유저: " + user.getId());
    }

}
