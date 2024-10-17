package felipe.proj.estqoue.service.fornecedor;

import felipe.proj.estqoue.entidades.Fornecedor;
import felipe.proj.estqoue.excecao.GlobalExcecao;
import felipe.proj.estqoue.repositorio.FornecedorRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FornecedorService implements IForncedeorService{

    private final FornecedorRepositorio fornecedorRepositorio;

    public FornecedorService(FornecedorRepositorio fornecedorRepositorio) {
        this.fornecedorRepositorio = fornecedorRepositorio;
    }

    @Override
    public Fornecedor addFornecedor(Fornecedor fornecedor) {
        boolean fornecedorExiste = fornecedorRepositorio.existsByFornecedorNome(fornecedor.getFornecedorNome());

        if (fornecedorExiste) {throw new GlobalExcecao(fornecedor.getFornecedorNome() + " já existe este fornecedor.");}

        return fornecedorRepositorio.save(fornecedor);
    }

    @Override
    public Fornecedor atualizarFornecedor(Fornecedor fornecedor, Long id) {
        return Optional.ofNullable(getFornecedorById(id)).map(fornecedorAntigo -> {
            fornecedorAntigo.setFornecedorNome(fornecedor.getFornecedorNome());
            return fornecedorRepositorio.save(fornecedorAntigo);
        }).orElseThrow(()-> new GlobalExcecao("Fornecedor não encontrada!"));
    }

    @Override
    public void deleteFornecedor(Long id) {
        fornecedorRepositorio.findById(id)
                .ifPresentOrElse(fornecedorRepositorio::delete,() -> {
                    throw new GlobalExcecao("Não foi possível encontrar esse fornecedor!");
        });
    }

    @Override
    public List<Fornecedor> getAllFornecedores() {
        return fornecedorRepositorio.findAll();
    }

    @Override
    public Fornecedor getFornecedorById(Long id) {
        return fornecedorRepositorio.findById(id)
                .orElseThrow(()-> new GlobalExcecao("Não foi possivel encontrar esse fornecedor!"));
    }

    @Override
    public Optional<Fornecedor> getFornecedorByFornecedorNome(String fornecedorNome) {
        return fornecedorRepositorio.findByFornecedorNome(fornecedorNome);
    }
}
