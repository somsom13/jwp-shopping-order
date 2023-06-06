package cart.repository;

import cart.dao.ProductDao;
import cart.dao.entity.ProductEntity;
import cart.domain.Product;
import cart.exception.notfoundexception.ProductNotFoundException;
import cart.repository.mapper.ProductMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {

    private final ProductDao productDao;

    public ProductRepository(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> findAll() {
        List<ProductEntity> productEntities = productDao.findAll();
        return productEntities.stream()
            .map(ProductMapper::toProduct)
            .collect(Collectors.toList());
    }

    public Product findById(Long productId) {
        ProductEntity productEntity = productDao.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException(productId));
        return ProductMapper.toProduct(productEntity);
    }

    public long save(Product product) {
        return productDao.save(ProductMapper.toProductEntity(product));
    }


    public void updateProduct(Long productId, Product product) {
        validateProductExistence(productId);
        productDao.updateProduct(productId, ProductMapper.toProductEntity(product));
    }

    public void deleteById(long productId) {
        validateProductExistence(productId);
        productDao.deleteById(productId);
    }

    private void validateProductExistence(long productId) {
        if (productDao.isNonExistingId(productId)) {
            throw new ProductNotFoundException(productId);
        }
    }
}
