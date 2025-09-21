import java.util.*;

class BookNotAvailableException extends Exception {
    public BookNotAvailableException(String message) {
        super(message);
    }
}

class Book {
    private String title;
    private String author;
    private boolean available;
    
    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.available = true;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public boolean isAvailable() {
        return available;
    }
    
    public void setAvailable(boolean available) {
        this.available = available;
    }
    
    @Override
    public String toString() {
        return title + " by " + author + " [" + (available ? "Available" : "Checked Out") + "]";
    }
}

public class LibraryManagementSystem {
    private List<Book> books;
    
    public LibraryManagementSystem() {
        books = new ArrayList<>();
    }
    
    public void addBook(String title, String author) {
        books.add(new Book(title, author));
        System.out.println("Book added: " + title + " by " + author);
    }
    
    public List<Book> searchByTitle(String keyword) {
        List<Book> results = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(book);
            }
        }
        return results;
    }
    
    public List<Book> searchByAuthor(String author) {
        List<Book> results = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                results.add(book);
            }
        }
        return results;
    }
    
    public void checkoutBook(String title) throws BookNotAvailableException {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                if (book.isAvailable()) {
                    book.setAvailable(false);
                    System.out.println("Book checked out: " + title);
                    return;
                } else {
                    throw new BookNotAvailableException("Book is not available: " + title);
                }
            }
        }
        throw new BookNotAvailableException("Book not found: " + title);
    }
    
    public void returnBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                book.setAvailable(true);
                System.out.println("Book returned: " + title);
                return;
            }
        }
        System.out.println("Book not found: " + title);
    }
    
    public void displayBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in the library");
            return;
        }
        
        System.out.println("Library Books:");
        for (int i = 0; i < books.size(); i++) {
            System.out.println((i + 1) + ". " + books.get(i));
        }
    }
    
    public static void main(String[] args) {
        LibraryManagementSystem library = new LibraryManagementSystem();
        
        library.addBook("Java Programming", "John Doe");
        library.addBook("Data Structures", "Jane Smith");
        library.addBook("Algorithms", "Bob Johnson");
        
        library.displayBooks();
        
        // Search for books
        System.out.println("\nSearch results for 'Java':");
        List<Book> javaBooks = library.searchByTitle("Java");
        for (Book book : javaBooks) {
            System.out.println("  " + book);
        }
        
        // Checkout a book
        try {
            library.checkoutBook("Java Programming");
            library.displayBooks();
            
            // Try to checkout the same book again
            library.checkoutBook("Java Programming");
        } catch (BookNotAvailableException e) {
            System.err.println("Error: " + e.getMessage());
        }
        
        // Return the book
        library.returnBook("Java Programming");
        library.displayBooks();
    }
}