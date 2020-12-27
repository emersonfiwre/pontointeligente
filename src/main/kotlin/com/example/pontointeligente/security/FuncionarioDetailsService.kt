package com.example.pontointeligente.security

import com.example.pontointeligente.documents.Funcionario
import com.example.pontointeligente.services.FuncionarioService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

class FuncionarioDetailsService(val funcionarioService: FuncionarioService) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        if (username != null) {
            val funcionario: Funcionario? = funcionarioService.buscarPorEmail(username)
            if (funcionario != null) {
                FuncionarioPrincipal(funcionario)
            }
        }
        throw UsernameNotFoundException(username)
    }
}