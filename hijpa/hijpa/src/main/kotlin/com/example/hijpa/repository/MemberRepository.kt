package com.example.hijpa.repository

import com.example.hijpa.model.BaseMember
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface MemberRepository:JpaRepository<BaseMember,Long>{

    fun findByEmailContaining(email:String):List<BaseMember>


    @Query("select count(distinct m.name) from BaseMember m where m.name=:name")
    fun countDistinctByName(name:String):Int



    @Query("select m from BaseMember m ")
    fun finAllMembersWithPagin(pageable:Pageable): Page<BaseMember>
}