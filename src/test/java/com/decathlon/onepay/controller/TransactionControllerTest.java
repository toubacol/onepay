package com.decathlon.onepay.controller;

import com.decathlon.onepay.Util.Constant;
import com.decathlon.onepay.dto.*;
import com.decathlon.onepay.exception.TransactionNotFoundException;
import com.decathlon.onepay.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.decathlon.onepay.util.TestUtils.*;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService service;


    @Test
    void createTransactionShouldSucceed() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TransactionCreateDto dto = getTransactionCreateDto();

        String requestJson = mapper.writer().writeValueAsString(dto);

        TransactionDto createdTransaction = new TransactionDto();
        createdTransaction.setId(12L);
        when(service.create(any(TransactionCreateDto.class))).thenReturn(createdTransaction);

        this.mockMvc.perform(post(Constant.API_TRANSACTION)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString(Constant.API_TRANSACTION + "/12")));
    }


    @Test
    void getAllTransactionShouldReturnAList() throws Exception {

        List<TransactionDto> transactionList = getValidTranctionDtoList();
        when(service.getAll()).thenReturn(transactionList);

        String responseAsString = this.mockMvc.perform(MockMvcRequestBuilders.get(Constant.API_TRANSACTION))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(responseAsString, new ObjectMapper().writeValueAsString(transactionList));
    }

    @Test
    void getTransactionByIdShouldReturnOne() throws Exception {

        TransactionDto transaction = getTransactionDto_1();
        when(service.getById(anyLong())).thenReturn(transaction);

        String responseAsString = this.mockMvc.perform(MockMvcRequestBuilders.get(Constant.API_TRANSACTION + "/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(responseAsString, new ObjectMapper().writeValueAsString(transaction));
    }

    @Test
    void getTransactionByIdNotFound() throws Exception {

        doThrow(TransactionNotFoundException.class).when(service).getById(anyLong());

        this.mockMvc.perform(MockMvcRequestBuilders.get(Constant.API_TRANSACTION + "/1"))
                .andExpect(status().isNotFound());

    }

    @Test
    void updateTransactionShouldSucceed() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TransactionUpdateDto dto = getTransactionUpdateDto();

        String requestJson = mapper.writer().writeValueAsString(dto);

        TransactionDto updatedTransaction = new TransactionDto();
        updatedTransaction.setId(37L);
        when(service.update(anyLong(), any(TransactionUpdateDto.class))).thenReturn(updatedTransaction);

        String responseAsString = this.mockMvc.perform(patch(Constant.API_TRANSACTION + "/37")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(responseAsString, new ObjectMapper().writeValueAsString(updatedTransaction));
    }

}
