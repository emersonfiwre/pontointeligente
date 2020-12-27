package com.example.pontointeligente.controllers

import com.example.pontointeligente.documents.Funcionario
import com.example.pontointeligente.documents.Lancamento
import com.example.pontointeligente.dtos.LancamentoDto
import com.example.pontointeligente.enums.PerfilEnum
import com.example.pontointeligente.enums.TipoEnum
import com.example.pontointeligente.services.FuncionarioService
import com.example.pontointeligente.services.LancamentoService
import com.example.pontointeligente.utils.SenhaUtils
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.text.SimpleDateFormat
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
class LancamentoControllerTest {

    @Autowired
    private val mvc: MockMvc? = null

    @MockBean
    private val lancamentoService: LancamentoService? = null

    @MockBean
    private val funcionarioService: FuncionarioService? = null

    private val urlBase: String = "/api/lancamentos/"
    private val idFuncionario: String = "1"
    private val idLancamento: String = "1"
    private val tipo: String = TipoEnum.INICIO_TRABALHO.name
    private val data: Date = Date()

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    @Test
    @Throws(Exception::class)
    //@WithMockUser
    fun testCadastrarLancamento() {
        val lancamento: Lancamento = obterDadosLancamento()

        BDDMockito.given<Funcionario>(funcionarioService?.buscarPorId(idFuncionario))
            .willReturn(funcionario())
        BDDMockito.given(lancamentoService?.persistir(obterDadosLancamento()))
            .willReturn(lancamento)

        mvc!!.perform(
            MockMvcRequestBuilders.post(urlBase)
                .content(obterJsonRequisicaoPost())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.tipo").value(tipo))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.data").value(dateFormat.format(data)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.funcionarioId").value(idFuncionario))
            .andExpect(MockMvcResultMatchers.jsonPath("$.erros").isEmpty())
    }

    @Test
    @Throws(Exception::class)
    //@WithMockUser(username = "admin@empresa.com", roles = ["ADMIN"])
    @WithMockUser
    fun testCadastrarLancamentoFuncionarioIdInvalido() {
        BDDMockito.given<Funcionario>(funcionarioService?.buscarPorId(idFuncionario))
            .willReturn(null)

        mvc!!.perform(
            MockMvcRequestBuilders.post(urlBase)
                .content(obterJsonRequisicaoPost())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.erros")
                    .value("Funcionário não encontrado. ID inexistente.")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
    }

    @Test
    @Throws(Exception::class)
    //@WithMockUser(username = "admin@admin.com", roles = arrayOf("ADMIN"))
    @WithMockUser
    fun testRemoverLancamento() {
        BDDMockito.given<Lancamento>(lancamentoService?.buscarPorId(idLancamento))
            .willReturn(obterDadosLancamento())

        mvc!!.perform(
            MockMvcRequestBuilders.delete(urlBase + idLancamento)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
    }

    @Throws(JsonProcessingException::class)
    private fun obterJsonRequisicaoPost(): String {
        val lancamentoDto: LancamentoDto = LancamentoDto(
            dateFormat.format(data), tipo, "Descrição",
            "1.234,4.234", idFuncionario
        )
        val mapper = ObjectMapper()
        return mapper.writeValueAsString(lancamentoDto)
    }

    private fun obterDadosLancamento(): Lancamento =
        Lancamento(
            data, TipoEnum.valueOf(tipo), idFuncionario,
            "Descrição", "1.243,4.345", idLancamento
        )

    private fun funcionario(): Funcionario =
        Funcionario(
            "Nome", "email@email.com",
            SenhaUtils().gerarBcrypt("123456"),
            "23145699876", PerfilEnum.ROLE_USUARIO,
            idFuncionario
        )

}
