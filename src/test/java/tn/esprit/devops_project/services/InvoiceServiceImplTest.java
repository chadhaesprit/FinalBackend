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
import tn.esprit.devops_project.entities.Invoice;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.entities.Stock;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
class InvoiceServiceImplTest {
    @Autowired
    private InvoiceServiceImpl invoiceService;
    @Autowired OperatorServiceImpl operatorService;

    @Test
    @DatabaseSetup("/data-set/invoice.xml")
    void retrieveAllInvoices() {
        final List<Invoice> invoiceList = this.invoiceService.retrieveAllInvoices();
        assertEquals(invoiceList.size(), 0);
    }

    @Test
    @DatabaseSetup("/data-set/invoice-data.xml")
    void cancelInvoice() {
        Long invoiceId = 3L; // Remplacez par un ID existant dans vos données de test
        invoiceService.cancelInvoice(invoiceId);
        // Vérifiez que l'invoice a été correctement annulée
    }

    @Test
    @DatabaseSetup("/data-set/invoice-data.xml")
    void getTotalAmountInvoiceBetweenDates() {
        // Date de début et de fin appropriées en fonction des données du jeu de données
        Date startDate = getDate("2023-01-01");
        Date endDate = getDate("2023-01-04");

        float totalAmount = invoiceService.getTotalAmountInvoiceBetweenDates(startDate, endDate);

        // Écrivez des assertions pour vérifier le montant total
        assertEquals(100.0f, totalAmount, 0.01f); // 150.0f est le montant total attendu
    }

    private Date getDate(String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException("Erreur lors de la conversion de la date.", e);
        }
    }





    @Test
    @DatabaseSetup("/data-set/invoice.xml")
    void retrieveInvoice_nullId() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            final Invoice invoice = this.invoiceService.retrieveInvoice(100L);
        });
    }
}