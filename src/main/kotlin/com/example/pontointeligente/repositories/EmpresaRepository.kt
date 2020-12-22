package com.example.pontointeligente.repositories

import com.example.pontointeligente.documents.Empresa
import org.springframework.data.mongodb.repository.MongoRepository

interface EmpresaRepository : MongoRepository<Empresa, String> {
    fun findByCnpj(cnpj: String): Empresa?
}