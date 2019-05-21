package cn.algerfan.service.impl;

import cn.algerfan.domain.Result;
import cn.algerfan.domain.User;
import cn.algerfan.dto.UserDTO;
import cn.algerfan.enums.ResultCodeEnum;
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
    public Result addUser(User user) {
        if(user.getRole()==null || user.getRole()==0 ||
                user.getUserName()==null || "".equals(user.getUserName()) ||
                user.getPassword()==null || "".equals(user.getPassword())) {
            return new Result(ResultCodeEnum.SAVEFAIL);
        }
        if(userMapper.selectByName(user.getUserName()) !=null) {
            return new Result(-1,"添加失败，该用户名已存在");
        }
        if(userMapper.insertSelective(user)==0) {
            return new Result(ResultCodeEnum.UNSAVE);
        }
        return new Result(ResultCodeEnum.SAVE) ;
    }

    @Override
    public User getUserById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public Result updateUser(Integer userId,User user) {
        if(user.getRole()==null || user.getRole()==0 ||
                user.getUserName()==null || "".equals(user.getUserName()) ||
                user.getPassword()==null || "".equals(user.getPassword())) {
            return new Result(ResultCodeEnum.UPDATEFAIL);
        }
        if(!userMapper.selectByPrimaryKey(userId).getUserName().equals(user.getUserName())) {
            if(userMapper.selectByName(user.getUserName()) !=null) {
                return new Result(-1,"修改失败，该用户名已存在");
            }
        }
        user.setUserId(userId);
        if(userMapper.updateByPrimaryKeySelective(user)==0) {
            return new Result(ResultCodeEnum.UNUPDATE);
        }
        return new Result(ResultCodeEnum.UPDATE);
    }

    @Override
    public Result deleteUser(Integer userId) {
        if(userId == null || userId == 0) {
            return new Result(ResultCodeEnum.UNDELETE);
        }
        if(userMapper.deleteByPrimaryKey(userId) == 0) {
            return new Result(ResultCodeEnum.UNDELETE);
        }
        return new Result(ResultCodeEnum.DELETE);
    }
}
