package com.carrot.market.global.config

import com.carrot.market.auth.application.AuthenticateService
import com.carrot.market.auth.ui.LoginPrincipalArgumentResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig: WebMvcConfigurer {

    @Autowired
    private lateinit var authenticateService: AuthenticateService

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(loginPrincipalArgumentResolver())
    }

    private fun loginPrincipalArgumentResolver(): LoginPrincipalArgumentResolver {
        return LoginPrincipalArgumentResolver(authenticateService)
    }
}
