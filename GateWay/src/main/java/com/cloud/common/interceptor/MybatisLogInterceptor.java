package com.cloud.common.interceptor;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @Author : BieFeNg
 * @DATE : 2018/10/18 23:11
 */

@Component
@Intercepts(@Signature(type = ParameterHandler.class,method = "setParameters",args = {PreparedStatement.class}))
public class MybatisLogInterceptor implements Interceptor {
    private static final Logger LOGGER= LoggerFactory.getLogger(MybatisLogInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();

        ParameterHandler handler = (ParameterHandler) target;
        Object[] args = invocation.getArgs();

        MappedStatement mappedStatement = (MappedStatement) getUnAccessField(handler,"mappedStatement");
        BoundSql boundSql = (BoundSql) getUnAccessField(handler,"boundSql");
        Object parameterObject = getUnAccessField(handler,"parameterObject");
        Configuration configuration = (Configuration) getUnAccessField(handler,"configuration");
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        String id = mappedStatement.getId();

        String sqlName = id.substring(id.lastIndexOf(".")+1);

        String sql = boundSql.getSql();

        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        List<Object> values = new ArrayList<>();

        if (parameterMappings != null) {
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    if (boundSql.hasAdditionalParameter(propertyName)) { // issue #448 ask first for additional params
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (parameterObject == null) {
                        value = null;
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                        value = parameterObject;
                    } else {
                        MetaObject metaObject = configuration.newMetaObject(parameterObject);
                        value = metaObject.getValue(propertyName);
                    }
                    TypeHandler typeHandler = parameterMapping.getTypeHandler();
                    JdbcType jdbcType = parameterMapping.getJdbcType();
                    if (value == null && jdbcType == null) {
                        jdbcType = configuration.getJdbcTypeForNull();
                    }
                    try {
                        typeHandler.setParameter((PreparedStatement) args[0], i + 1, value, jdbcType);
                        if(value instanceof Number){
                            values.add(value);
                        }else {
                            values.add("'" + value + "'");
                        }
                    } catch (TypeException e) {
                        throw new TypeException("Could not set parameters for mapping: " + parameterMapping + ". Cause: " + e, e);
                    } catch (SQLException e) {
                        throw new TypeException("Could not set parameters for mapping: " + parameterMapping + ". Cause: " + e, e);
                    }
                }
            }
        }

        sql=sql.replaceAll("\\?","{}").replaceAll("[\\n\\s\\t]+"," ");

        LOGGER.debug("(SQL-"+sqlName+"): "+sql,values.toArray());

        return invocation.proceed();

    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o,this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

    public Object getUnAccessField(Object obj,String fieldName){
        try {
            Class clazz = obj.getClass();
            Field field = clazz.getDeclaredField(fieldName);
            if(!field.isAccessible())
                field.setAccessible(true);
            return field.get(obj);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
