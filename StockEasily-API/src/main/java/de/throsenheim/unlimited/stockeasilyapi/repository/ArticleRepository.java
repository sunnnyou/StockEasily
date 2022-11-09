package de.throsenheim.unlimited.stockeasilyapi.repository;

import de.throsenheim.unlimited.stockeasilyapi.exception.NotImplementedException;
import de.throsenheim.unlimited.stockeasilyapi.factory.IDatabaseConnectionFactory;
import de.throsenheim.unlimited.stockeasilyapi.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Optional;

@Repository
public class ArticleRepository implements CrudRepository<Article, Integer> {

    private final Connection connection;

    @Autowired
    public ArticleRepository(IDatabaseConnectionFactory databaseConnectionFactory) {
        this.connection = databaseConnectionFactory.getConnection(false);
    }

    @Override
    public long count() {
        throw new NotImplementedException("ArticleRepository.count");
    }

    @Override
    public void deleteAll() {
        throw new NotImplementedException("ArticleRepository.deleteAll");
    }

    @Override
    public void deleteAll(Iterable<? extends Article> articles) {
        throw new NotImplementedException("ArticleRepository.deleteAll");
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> ids) {
        throw new NotImplementedException("ArticleRepository.deleteAllById");
    }

    @Override
    public void delete(Article articles) {
        throw new NotImplementedException("ArticleRepository.delete");
    }

    @Override
    public void deleteById(Integer id) {
        throw new NotImplementedException("ArticleRepository.deleteById");
    }

    @Override
    public boolean existsById(Integer id) {
        return findById(id).isPresent();
    }

    @Override
    public Iterable<Article> findAll() {
        throw new NotImplementedException("ArticleRepository.findAll");
    }

    @Override
    public Iterable<Article> findAllById(Iterable<Integer> ids) {
        throw new NotImplementedException("ArticleRepository.findAllById");
    }

    @Override
    public Optional<Article> findById(Integer id) {
////        try {
////            String table = "";
////            final String query = "SELECT * FROM articles WHERE id = ? LIMIT 1";
////            Statement statement = connection.createStatement();
////            ResultSet resultSet = statement.executeQuery(query);
////            if (resultSet.next()) {
////                Article result = new Article();
////                result.setId(id);
////                result.setName(resultSet.getString(""));
////                return
////            }
////        } catch (SQLException e) {
////            throw new RuntimeException(e);
////        }
        return Optional.empty();
    }

    @Override
    public <S extends Article> S save(S article) {
        return null;
    }

    @Override
    public <S extends Article> Iterable<S> saveAll(Iterable<S> articles) {
        return null;
    }
}
