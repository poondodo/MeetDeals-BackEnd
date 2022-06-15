package com.laioffer.meetdeals.Repository;

import com.laioffer.meetdeals.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String userEmail);
}
