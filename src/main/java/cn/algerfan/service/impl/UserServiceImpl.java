package cn.algerfan.service.impl;

import cn.algerfan.mapper.UserMapper;
import cn.algerfan.domain.User;
import cn.algerfan.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author algerfan
 * @since 2019/4/15 16
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> getAllUser() {
        List<User> userList = userMapper.getAllUser();
        List<User> userModelList = new ArrayList<>();
        for (User user : userList){
            User userModel = new User();
            userModel.setName(user.getName());
            userModel.setId(user.getId());
            userModel.setAge(user.getAge());
            userModelList.add(userModel);
        }
        return userModelList;
    }

    @Override
    public void addUser(User user) {
        User userEntity = new User();
        userEntity.setName(user.getName());
        userEntity.setAge(user.getAge());
        userEntity.setPassword(user.getPassword());
        userMapper.addUser(userEntity);
    }

    @Override
    public User getUserById(Integer id) {
        User userEntity = userMapper.getUserById(id);
        User user = new User();
        user.setAge(userEntity.getAge());
        user.setId(userEntity.getId());
        user.setName(userEntity.getName());
        user.setPassword(userEntity.getPassword());
        return user;
    }

    @Override
    public void updateUser(User user) {
        User userEntity = userMapper.getUserById(user.getId());
        userEntity.setPassword(user.getPassword());
        userEntity.setAge(user.getAge());
        userEntity.setName(user.getName());
        userMapper.updateUser(userEntity);
    }

    @Override
    public void deleteUser(Integer[] ids) {
        for (Integer id : ids){
            userMapper.deleteUserById(id);
        }
    }
}
