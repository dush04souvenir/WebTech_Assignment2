package com.assignment2.question1_library_api.model;

public class Book {

    private String isbn;
    private String title;
    private int publicationYear;

    public Book() {
    }

    public Book(String isbn, String title, int publicationYear) {
        this.isbn = isbn;
        this.title = title;
        this.publicationYear = publicationYear;
    }

    // Getters & Setters
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }
}
