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

package com.ford.labs.retroquest.api

import com.ford.labs.retroquest.user.UserRepository
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

class UserApiTest : ControllerTest() {
    private val VALID_PASSWORD = "Passw0rd"

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    @Throws(Exception::class)
    fun canCreateTeamWithValidTeamNameAndPassword() {
        val teamJsonBody = """{ "userName" : "BeachBums", "password" : "$VALID_PASSWORD"}"""

        val mvcResult = mockMvc.perform(post("/api/user")
                .contentType(APPLICATION_JSON)
                .content(teamJsonBody)).andReturn()


        val savedUser = userRepository.findAll()[0]

        assertEquals(201, mvcResult.response.status.toLong())
        assertEquals("BeachBums", savedUser.userName)
        assertEquals(60, savedUser.password.length.toLong())
        assertNotNull(mvcResult.response.contentAsString)
    }
}