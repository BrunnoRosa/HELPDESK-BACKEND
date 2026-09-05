package com.example.help_desk.config;

import com.example.help_desk.model.UsuarioModel;
import com.example.help_desk.model.enums.PerfilUsuario;
import com.example.help_desk.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Verifica se o e-mail já existe para não duplicar o cadastro ao reiniciar o servidor
        if (!usuarioRepository.existsByEmailIgnoreCase("admin@sistema.com")) {
            UsuarioModel admin = new UsuarioModel();
            admin.setNome("Administrador Master");
            admin.setEmail("admin@sistema.com");
            admin.setSenha(passwordEncoder.encode("123456")); // Senha provisória inicial
            admin.setPerfil(PerfilUsuario.ADMINISTRADOR);

            usuarioRepository.save(admin);
            System.out.println("✅ Administrador padrão criado: admin@sistema.com / Senha: 123456");
        }
    }
}