package dao;

import model.Book;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private final DataSource dataSource;
    public BookDAO(DataSource dataSource){
        this.dataSource = dataSource;
    }

    // Helper method to get a connection
    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public boolean insertBook(Book book) throws SQLException {
        String sql = "INSERT INTO book (title, author, price) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setFloat(3, book.getPrice());
            return statement.executeUpdate() > 0;
        }
    }

    public List<Book> listAllBooks() throws SQLException {
        List<Book> listBook = new ArrayList<>();
        String sql = "SELECT * FROM book";

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                float price = resultSet.getFloat("price");

                Book book = new Book(id, title, author, price);
                listBook.add(book);
            }
            return listBook;
        }
    }

    public boolean deleteBook(Book book) throws SQLException {
        String sql = "DELETE FROM book WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, book.getId());
            return statement.executeUpdate() > 0;
        }
    }

    public boolean updateBook(Book book) throws SQLException {
        String sql = "UPDATE book SET title = ?, author = ?, price = ?";
        sql += " WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setFloat(3, book.getPrice());
            statement.setInt(4, book.getId());
            return statement.executeUpdate() > 0;
        }
    }

    public Book getBook(int id) throws SQLException {
        Book book = null;
        String sql = "SELECT * FROM book WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                float price = resultSet.getFloat("price");

                book = new Book(id, title, author, price);
            }
            return book;
        }
    }

}
