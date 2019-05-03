/*
 * Copyright (c) Akveo 2019. All Rights Reserved.
 * Licensed under the Personal / Commercial License.
 * See LICENSE_PERSONAL / LICENSE_COMMERCIAL in the project root for license information on type of purchased license.
 */

package com.akveo.bundlejava.authentication;

import com.akveo.bundlejava.authentication.resetpassword.RequestPasswordService;
import com.akveo.bundlejava.authentication.resetpassword.RestorePasswordService;
import com.akveo.bundlejava.authentication.resetpassword.ResetPasswordService;
import com.akveo.bundlejava.authentication.resetpassword.RestorePasswordDTO;
import com.akveo.bundlejava.authentication.resetpassword.RequestPasswordDTO;
import com.akveo.bundlejava.authentication.resetpassword.ResetPasswordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Collections;

import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final RequestPasswordService requestPasswordService;
    private final RestorePasswordService restorePasswordService;
    private final ResetPasswordService resetPasswordService;

    @Autowired
    public AuthController(AuthService authService,
                          RequestPasswordService requestPasswordService,
                          RestorePasswordService restorePasswordService,
                          ResetPasswordService resetPasswordService) {
        this.authService = authService;
        this.requestPasswordService = requestPasswordService;
        this.restorePasswordService = restorePasswordService;
        this.resetPasswordService = resetPasswordService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginDTO loginDTO) {
        Token token = authService.login(loginDTO);
        return toResponse(token);
    }

    @PostMapping("/restore-pass")
    public ResponseEntity restorePassword(@Valid @RequestBody RestorePasswordDTO restorePasswordDTO) {
        restorePasswordService.restorePassword(restorePasswordDTO);
        return ok("Password was restored");
    }

    @PostMapping("/sign-up")
    public ResponseEntity register(@Valid @RequestBody SignUpDTO signUpDTO) {
        Token token = authService.register(signUpDTO);
        return toResponse(token);
    }

    @PostMapping("/request-pass")
    public ResponseEntity requestPassword(@Valid @RequestBody RequestPasswordDTO requestPasswordDTO) {
        requestPasswordService.requestPassword(requestPasswordDTO);
        return ok("Ok");
    }

    @PostMapping("/sign-out")
    public ResponseEntity logout() {
        return ok("Ok");
    }

    @PostMapping("/reset-pass")
    public ResponseEntity resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        resetPasswordService.resetPassword(resetPasswordDTO);
        return ok("Password was reset");
    }

    @PostMapping("/refresh-token")
    public ResponseEntity refreshToken(@Valid @RequestBody RefreshTokenDTO refreshTokenDTO) {
        Token token = authService.refreshToken(refreshTokenDTO);
        return toResponse(token);
    }

    private ResponseEntity toResponse(Token token) {
        return ok(Collections.singletonMap("token", token));
    }
}
