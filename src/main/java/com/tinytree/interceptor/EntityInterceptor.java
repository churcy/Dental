package com.tinytree.interceptor;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.tinytree.util.RandomUtil;
import com.tinytree.util.ReflectUtil;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}) })
public class EntityInterceptor implements Interceptor{
	
	private static final String CREATE_DATE_PROPERTY_NAME = "createDate";// "创建日期"属性名称
	private static final String MODIFY_DATE_PROPERTY_NAME = "modifyDate";// "修改日期"属性名称
	private static final String PK = "id";

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        Object parameter = invocation.getArgs()[1];
        List<Field> fields = ReflectUtil.getAllFields(parameter.getClass());
        Date currentDate = new Date();
        if(SqlCommandType.UPDATE==sqlCommandType) {
            for (Field field : fields) {
                if (MODIFY_DATE_PROPERTY_NAME.equals(field.getName())) {
                    field.setAccessible(true);
                    field.set(parameter,currentDate);
                    field.setAccessible(false);
                }
            }
        } else if(SqlCommandType.INSERT==sqlCommandType){
            for (Field field : fields) {
                if (CREATE_DATE_PROPERTY_NAME.equals(field.getName())||MODIFY_DATE_PROPERTY_NAME.equals(field.getName())) {
                    field.setAccessible(true);
                    field.set(parameter,currentDate);
                    field.setAccessible(false);
                }
                
                if(PK.equals(field.getName())){
                	field.setAccessible(true);
                    field.set(parameter, RandomUtil.uuid());
                    field.setAccessible(false);
                }
            }
        }
        return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
	}

	@Override
	public void setProperties(Properties arg0) {
		
	}

}
