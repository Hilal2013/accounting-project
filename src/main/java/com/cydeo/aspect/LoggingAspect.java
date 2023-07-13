package com.cydeo.aspect;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.service.CompanyService;
import com.cydeo.service.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    private final CompanyService companyService;
    private final SecurityService securityService;

    public LoggingAspect(CompanyService companyService, SecurityService securityService) {
        this.companyService = companyService;
        this.securityService = securityService;
    }

    private String getCompanyTitle() {
        return companyService.getCompanyDTOByLoggedInUser().getTitle();
    }

    private UserDTO getUserInfo() {
        return securityService.getLoggedInUser();
    }

    @Pointcut("execution(* com.cydeo.controller.CompanyController.activateCompany(..))")
    public void logActivateCompany() {
    }
    @Pointcut("execution(* com.cydeo.controller.CompanyController.deactivateCompany(..))")
    public void logDeactivateCompany() {
    }
    @AfterReturning(pointcut = "logActivateCompany()", returning = "results")
    public void afterLogActivateCompany(JoinPoint joinPoint, Object results) {
        log.info("After Returning ->Method Name: {},Company Name: {},First name: {},Last Name: {}, Username of the logged-in user: {}"
                , joinPoint.getSignature().toString()
                , getCompanyTitle()
        ,getUserInfo().getFirstname()
        ,getUserInfo().getLastname()
        ,getUserInfo().getUsername());
    }
    @AfterReturning(pointcut = "logDeactivateCompany()", returning = "results")
    public void afterLogDeactivateCompany(JoinPoint joinPoint, Object results) {
        log.info("After Returning ->Method Name: {},Company Name: {}, First name: {},Last Name: {}, Username of the logged-in user: {}"
                , joinPoint.getSignature().toString()
                , getCompanyTitle()
                ,getUserInfo().getFirstname()
                ,getUserInfo().getLastname()
                ,getUserInfo().getUsername());
    }
}
