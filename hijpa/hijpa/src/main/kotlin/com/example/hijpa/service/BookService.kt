package com.example.hijpa.service

import com.example.hijpa.model.Book
import com.example.hijpa.repository.BookRepository

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
class BookService (
    private val bookRepository: BookRepository
){

    @Transactional(readOnly = true)
    fun findAll():List<Book>{
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    fun findById(id:Long):Book?{
        return bookRepository.findById(id).getOrNull()
    }

    @Transactional
    fun save(book:Book):Book {
        return bookRepository.save(book)
    }

    @Transactional
    fun deleteById(id:Long){
        bookRepository.deleteById(id)
    }


}