package growfast;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface GrowboxRepository extends MongoRepository<Growbox, String>
{
    public List<Growbox> findByName(String lastName);
}
