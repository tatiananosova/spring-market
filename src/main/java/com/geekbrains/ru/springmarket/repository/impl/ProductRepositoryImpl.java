package com.geekbrains.ru.springmarket.repository.impl;

import com.geekbrains.ru.springmarket.domain.CategoryEntity;
import com.geekbrains.ru.springmarket.domain.ProductEntity;
import com.geekbrains.ru.springmarket.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final DataProductRepository dataProductRepository;

    private DataSource dataSource;

    @Override
    public Page<ProductEntity> findAllByCategories(CategoryEntity category, Pageable pageable) {
        return dataProductRepository.findAllByCategories(category, pageable);
    }

    @Override
    public ProductEntity findNativeProduct() {
//        Resource resource = new ClassPathResource("");
//        try {
//            ScriptUtils.executeSqlScript(dataSource.getConnection(), resource);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        ProductRowMapper productRowMapper = new ProductRowMapper();
//        List<ProductEntity> productEntities = jdbcTemplate.queryForList("select * from product", ProductEntity.class);
//        ProductEntity product = jdbcTemplate.queryForObject("select id, title from product", productRowMapper);

        Map<String, Object> map = new HashMap<>();
        map.put("productId", 1L);
        map.put("productTitle", "title");
        ProductEntity query = namedParameterJdbcTemplate
                .queryForObject("select id, title from product where id = :productId and title like :productTitle",
                        map,
                        productRowMapper);
//
        return null;
    }

    @Override
    public Page<ProductEntity> findAll(Pageable pageRequest) {
        return dataProductRepository.findAll(pageRequest);
    }

    @Override
    public ProductEntity save(ProductEntity product) {
        return dataProductRepository.save(product);
    }

    @Override
    public void deleteById(Long productId) {
        dataProductRepository.deleteById(productId);
    }

    @Override
    public Optional<ProductEntity> findById(long id) {
        return dataProductRepository.findById(id);
    }

    @Override
    public Optional<ProductEntity> findByTitle(String title) {
        return dataProductRepository.findAll().stream().filter(productEntity -> productEntity.getTitle().equals(title)).findFirst();
    }

    @Override
    public List<ProductEntity> findAll() {
        return dataProductRepository.findAll();
    }

    private class ProductRowMapper implements RowMapper<ProductEntity> {

        @Override
        public ProductEntity mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            long id = resultSet.getLong("id");
            String title = resultSet.getString("title");

            return ProductEntity.builder()
                    .id(id)
                    .title(title)
                    .build();
        }
    }
//
//    private class ProductExtractor implements ResultSetExtractor<ProductEntity> {
//
//        @Override
//        public ProductEntity extractData(ResultSet resultSet) throws SQLException, DataAccessException {
//            while (resultSet.next()) {
//                String productId = resultSet.getString("id");
//                String productTitle = resultSet.getString("title");
//                String catId = resultSet.getString("id");
//                String catName = resultSet.getString("name");
//            }
//            return null;
//        }
//    }

}
