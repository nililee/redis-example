package com.lotte.redis.controller;

import java.io.*;

import javax.servlet.http.*;

import org.springframework.web.bind.annotation.*;

@RestController
public class RedisTestController {

    @GetMapping("/session")
    public void testSession(HttpServletRequest req, HttpServletResponse res) throws IOException {
        req.getSession().invalidate();

        HttpSession session = req.getSession(true);
        res.getWriter().println(session.getId());
    }

}
