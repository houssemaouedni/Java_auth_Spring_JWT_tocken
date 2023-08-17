package com.facturePro.FacturePro.services;

import com.facturePro.FacturePro.dto.JwtAuthResponse;
import com.facturePro.FacturePro.dto.LoginDto;
import com.facturePro.FacturePro.dto.RegisterDto;

public interface AuthService {

    String register(RegisterDto registerDto);

    JwtAuthResponse login(LoginDto loginDto);
}
