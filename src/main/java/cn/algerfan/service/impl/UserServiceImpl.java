package cn.algerfan.service.impl;

import cn.algerfan.domain.User;
import cn.algerfan.dto.UserDTO;
import cn.algerfan.mapper.UserMapper;
import cn.algerfan.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  管理员
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
    public List<UserDTO> getAllUser() {
        List<User> userList = userMapper.getAllUser();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : userList){
            UserDTO userModel = new UserDTO(user.getUserId(), user.getUserName(),
                    user.getName(), user.getPhone(), user.getRole());
            userDTOS.add(userModel);
        }
        return userDTOS;
    }

    @Override
    public void addUser(User user) {
        User userEntity = new User();
        userEntity.setName(user.getName());
        userEntity.setPhone(user.getPhone());
        userEntity.setPassword(user.getPassword());
        userMapper.insert(userEntity);
    }

    @Override
    public User getUserById(Integer id) {
        User userEntity = userMapper.selectByPrimaryKey(id);
        User user = new User();
        user.setPhone(userEntity.getPhone());
        user.setUserId(userEntity.getUserId());
        user.setName(userEntity.getName());
        user.setPassword(userEntity.getPassword());
        return user;
    }

    @Override
    public void updateUser(User user) {
        User userEntity = userMapper.selectByPrimaryKey(user.getUserId());
        userEntity.setPassword(user.getPassword());
        userEntity.setPhone(user.getPhone());
        userEntity.setName(user.getName());
        userMapper.updateByPrimaryKeySelective(userEntity);
    }

    @Override
    public void deleteUser(Integer ids) {
        userMapper.deleteByPrimaryKey(ids);
    }
}
