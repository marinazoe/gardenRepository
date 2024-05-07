package Repositories;

import org.springframework.data.repository.CrudRepository;
import Tables.User;

public interface UserRepository extends CrudRepository<User, Integer>
{
    
}
