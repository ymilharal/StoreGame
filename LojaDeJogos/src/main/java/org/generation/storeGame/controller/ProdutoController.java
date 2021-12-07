package org.generation.storeGame.controller;

import java.util.List;
import org.generation.storeGame.model.Produto;
import org.generation.storeGame.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {

	@Autowired
	private ProdutoRepository prodRepository;
	@GetMapping
	public ResponseEntity<List<Produto>> getAll(){
		return ResponseEntity.ok(prodRepository.findAll());
	}
	// retornar uma postagem pelo id
    @GetMapping("/{id}")
    public ResponseEntity<Produto> GetById(@PathVariable long id){
        return prodRepository.findById(id)
                .map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
    }

    // retornar uma postagem por nome
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Produto>> GetByNome(@PathVariable String nome){
        return ResponseEntity.ok(prodRepository.findAllByNomeContainingIgnoreCase(nome));
    }
    @PostMapping
	public ResponseEntity<Produto> post (@RequestBody Produto produto){
		return ResponseEntity.status(HttpStatus.CREATED).body(prodRepository.save(produto));
	}
    @PutMapping
	public ResponseEntity<Produto> put (@RequestBody Produto produto){
		return ResponseEntity.ok(prodRepository.save(produto));
	}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRepository(@PathVariable long id) {
        return prodRepository.findById(id).map(resposta -> {
            prodRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
