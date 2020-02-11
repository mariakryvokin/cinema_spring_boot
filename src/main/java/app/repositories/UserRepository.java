package app.repositories;

import app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    User getUserByEmail(String email);

    @Query(value = "SELECT count (*) FROM ((users u inner join orders o on u.id = o.users_id) inner join tickets t on o.id=t.orders_id)  WHERE u.id= ?1",
            nativeQuery = true)
    int countBookedTickets(long userId);
}
