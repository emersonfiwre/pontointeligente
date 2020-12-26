package com.example.pontointeligente.documents

import com.example.pontointeligente.enums.PerfilEnum
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Funcionario(
        val nome: String,
        val email: String,
        val senha: String,
        val cpf: String,
        val perfil: PerfilEnum,
        val empresaId: String,
        val valorHora: Double? = 0.0,
        val horasTrabalhoDia: Float? = 0.0f,
        val qtdeHorasAlmoco: Float? = 0.0f,
        @Id var id: String? = null

)