package cn.algerfan.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * @param <T> 这个泛型通配符用来指定实体类
 */
public class BaseDao<T> extends SqlSessionDaoSupport implements Serializable {
    private static final long serialVersionUID = 1L;

    protected Logger log;
    private Class<T> clazz;

    @SuppressWarnings("unchecked")
    public BaseDao() {
        // 获得log对象
        if (log == null) {
            log = Logger.getLogger(this.getClass());
        }

        // 获得子类的泛型参数的类型
        Type type = this.getClass().getGenericSuperclass();
        if (!(type instanceof ParameterizedType)) {
            type = type.getClass().getSuperclass().getGenericSuperclass();
        }
        clazz = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
    }

    // 这是为了让父类获得SqlSessionFactory实例以便获得SqlSession，该实例已在spring-mybatis.xml配置
    @Override
    @Resource
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

}
