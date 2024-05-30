package web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.models.Agent;

@Repository
public interface AgentRepo  extends JpaRepository<Agent, Long> {
    Agent findByUsername(String username);
    Agent findAgentByNumTel(String numTel);
    Agent findByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Boolean existsAgentByNumTel(String numTel);
}
