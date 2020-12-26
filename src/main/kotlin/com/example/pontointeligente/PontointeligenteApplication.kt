package com.example.pontointeligente

import com.example.pontointeligente.documents.Empresa
import com.example.pontointeligente.documents.Funcionario
import com.example.pontointeligente.enums.PerfilEnum
import com.example.pontointeligente.repositories.EmpresaRepository
import com.example.pontointeligente.repositories.FuncionarioRepository
import com.example.pontointeligente.repositories.LancamentoRepository
import com.example.pontointeligente.utils.SenhaUtils
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PontointeligenteApplication(val empresaRepository: EmpresaRepository,
								  val funcionarioRepository: FuncionarioRepository,
								  val lancamentoRepository: LancamentoRepository
) : CommandLineRunner {

	override fun run(vararg args: String?) {
		empresaRepository.deleteAll()
		funcionarioRepository.deleteAll()
		lancamentoRepository.deleteAll()

		var empresa: Empresa = Empresa("Empresa", "10443887000146")
		empresa = empresaRepository.save(empresa)

		var admin: Funcionario = Funcionario(
			"Admin", "admin@empresa.com",
			SenhaUtils().gerarBcrypt("123456"), "25708317000",
			PerfilEnum.ROLE_ADMIN, empresa.id!!
		)
		admin = funcionarioRepository.save(admin)

		var funcionario: Funcionario = Funcionario(
			"Funcionario",
			"funcionario@empresa.com", SenhaUtils().gerarBcrypt("123456"),
			"44325441557", PerfilEnum.ROLE_USUARIO, empresa.id!!
		)
		funcionario = funcionarioRepository.save(funcionario)

		System.out.println("Empresa ID: " + empresa.id)
		System.out.println("Admin ID: " + admin.id)
		System.out.println("Funcionario ID: " + funcionario.id)

	}
	//iniciar mongodb = mongod
	//parar mongodb = net stop MongoDB
}
	fun main(args: Array<String>) {
	runApplication<PontointeligenteApplication>(*args)
}
