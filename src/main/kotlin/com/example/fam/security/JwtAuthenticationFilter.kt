package com.example.fam.security

import com.example.fam.domain.User
import com.example.fam.service.impl.CustomUserDetailsService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationFilter(private val tokenProvider: JwtTokenProvider,
                              private val customUserDetailsService: CustomUserDetailsService)
    : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse,
                                  filterChain: FilterChain) {
        try {
            getJWTFromRequst(request).ifPresent {
                if (StringUtils.hasText(it) && tokenProvider.validateToken(it)) {
                    val userId = tokenProvider.getUserIdFromJWT(it)
                    val userDetails: User = customUserDetailsService.loadUserById(userId).orElseThrow()
                    val authentication = UsernamePasswordAuthenticationToken(userDetails,
                            null, Collections.emptyList())
                    authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = authentication;
                }
            }
        } catch (ex: Exception) {
            logger.error("Could not set user authentication in security context", ex);
        }
        filterChain.doFilter(request, response);
    }

    private fun getJWTFromRequst(request: HttpServletRequest): Optional<String> {
        val bearerToken = request.getHeader("Authorization")
        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            Optional.ofNullable(bearerToken.substring(7, bearerToken.length))
        } else Optional.empty()
    }
}