package cn.algerfan.service;

import cn.algerfan.domain.User;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author algerfan
 * @since 2019/4/15 16
 */
public interface UserService {
    List<User> getAllUser();

    void addUser(User user);

    User getUserById(Integer id);

    void updateUser(User user);

    void deleteUser(Integer[] ids);
}
