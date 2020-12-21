package com.example.pontointeligente.utils

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


class SenhaUtilsTest {
    private val SENHA = "123456"
    private val bCryptEncoder = BCryptPasswordEncoder()


    @Test
    fun testGerarHashSenha() {
        val hash = SenhaUtils().gerarBcrypt(SENHA)
        Assertions.assertTrue(bCryptEncoder.matches(SENHA, hash))
    }

}