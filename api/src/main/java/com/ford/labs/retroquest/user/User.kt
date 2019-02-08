package com.ford.labs.retroquest.user

import javax.persistence.*

@Entity
@Table(name = "user")
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,

        @Column(name = "user_name")
        val userName: String = "",

        @Column(name = "password")
        var password: String = "",

        @OneToMany(mappedBy = "user", cascade = arrayOf(CascadeType.ALL), orphanRemoval = true)
        val userTeams: Set<UserTeam> = setOf()
)