package cn.algerfan.mapper;

import cn.algerfan.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author algerfan
 * @since 2019/4/15 16
 */
@Repository
public interface UserMapper {
    List<User> getAllUser();

    void addUser(User user);

    User getUserById(Integer id);

    void updateUser(User user);

    void deleteUserById(Integer id);
}
