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
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.entities.Product;
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
class OperatorServiceImplTest {
    @Autowired
    private OperatorServiceImpl operatorService;
    @Test
    @DatabaseSetup("/data-set/operator.xml")
    void retrieveAllOperators() {
        final List<Operator> operators = this.operatorService.retrieveAllOperators();
        assertEquals(operators.size(), 0);
    }
    @Test
    @DatabaseSetup("/data-set/operator.xml")
    void addOperator() {
        final Operator operator = new Operator();
        operator.setFname("ahmed");
        this.operatorService.addOperator(operator);
        assertEquals(this.operatorService.retrieveAllOperators().size(),1);
        assertEquals(this.operatorService.retrieveOperator(1L).getFname(), "ahmed");
    }





    @Test
    @DatabaseSetup("/data-set/operator.xml")
    void retrieveProduct_nullId() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            final Operator operator = this.operatorService.retrieveOperator(100L);
        });
    }
}