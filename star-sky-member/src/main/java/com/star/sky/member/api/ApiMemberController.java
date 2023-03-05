package com.star.sky.member.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/member")
public class ApiMemberController {

    @GetMapping("/getMember")
    public String getMember() {
        return "Helle world!!!";
    }

}
