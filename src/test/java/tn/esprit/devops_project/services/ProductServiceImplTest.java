package tn.esprit.devops_project.services;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;
import tn.esprit.devops_project.entities.Stock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@ActiveProfiles("test")
class ProductServiceImplTest {
    @Autowired
    private ProductServiceImpl productService;

    @Test
    @DatabaseSetup("/data-set/product.xml")
    void retreiveAllProduct() {
        final List<Product> allProducts = this.productService.retreiveAllProduct();
        assertEquals(allProducts.size(), 0);
    }
    @Test
    @DatabaseSetup("/data-set/product.xml")
    void retrieveProductByCategory() {
        ProductCategory categoryToRetrieve = ProductCategory.ELECTRONICS; // Replace with the desired category
        List<Product> products = productService.retrieveProductByCategory(categoryToRetrieve);
        // Print the size of the products list
        System.out.println("Number of products in category " + categoryToRetrieve + ": " + products.size());
        // Add assertions to check the results
        assertEquals(products.size(), 0);
    }


    @Test
    @DatabaseSetup("/data-set/product.xml")
    void retreiveProductStock() {
        final List<Product> allProductStock = this.productService.retreiveProductStock(1L);
        assertNotNull(allProductStock.size());
    }
    @Test
    @DatabaseSetup("/data-set/stock-data.xml")
    void retrieveProduct_nullId() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            final Product product = this.productService.retrieveProduct(100L);
        });
    }

    @Test
    @DatabaseSetup("/data-set/product-data.xml")
    void deleteProduct() {
        Long productId = 2L; // Supposons qu'un produit avec l'ID 1 existe dans vos données de test

        // Tentez de supprimer le produit
        productService.deleteProduct(productId);

        // Essayez de récupérer le produit
        try {
            productService.retrieveProduct(productId);
        } catch (NullPointerException e) {
            // L'exception a été levée, ce qui est attendu
            return;
        }

        fail("L'exception NullPointerException n'a pas été levée lors de la suppression du produit.");
    }
    @Test
    @DatabaseSetup("/data-set/product-data.xml")
    void addProduct() {
        Product product = new Product();
        product.setTitle("TitreDuProduit");
        product.setPrice(10.0f);
        product.setQuantity(5);
        product.setCategory(ProductCategory.CLOTHING);

        Long existingStockId = 2L;

        product = productService.addProduct(product, existingStockId);

        assertEquals(product.getTitle(), "TitreDuProduit");
        assertEquals(product.getPrice(), 10.0f);
        assertEquals(product.getQuantity(), 5);
        assertEquals(product.getCategory(), ProductCategory.CLOTHING);

        // Si votre méthode de service fonctionne correctement,
        // l'ID du stock associé au produit doit être 2L (existingStockId).
        assertEquals(product.getStock().getIdStock(), existingStockId);
    }

}