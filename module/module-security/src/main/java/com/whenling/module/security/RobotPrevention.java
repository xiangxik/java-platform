package com.whenling.module.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public interface RobotPrevention {

	boolean validateRequest(ServletRequest request, ServletResponse response);

}
