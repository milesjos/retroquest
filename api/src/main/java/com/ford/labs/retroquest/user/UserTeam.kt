package com.ford.labs.retroquest.user

import com.ford.labs.retroquest.team.Team
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "USER_TEAM")
class UserTeam : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User? = null

    @Id
    @ManyToOne
    @JoinColumn(name = "team_id")
    val team: Team? = null

    @Column(name = "is_admin")
    val isAdmin: Boolean = false
}
