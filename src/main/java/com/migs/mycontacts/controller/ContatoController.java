package com.migs.mycontacts.controller;

import com.migs.mycontacts.controller.interfaces.ContatoControllerInterface;
import com.migs.mycontacts.dto.request.contato.AtualizarContatoRequestDTO;
import com.migs.mycontacts.dto.request.contato.CriarContatoRequestDTO;
import com.migs.mycontacts.dto.response.ContatoResponseDTO;
import com.migs.mycontacts.service.ContatoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/contatos")
@PreAuthorize("hasRole('USER')")
public class ContatoController implements ContatoControllerInterface {

    @NonNull
    private ContatoService contatoService;

    @PostMapping
    @Override
    public ResponseEntity<ContatoResponseDTO> salvarContato(
        CriarContatoRequestDTO dto,
        UriComponentsBuilder uriBuilder
    ) {
        ContatoResponseDTO responseDto = contatoService.salvarContato(dto);
        URI uri = uriBuilder.path("/{id}").buildAndExpand(responseDto.id()).toUri();

        return ResponseEntity.created(uri).body(responseDto);
    }

    @GetMapping
    @Override
    public ResponseEntity<List<ContatoResponseDTO>> buscarTodosContatos() {
        List<ContatoResponseDTO> responseDtos = contatoService.listarContatos();

        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<ContatoResponseDTO> buscarContatoPorId(UUID id) {
        ContatoResponseDTO responseDto = contatoService.buscarContatoPorId(id);

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping(params = "nome")
    @Override
    public ResponseEntity<List<ContatoResponseDTO>> buscarContatoPorNome(@RequestParam(required = false) String nome) {
        List<ContatoResponseDTO> responseDtos = contatoService.buscarContatoPorNome(nome);

        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping(params = "telefone")
    @Override
    public ResponseEntity<List<ContatoResponseDTO>> buscarContatoPorTelefone(@RequestParam(required = false) String telefone) {
        List<ContatoResponseDTO> responseDtos = contatoService.buscarContatoPorTelefone(telefone);

        return ResponseEntity.ok(responseDtos);
    }

    @PatchMapping("/{id}")
    @Override
    public ResponseEntity<ContatoResponseDTO> atualizarContato(
        @PathVariable UUID id,
        @Valid @RequestBody AtualizarContatoRequestDTO requestDto
    ) {
        ContatoResponseDTO responseDto = contatoService.editarContato(id, requestDto);

        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> removerContato(@PathVariable UUID id) {
        contatoService.removerContato(id);

        return ResponseEntity.noContent().build();
    }
}
