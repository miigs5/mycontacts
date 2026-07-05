package com.migs.mycontacts.repository.sqlite;

import com.migs.mycontacts.exception.SQLInvalidoException;
import com.migs.mycontacts.model.Contato;
import com.migs.mycontacts.model.Email;
import com.migs.mycontacts.model.Nome;
import com.migs.mycontacts.model.Telefone;
import com.migs.mycontacts.repository.ContatoRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ContatoDAO implements ContatoRepository {
    private Map<UUID, Contato> bufferContatos = new HashMap<>();
    private final Connection con = ConexaoJDBC.INSTANCE.getConnection();

    public ContatoDAO() {
        bufferContatos = this.buscarTodos();
    }

    @Override
    public Contato salvar(Contato contato) {
        String query = "INSERT INTO contato (id, nome, telefone, email, descricao) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, contato.getId().toString());
            preparedStatement.setString(2, contato.getNome().nome());
            preparedStatement.setString(3, contato.getTelefone().telefone());
            preparedStatement.setString(4, contato.getEmail().email());
            preparedStatement.setString(5, contato.getDescricao());
            preparedStatement.execute();

            bufferContatos = buscarTodos();

            return contato;
        }

        catch (SQLException e) { throw new SQLInvalidoException(e.getMessage()); }
    }

    @Override
    public Optional<Contato> buscarPorId(UUID id) {
        String query = "SELECT * FROM contato WHERE id = ?";

        if (bufferContatos != null && bufferContatos.containsKey(id)) { return Optional.of(bufferContatos.get(id)); }

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, id.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next())  { return Optional.empty(); }

            String contatoId = resultSet.getString("id");
            String contatoNome = resultSet.getString("nome");
            String contatoTelefone = resultSet.getString("telefone");
            String contatoEmail = resultSet.getString("email");
            String contatoDescricao = resultSet.getString("descricao");

            return Optional.of(new Contato(
                UUID.fromString(contatoId),
                new Nome(contatoNome),
                new Telefone(contatoTelefone),
                new Email(contatoEmail),
                contatoDescricao
            ));
        }

        catch (SQLException e) { throw new SQLInvalidoException(e.getMessage()); }
    }

    @Override
    public Map<UUID, Contato> buscarTodos() {
        String query = "SELECT * FROM contato ORDER BY nome";

        if (!bufferContatos.isEmpty()) { return bufferContatos; }

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) { return Collections.emptyMap(); }

            Map<UUID, Contato> contatos = new HashMap<>();

            do {
                String contatoId = resultSet.getString("id");
                String contatoNome = resultSet.getString("nome");
                String contatoTelefone = resultSet.getString("telefone");
                String contatoEmail = resultSet.getString("email");
                String contatoDescricao = resultSet.getString("descricao");

                Contato contato = new Contato(
                    UUID.fromString(contatoId),
                    new Nome(contatoNome),
                    new Telefone(contatoTelefone),
                    new Email(contatoEmail),
                    contatoDescricao
                );

                contatos.put(contato.getId(), contato);
            } while (resultSet.next());

            return contatos;
        }

        catch (SQLException e) { throw new SQLInvalidoException(e.getMessage()); }
    }

    @Override
    public Contato editar(UUID id, Contato contato) {
        String query = "UPDATE contato SET nome = ?, telefone = ?, email = ?, descricao = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, contato.getNome().nome());
            preparedStatement.setString(2, contato.getTelefone().telefone());
            preparedStatement.setString(3, contato.getEmail().email());
            preparedStatement.setString(4, contato.getDescricao());
            preparedStatement.setString(5, contato.getId().toString());
            int res = preparedStatement.executeUpdate();

            if (res > 0) {
                bufferContatos.replace(contato.getId(), contato);
                return contato;
            }

            return null;
        }

        catch (SQLException e) { throw new SQLInvalidoException(e.getMessage()); }
    }

    @Override
    public Contato remover(UUID id) {
        String query = "DELETE FROM contato WHERE id = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, id.toString());
            int res = preparedStatement.executeUpdate();

            if (res > 0) { return bufferContatos.remove(id); }

            return null;
        }

        catch (SQLException e) { throw new SQLInvalidoException(e.getMessage()); }
    }

    @Override
    public boolean existePorNomeETelefone(Nome nome, Telefone telefone) {
        String query = "SELECT * FROM contato WHERE nome = ? AND telefone = ?";

        for (Contato contato : bufferContatos.values()) {
            if (contato.getNome().equals(nome) && contato.getTelefone().equals(telefone)) {
                return true;
            }
        }

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, nome.nome());
            preparedStatement.setString(2, telefone.telefone());
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        }

        catch (SQLException e) { throw new SQLInvalidoException(e.getMessage()); }
    }

    @Override
    public List<Contato> buscarPorNome(Nome nome) {
        String query = "SELECT * FROM contato WHERE nome = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, nome.nome());
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Contato> contatos = new ArrayList<>();
            while (resultSet.next()) {
                contatos.add(new Contato(
                    UUID.fromString(resultSet.getString("id")),
                    new Nome(resultSet.getString("nome")),
                    new Telefone(resultSet.getString("telefone")),
                    new Email(resultSet.getString("email")),
                    resultSet.getString("descricao")
                ));
            }

            return contatos;
        }

        catch (SQLException e) { throw new SQLInvalidoException(e.getMessage()); }
    }
}
