/*
 * Copyright © 2019 Ford Motor Company
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ford.labs.retroquest.user

import com.ford.labs.retroquest.security.JwtBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URISyntaxException
import javax.transaction.Transactional
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/api"])
open class UserController {
    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var jwtBuilder: JwtBuilder

    @PostMapping("/user")
    @Transactional(rollbackOn = [URISyntaxException::class])
    open fun createUser(@RequestBody @Valid request: CreateUserRequest): ResponseEntity<String> {
        val user = userService.createUser(request.toUser())

        val jwt = jwtBuilder.buildJwt(user.userName)

        return ResponseEntity(jwt, HttpStatus.CREATED)
    }
}

private fun CreateUserRequest.toUser(): User {
    return User(userName = this.userName, password = this.password)
}
