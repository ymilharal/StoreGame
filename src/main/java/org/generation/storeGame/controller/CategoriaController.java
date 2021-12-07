package org.generation.storeGame.controller;

import java.util.List;

import org.generation.storeGame.model.Categoria;
import org.generation.storeGame.repository.CategoriaRepository;
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
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired
	private CategoriaRepository catRepository;
	
	@GetMapping
	public ResponseEntity<List<Categoria>> getAll(){
		return ResponseEntity.ok(catRepository.findAll());
	}
	// retornar uma postagem pelo id
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> GetById(@PathVariable long id){
        return catRepository.findById(id)
                .map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
    }

    // retornar uma postagem por desenvolvedora
    @GetMapping("/descricao/{descricao}")
    public ResponseEntity<List<Categoria>> GetByDescricao(@PathVariable String descricao){
        return ResponseEntity.ok(catRepository.findAllByDesenvolvedoraContainingIgnoreCase(descricao));
    }
    @PostMapping
	public ResponseEntity<Categoria> post (@RequestBody Categoria categoria){
		return ResponseEntity.status(HttpStatus.CREATED).body(catRepository.save(categoria));
	}
    @PutMapping
	public ResponseEntity<Categoria> put (@RequestBody Categoria categoria){
		return ResponseEntity.ok(catRepository.save(categoria));
	}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRepository(@PathVariable long id) {
        return catRepository.findById(id).map(resposta -> {
            catRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }).orElse(ResponseEntity.notFound().build());
    }
}