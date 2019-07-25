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
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.runners.MockitoJUnitRunner
import org.springframework.http.ResponseEntity

@RunWith(MockitoJUnitRunner::class)
class UserControllerTest {

    @Mock
    private lateinit var mockUserService: UserService

    @Mock
    private lateinit var mockJwtBuilder: JwtBuilder

    @InjectMocks
    private lateinit var controller: UserController

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `returns JWT on user creation`() {
        val expectedJwt = "I am a user JWT"
        val createUserRequest = CreateUserRequest(
                userName = "username",
                password = "Password1",
                captchaResponse = "captcha"
        )
        val savedUser = User(1, "username", "hashedPassword")

        `when`(mockUserService.createUser(any())).thenReturn(savedUser)
        `when`(mockJwtBuilder.buildJwt("username")).thenReturn(expectedJwt)

        val responseEntity: ResponseEntity<String?> = controller.createUser(createUserRequest)

        assertEquals(expectedJwt, responseEntity.body)
    }
}

fun <T> any(): T {
    Mockito.any<T>()
    return uninitialized()
}

private fun <T> uninitialized(): T = null as T