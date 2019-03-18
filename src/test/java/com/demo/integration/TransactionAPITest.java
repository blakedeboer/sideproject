package com.demo.integration;

import com.sideproject.DemoApplication;
import com.sideproject.Employee;
import com.sideproject.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
public class TransactionAPITest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void givenEmployees_whenGetEmployees_thenStatus200()
        throws Exception {

        createTestEmployee();

        MvcResult mvcResult = mvc.perform(get("/api/employees")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json;charset=UTF-8"))
                .andReturn();
        System.out.println("mvcResult = " + mvcResult);
//                .andExpect(jsonPath("$[0].name", is("bob")));

    }

    private void createTestEmployee() {
        this.employeeRepository.save(new Employee("Frodo", "Baggins", "ring bearer"));
    }
}
