package com.example.hijpa

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository:JpaRepository<BaseMember,Long>