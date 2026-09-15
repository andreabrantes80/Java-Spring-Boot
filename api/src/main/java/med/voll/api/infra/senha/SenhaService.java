package med.voll.api.infra.senha;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;
@Service
public class SenhaService {

    private Queue<String> fila = new LinkedList<>();
    private int contador = 0;

    public String gerarSenha() {
        contador++;
        String senha = "A" + String.format("%03d", contador);
        fila.add(senha);
        return senha;
    }

    public String chamarProximaSenha() {
        return fila.poll();
    }
}
