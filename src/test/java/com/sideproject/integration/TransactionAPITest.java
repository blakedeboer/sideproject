package com.sideproject.integration;

import com.sideproject.BudgetCategory;
import com.sideproject.CreditCardTransaction;
import com.sideproject.CreditCardTransactionRepository;
import com.sideproject.DemoApplication;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
public class TransactionAPITest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CreditCardTransactionRepository transactionRepository;

    @Test
    public void givenCreditCardTransactions_whenGetTransactions_thenStatus200() throws Exception {
        createTestTransaction();

        mvc.perform(get("/api/creditCardTransactions").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenCreditCardTransactions_whenGetTransactions_thenTransactionHasCorrectProperties() throws Exception {
        createTestTransaction();

        String firstTransactionsJSONPath = "_embedded.creditCardTransactions.[0]";
        mvc.perform(get("/api/creditCardTransactions").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(firstTransactionsJSONPath + ".vendor", is("b&b")))
                .andExpect(jsonPath(firstTransactionsJSONPath + ".description", is("food")))
                .andExpect(jsonPath(firstTransactionsJSONPath + ".amount", is(10.12)))
                .andExpect(jsonPath(firstTransactionsJSONPath + ".budgetCategory", is("RESTAURANT")))
                .andExpect(jsonPath(firstTransactionsJSONPath + ".date", is("2019-03-19")));
    }

    @Test
    public void givenCreditCardTransactions_whenGetTransactions_thenTransactionsHasTheCorrespondingNumberOfElements()
            throws Exception {
        createTestTransaction();
        createTestTransaction();

        String transactionsJSONPath = "_embedded.creditCardTransactions";

        mvc.perform(get("/api/creditCardTransactions").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(transactionsJSONPath, Matchers.hasSize(2)));
    }


    private void createTestTransaction() {
        Date date = Date.valueOf("2019-03-19");
        this.transactionRepository.save(new CreditCardTransaction(date, "b&b", "food", 10.12, BudgetCategory.RESTAURANT));
    }
}
