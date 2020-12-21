package com.example.pontointeligente.services.impl

import com.example.pontointeligente.documents.Empresa
import com.example.pontointeligente.repository.EmpresaRepository
import com.example.pontointeligente.services.EmpresaService
import org.springframework.stereotype.Service

@Service
class EmpresaServiceImpl(val empresaRepository: EmpresaRepository) : EmpresaService {
    override fun buscarPorCnpj(cnpj: String): Empresa? = empresaRepository.findByCnpj(cnpj)

    override fun persistir(empresa: Empresa) = empresaRepository.save(empresa)
}