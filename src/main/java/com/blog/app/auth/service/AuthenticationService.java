package com.blog.app.auth.service;

import com.blog.app.auth.dto.request.SignInRequest;
import com.blog.app.auth.dto.request.SignUpRequest;
import com.blog.app.auth.dto.response.SignInResponse;

public interface AuthenticationService {

	SignInResponse signIn(SignInRequest request);

	SignInResponse signUp(SignUpRequest signUpRequest);
}
