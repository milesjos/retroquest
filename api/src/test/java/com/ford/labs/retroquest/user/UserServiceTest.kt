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

import com.ford.labs.retroquest.exception.UserNameTakenException
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.runners.MockitoJUnitRunner
import org.springframework.security.crypto.password.PasswordEncoder

@RunWith(MockitoJUnitRunner::class)
class UserServiceTest {
    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var passwordEncoder: PasswordEncoder

    @InjectMocks
    private lateinit var userService: UserService

    @Test
    fun `create should return user with hashed password when successful`() {
        val user = User(
                userName = "User1",
                password = "Password1"
        )

        val expectedUser = User(
                id = 1,
                userName = "User1",
                password = "encrypted-password"
        )

        `when`(passwordEncoder.encode("Password1")).thenReturn("encrypted-password")
        `when`(userRepository.save(any<User>())).thenReturn(expectedUser)

        val actualUser = userService.createUser(user)

        assertEquals(expectedUser, actualUser)
        verify(userRepository).save(user)
    }

    @Test(expected = UserNameTakenException::class)
    fun `should throw UserNameTakenException given user name has been used`() {
        val user1 = User(
                userName = "User1",
                password = "Password1"
        )

        val user2 = User(
                userName = "User1",
                password = "Password1"
        )

        val expectedUser = User(
                id = 1,
                userName = "User1",
                password = "encrypted-password"
        )

        `when`(passwordEncoder.encode("Password1")).thenReturn("encrypted-password")
        `when`(userRepository.save(any<User>())).thenReturn(expectedUser)
        `when`(userRepository.findByUserName("User1")).thenReturn(null, user1)

        userService.createUser(user1)
        userService.createUser(user2)
    }
}