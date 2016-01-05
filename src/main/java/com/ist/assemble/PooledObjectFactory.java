package com.ist.assemble;

import io.searchbox.client.JestClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool.PoolableObjectFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class PooledObjectFactory implements PoolableObjectFactory<JestClient>, BeanFactoryAware {
    private Log logger = LogFactory.getLog(this.getClass());
    private BeanFactory factory;

    @Override
    public void activateObject(JestClient arg0) throws Exception {
    }

    @Override
    public void destroyObject(JestClient arg0) throws Exception {
    }

    //创建对象实例，用于填充对象池。同时可以分配这个对象适用的资源。
    @Override
    public JestClient makeObject() throws Exception {
        return getClient();
    }

    @Override
    public void passivateObject(JestClient arg0) throws Exception {
    }

    @Override
    public boolean validateObject(JestClient arg0) {
        return false;
    }
    
    private JestClient getClient(){
        JestClient client = (JestClient) factory.getBean("jestClient");
        logger.debug("jestClient: " + client);
        return client;
    }

    @Override
    public void setBeanFactory(BeanFactory arg0) throws BeansException {
        this.factory = arg0;
    }

}
