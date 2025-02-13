package br.com.ifba.Avaliacao.repository;


import br.com.ifba.Avaliacao.entity.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {


}
