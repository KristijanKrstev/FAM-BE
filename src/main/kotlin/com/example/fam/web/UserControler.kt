package com.example.fam.web

import com.example.fam.domain.User
import com.example.fam.domain.exception.InvalidUser
import com.example.fam.payload.JWTLoginSuccessResponse
import com.example.fam.payload.LoginRequest
import com.example.fam.security.JwtTokenProvider
import com.example.fam.service.MapValidationErrorService
import com.example.fam.service.UserService
import com.example.fam.validator.UserValidator
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.MimeTypeUtils
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.security.Principal
import java.util.*
import javax.validation.Valid

@CrossOrigin
@RestController
@RequestMapping(path = ["/api/users"], produces = [MimeTypeUtils.APPLICATION_JSON_VALUE])
class UserControler(val userService: UserService, val mapValidationErrorService: MapValidationErrorService, val userValidator: UserValidator,
                    val tokenProvider: JwtTokenProvider, val authenticationManager: AuthenticationManager) {

    @PostMapping("/login")
    fun authenticateUser(@RequestBody loginRequest: LoginRequest, bindingResult: BindingResult)
            : ResponseEntity<*> {
        val errorMap = mapValidationErrorService.mapValidationService(bindingResult)
        if (errorMap != null) return errorMap;

        val authentication: Authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                        loginRequest.email,
                        loginRequest.password
                )
        )

        SecurityContextHolder.getContext().authentication = authentication
        val jwt: String = "Bearer " + tokenProvider.generateToken(authentication)
        val s = JWTLoginSuccessResponse(true, jwt, getUserByEmail(loginRequest.email))
        return ResponseEntity.ok(s)
    }

    private fun getUserByEmail(email: String): User {
        return userService.getAllUsers(email)[0]
    }

    @PostMapping("/register")
    fun create(@RequestBody @Valid user: User, result: BindingResult): ResponseEntity<*>? {
        userValidator.validate(user, result)
        val errorMap = mapValidationErrorService.mapValidationService(result)
        if (errorMap != null) return errorMap
        return userService.createUser(user).map {
            ResponseEntity.ok(it)
        }.orElseThrow {
            InvalidUser("Email ${user.email} or UserName ${user.name} already exits")
        }
    }

    @GetMapping
    fun getAllUsers(principal: Principal): List<User> {
        return userService.getAllUsers(principal.name)
    }

    @GetMapping(params = ["term"])
    fun searchUsers(@RequestParam term: String): List<User> {
        return userService.searchUser(term)
    }

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: Int, principal: Principal): User {
        return userService.findById(userId, principal.name).orElseThrow { InvalidUser("") }
    }

    @PutMapping("/{uId}")
    fun updateUser(@PathVariable uId: Int, @RequestBody user: User, principal: Principal): User? {
        return userService.updateUser(uId, user.name, user.dateOfBirth, user.email, principal.name)
    }

    @PutMapping("/password/{uId}")
    fun updatePassword(@PathVariable uId: Int, @RequestBody user: UpdateUser, principal: Principal): User {
        return userService.updatePassword(uId, user.password, user.confirmPassword, principal.name)
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Int, principal: Principal) {
        userService.deleteUser(id, principal.name)
    }
}