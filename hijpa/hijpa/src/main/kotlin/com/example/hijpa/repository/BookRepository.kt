package com.example.hijpa.repository

import com.example.hijpa.model.Book
import com.example.hijpa.model.BookShelf
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository: JpaRepository<Book, Long>

interface BookShelfRepository: JpaRepository<BookShelf, Long>
