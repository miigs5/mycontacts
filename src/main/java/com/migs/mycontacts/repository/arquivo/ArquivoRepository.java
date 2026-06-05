package com.migs.mycontacts.repository.arquivo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import com.migs.mycontacts.exception.ArquivoInvalidoException;
import com.migs.mycontacts.mapper.MapperPersistence;
import com.migs.mycontacts.repository.Repository;

public abstract class ArquivoRepository<ID, T> implements Repository<ID, T> {
    protected final Map<ID, T> dados;
    protected final Path CAMINHO_ARQUIVO;
    
    public ArquivoRepository(Path caminho) {
        if (caminho == null) {
            throw new NullPointerException("[ERRO] Caminho do arquivo nao deve ser NULL.");
        }

        try {
            caminho = Path.of("dados/" + caminho.toString());
            Path diretorio = caminho.getParent();

            if (diretorio != null) { Files.createDirectories(diretorio); }

            if (Files.notExists(caminho)) { Files.createFile(caminho); }
            
            this.CAMINHO_ARQUIVO = caminho;
            this.dados = this.buscarTodos();
        }

        catch (IOException e) {
            throw new ArquivoInvalidoException("Caminho do arquivo " + caminho.toString());
        }
    }

    protected abstract ID getId(T obj);
    protected abstract void setId(T obj, ID id);
    protected abstract ID gerarId();
    protected abstract MapperPersistence<T, String> getMapper();

    public T salvar(T obj) {
        if (dados.containsKey(getId(obj))) {
            throw new IllegalArgumentException("ID duplicado.");
        }

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(CAMINHO_ARQUIVO, StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
            bufferedWriter.write(getMapper().serializar(obj));
            bufferedWriter.newLine();
            this.setId(obj, this.gerarId());
            return obj;
        }

        catch (IOException e) {
            System.err.println(e);
            throw new ArquivoInvalidoException("Falha ao converter/salvar objeto.");
        }
    }

    public Optional<T> buscarPorId(ID id) {
        return Optional.ofNullable(dados.get(id));
    }

    public Map<ID, T> buscarTodos() {
        try (BufferedReader bufferedReader = Files.newBufferedReader(CAMINHO_ARQUIVO)) {
            Map<ID, T> lista = new LinkedHashMap<>();

            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                T objLido = this.getMapper().desserializar(linha);
                lista.put(this.getId(objLido), objLido);
            }

            return lista;
        }

        catch (IOException e) {
            System.err.println(e);
            throw new ArquivoInvalidoException("Falha ao converter/encontrar objetos.");
        }
    }

    public T editar(ID id, T obj) {
        if (!dados.containsKey(id)) {
            throw new IllegalArgumentException("ID inexistente.");
        }

        Path CAMINHO_ARQUIVO_TEMP = null;
        try {
            CAMINHO_ARQUIVO_TEMP = Files.createTempFile(CAMINHO_ARQUIVO.getParent(), "temp", ".tmp");
            T objRetorno = null;
            
            try (BufferedReader bufferedReader = Files.newBufferedReader(CAMINHO_ARQUIVO);
                 BufferedWriter bufferedWriter = Files.newBufferedWriter(CAMINHO_ARQUIVO_TEMP, StandardCharsets.UTF_8)) {
                String linha = null;
                while ((linha = bufferedReader.readLine()) != null) {
                    T objLido = this.getMapper().desserializar(linha);
                    
                    if (this.getId(objLido).equals(id)) {
                        bufferedWriter.write(this.getMapper().serializar(obj));
                        bufferedWriter.newLine();
                        objRetorno = obj;
                        continue;
                    }

                    bufferedWriter.write(this.getMapper().serializar(objLido));
                    bufferedWriter.newLine();
                }
            }

            catch (IOException e) {
                try {
                    Files.deleteIfExists(CAMINHO_ARQUIVO_TEMP);
                } 
                
                catch (IOException ignorar) {
                    throw new ArquivoInvalidoException("Falha ao apagar arquivo temporario.");
                }

                System.err.println(e);
                throw new ArquivoInvalidoException("Falha ao remover/encontrar objeto.");
            }

            Files.move(CAMINHO_ARQUIVO_TEMP, CAMINHO_ARQUIVO, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            return objRetorno;
        }

        catch (IOException e) {
            try {
                Files.deleteIfExists(CAMINHO_ARQUIVO_TEMP);
            } 
            
            catch (IOException ignorar) {
                throw new ArquivoInvalidoException("Falha ao apagar arquivo temporario.");
            }

            System.err.println(e);
            throw new ArquivoInvalidoException("Falha ao remover/encontrar objeto.");
        }
    }

    public T remover(ID id) {
        if (!dados.containsKey(id)) {
            throw new ArquivoInvalidoException("Falha ao remover, ID nao encontrado.");
        }
        
        Path CAMINHO_ARQUIVO_TEMP = null;
        try {
            CAMINHO_ARQUIVO_TEMP = Files.createTempFile(CAMINHO_ARQUIVO.getParent(), "temp", ".tmp");
            T objRetorno = null;
            
            try (BufferedReader bufferedReader = Files.newBufferedReader(CAMINHO_ARQUIVO);
                 BufferedWriter bufferedWriter = Files.newBufferedWriter(CAMINHO_ARQUIVO_TEMP, StandardCharsets.UTF_8)) {
                String linha = null;
                while ((linha = bufferedReader.readLine()) != null) {
                    T objLido = this.getMapper().desserializar(linha);
                    
                    if (this.getId(objLido).equals(id)) {
                        objRetorno = objLido;
                        continue;
                    }

                    bufferedWriter.write(this.getMapper().serializar(objLido));
                    bufferedWriter.newLine();
                }
            }

            catch (IOException e) {
                try {
                    Files.deleteIfExists(CAMINHO_ARQUIVO_TEMP);
                } 
                
                catch (IOException ignorar) {
                    throw new ArquivoInvalidoException("Falha ao apagar arquivo temporario.");
                }

                System.err.println(e);
                throw new ArquivoInvalidoException("Falha ao remover/encontrar objeto.");
            }

            Files.move(CAMINHO_ARQUIVO_TEMP, CAMINHO_ARQUIVO, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            return objRetorno;
        }

        catch (IOException e) {
            try {
                Files.deleteIfExists(CAMINHO_ARQUIVO_TEMP);
            } 
            
            catch (IOException ignorar) {
                throw new ArquivoInvalidoException("Falha ao apagar arquivo temporario.");
            }

            System.err.println(e);
            throw new ArquivoInvalidoException("Falha ao remover/encontrar objeto.");
        }
    }
}
