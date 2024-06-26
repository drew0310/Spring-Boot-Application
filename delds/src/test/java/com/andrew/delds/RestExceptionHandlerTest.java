package com.andrew.delds;

import com.andrew.delds.controller.MainController;
import com.andrew.delds.exception.ObjectNotFoundException;
import com.andrew.delds.service.AccountService;
import com.andrew.delds.service.AdPolicyService;
import com.andrew.delds.service.RestrictionService;
import com.andrew.delds.service.UserAgentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MainController.class)
public class RestExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @MockBean
    private AdPolicyService adPolicyService;

    @MockBean
    private RestrictionService restrictionService;

    @MockBean
    private UserAgentService userAgentService;


    @Test
    public void testHandleObjectNotFoundException() throws Exception {
        doThrow(new ObjectNotFoundException("Object not found")).when(accountService).getAccountById(1004);
        mockMvc.perform(get("/delivery/data/AccountSettings/accountsById/{id}", 1004))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data.statusCode").value("404"))
                .andExpect(jsonPath("$.data.errorMessage").value("Object not found"));
    }

    @Test
    public void testHandleHttpRequestMethodNotSupportedException() throws Exception {
        mockMvc.perform(delete("/delivery/data/Restriction/updateRestriction", 1001))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$.data.statusCode").value("405"))
                .andExpect(jsonPath("$.data.errorMessage").value("Request method 'DELETE' is not supported for this URL endpoint"));
    }

    @Test
    public void testHandleNullPointerException() throws Exception {
        doThrow(new NullPointerException("Test null pointer exception")).when(userAgentService).getUserAgentById(1007);

        mockMvc.perform(get("/delivery/data/UserAgent/userAgentsById/{id}", 1007))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.data.statusCode").value("500"))
                .andExpect(jsonPath("$.data.errorMessage").value("Null object (or) dependency not injected properly: Test null pointer exception"));
    }

    @Test
    public void testHandleNoHandlerFoundException() throws Exception {
        mockMvc.perform(post("/delivery/AdPolicy/addAdPolicy"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data.statusCode").value("400"))
                .andExpect(jsonPath("$.data.errorMessage").value("Invalid URL: no handler found for POST /delivery/AdPolicy/addAdPolicy"));
    }
}
