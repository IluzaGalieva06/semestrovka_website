package kpfu.itis.repositories;

import kpfu.itis.models.User;
import kpfu.itis.repositories.generic.CrudRepository;


import java.util.Optional;

public interface UserRepository extends CrudRepository<User> {

    Optional<User> findByEmail(String email);
    Optional<User> findByName(String model);
    void updateAvatarForUser(String email, Long fileId);

}
